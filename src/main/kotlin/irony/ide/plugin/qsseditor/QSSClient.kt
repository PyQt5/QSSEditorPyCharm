/*
 * Copyright (c) 2024. Irony All Rights Reserved.
 * Project: QSSEditor
 * File: QSSClient.kt
 * Date: 2024/5/18 上午1:02
 * Author: Irony
 * Email: 892768447@qq.com
 * Site: https://pyqt.site , https://pyqt5.com
 */

@file:Suppress("ktlint:standard:no-wildcard-imports")

package irony.ide.plugin.qsseditor

import com.intellij.openapi.diagnostic.logger
import com.intellij.util.ui.update.MergingUpdateQueue
import com.intellij.util.ui.update.Update
import kotlinx.serialization.json.*
import okhttp3.*
import org.jetbrains.annotations.Nullable
import java.util.concurrent.TimeUnit

private val Log = logger<QSSClient>()

interface MessageListener {
    fun onParams(array: JsonArray)
}

class KeywordsListener : MessageListener {
    override fun onParams(array: JsonArray) {
        // TODO: 添加关键词
    }
}

class QSSClient : WebSocketListener() {
    init {
        listener["addKeywords"] = KeywordsListener()
    }

    override fun onOpen(
        webSocket: WebSocket,
        response: Response,
    ) {
        twice = 0
        Log.info("onOpen")
    }

    override fun onMessage(
        webSocket: WebSocket,
        text: String,
    ) {
        // Log.debug("onMessage: $text")
        if (text.isNotEmpty()) {
            val json = Json.parseToJsonElement(text).jsonObject
            if (json.getValue(
                    "jsonrpc",
                ).jsonPrimitive.contentOrNull != "2.0" || json.getValue("method").jsonPrimitive.contentOrNull == null
            ) {
                Log.warn("onMessage: jsonrpc or method is null")
                return
            }

            // 处理对应消息
            val method = json.getValue("method").jsonPrimitive.contentOrNull
            listener[method]?.onParams(json.getValue("params").jsonArray)
        }
    }

    override fun onClosing(
        webSocket: WebSocket,
        code: Int,
        reason: String,
    ) {
        Log.debug("onClosing")
        if (done) {
            return
        }
        Thread.sleep(5000)
        reconnect(webSocket.request().url.host, webSocket.request().url.port)
    }

    override fun onFailure(
        webSocket: WebSocket,
        t: Throwable,
        response: Response?,
    ) {
        if (twice < 50) {
            Log.warn("onFailure, ${t.message}")
        }
        if (done) {
            Log.info("client require shutdown")
            return
        }
        Thread.sleep(5000)
        reconnect(webSocket.request().url.host, webSocket.request().url.port)
    }

    companion object {
        private var host: String = ""
        private var port: Int = 0
        private var twice: Int = 0
        private var done: Boolean = false
        private var self: QSSClient? = null
        private var client: OkHttpClient? = null
        private var socket: WebSocket? = null
        private var queue: MergingUpdateQueue? = null
        private var messages: MutableMap<String, List<String>> = mutableMapOf()
        private var listener: HashMap<String, MessageListener> = HashMap()

        fun connect(
            host: String,
            port: Int,
        ) {
            if (host == Companion.host && port == Companion.port) {
                if (socket != null || self != null) {
                    return
                }
            }

            Companion.host =
                host.ifEmpty {
                    "localhost"
                }
            Companion.port = port

            disconnect()

            if (self == null) {
                self = QSSClient()
                queue = MergingUpdateQueue("QSSClient", 2000, true, MergingUpdateQueue.ANY_COMPONENT, null)
                queue!!.setRestartTimerOnAdd(true)
            }
            if (client == null) {
                client = OkHttpClient.Builder().pingInterval(5, TimeUnit.SECONDS).connectTimeout(30, TimeUnit.SECONDS).build()
            }

            if (socket == null) {
                Log.debug("connect to node: ws://$host:$port")
                socket = client!!.newWebSocket(Request.Builder().url("ws://$host:$port").build(), self!!)
            }
        }

        fun reconnect(
            host: String,
            port: Int,
        ) {
            if (twice < 50) {
                twice = twice.inc()
                Log.debug("do reconnect")
            }
            socket = client!!.newWebSocket(Request.Builder().url("ws://$host:$port").build(), self!!)
        }

        private fun disconnect() {
            Log.debug("do disconnect")
            socket?.cancel()
            socket?.close(1001, "client disconnect")
            socket = null
        }

        fun shutdown() {
            Log.info("do shutdown")
            done = true
            disconnect()
        }

        fun isConnected(): Boolean {
            return socket != null
        }

        private fun buildMessage(
            method: String,
            message: List<String>,
        ): String {
            return buildJsonObject {
                put("jsonrpc", "2.0")
                put("method", method)
                putJsonArray("params") {
                    for (s in message) {
                        add(s)
                    }
                }
            }.toString()
        }

        private fun sendMessage(
            method: String,
            @Nullable message: List<String>,
            delay: Boolean = false,
        ) {
            if (socket == null || message.isEmpty()) {
                return
            }
            if (delay) {
                messages.clear()
                messages[method] = message
                queue?.queue(
                    object : Update(method) {
                        override fun run() {
                            for (item in messages) {
                                sendMessage(item.key, item.value, false)
                            }
                        }
                    },
                )
            } else {
                // Log.info("sendMessage: method=$method, message=" + message.joinToString("\n"))
                socket!!.send(buildMessage(method, message))
            }
        }

        fun applyStyle(
            @Nullable message: List<String>,
            delay: Boolean = false,
        ) {
            sendMessage("setStyleSheet", message, delay)
        }

        fun selectWidget(
            @Nullable message: List<String>,
            delay: Boolean = false,
        ) {
            sendMessage("selectWidget", message, delay)
        }
    }
}
