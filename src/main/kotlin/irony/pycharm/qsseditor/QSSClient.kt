package irony.pycharm.qsseditor

import com.intellij.openapi.application.ApplicationManager
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class QSSClient(serverUri: URI?) : WebSocketClient(serverUri) {

    override fun onOpen(handshakedata: ServerHandshake?) {
        println("onOpen");
    }

    override fun onMessage(message: String?) {
        println("onMessage: $message")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        println("onClose: code=$code, remote=$remote, reason=$reason")
    }

    override fun onError(ex: Exception?) {
        System.err.println("onError: $ex")
    }

    companion object {
        private var client : QSSClient? = null

        fun connect(host: String, port: Int) {
            disconnect()
            if (client == null) {
                client = QSSClient(URI("ws://$host:$port"))
                client!!.connect()
            }
        }

        fun reconnect() {
            client?.reconnect()
        }

        fun disconnect() {
            client?.close()
            client = null
        }
    }
}