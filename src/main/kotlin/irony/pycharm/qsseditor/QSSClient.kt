/*
 * Copyright (c) 2024. Irony All Rights Reserved.
 * Project: QSSEditor
 * File: QSSClient.kt
 * Date: 2024/5/18 上午1:02
 * Author: Irony
 * Email: 892768447@qq.com
 * Site: https://pyqt.site , https://pyqt5.com
 */

package irony.pycharm.qsseditor

import com.intellij.openapi.diagnostic.logger
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

private val Log = logger<QSSClient>()

class QSSClient(serverUri: URI?) : WebSocketClient(serverUri) {
    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.debug("onOpen")
    }

    override fun onMessage(message: String?) {
        Log.debug("onMessage: $message")
    }

    override fun onClose(
        code: Int,
        reason: String?,
        remote: Boolean,
    ) {
        Log.debug("onClose: code=$code, remote=$remote, reason=$reason")
    }

    override fun onError(e: Exception?) {
        Log.error("onError", e)
    }

    companion object {
        private var client: QSSClient? = null

        fun connect(
            host: String,
            port: Int,
        ) {
            disconnect()
            if (client == null) {
                Log.debug("connect to node: ws://$host:$port")
                client = QSSClient(URI("ws://$host:$port"))
                client!!.connect()
            }
        }

        fun reconnect() {
            Log.debug("do reconnect")
            client?.reconnect()
        }

        fun disconnect() {
            Log.debug("do disconnect")
            client?.close()
            client = null
        }

        fun send(message: String) {
            client?.send(message)
        }
    }
}
