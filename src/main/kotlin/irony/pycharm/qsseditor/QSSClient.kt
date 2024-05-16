package irony.pycharm.qsseditor

import com.intellij.openapi.diagnostic.logger
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

private val LOG = logger<QSSClient>()

class QSSClient(serverUri: URI?) : WebSocketClient(serverUri) {

    override fun onOpen(handshakedata: ServerHandshake?) {
        LOG.debug("onOpen");
    }

    override fun onMessage(message: String?) {
        LOG.debug("onMessage: $message")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        LOG.debug("onClose: code=$code, remote=$remote, reason=$reason")
    }

    override fun onError(e: Exception?) {
        LOG.error("onError", e)
    }

    companion object {
        private var client : QSSClient? = null

        fun connect(host: String, port: Int) {
            disconnect()
            if (client == null) {
                LOG.debug("connect to node: ws://$host:$port")
                client = QSSClient(URI("ws://$host:$port"))
                client!!.connect()
            }
        }

        fun reconnect() {
            LOG.debug("do reconnect")
            client?.reconnect()
        }

        fun disconnect() {
            LOG.debug("do disconnect")
            client?.close()
            client = null
        }
    }
}