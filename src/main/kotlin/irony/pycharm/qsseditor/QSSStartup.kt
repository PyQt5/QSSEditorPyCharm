package irony.pycharm.qsseditor

import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

private val LOG = logger<QSSStartup>()

internal class QSSStartup : StartupActivity {

    override fun runActivity(project: Project) {
        // 启动客户端连接
        LOG.debug("project[${project.name}] opened")
        QSSClient.connect(QSSState.instance.host, QSSState.instance.port)
    }
}