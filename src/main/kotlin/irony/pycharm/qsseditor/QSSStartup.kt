package irony.pycharm.qsseditor

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

internal class QSSStartup : StartupActivity {

    override fun runActivity(project: Project) {
        // 启动客户端连接
        QSSClient.connect(QSSState.instance.host, QSSState.instance.port)
    }
}