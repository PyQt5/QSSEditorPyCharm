/*
 * Copyright (c) 2024. Irony All Rights Reserved.
 * Project: QSSEditor
 * File: QSSStartup.kt
 * Date: 2024/5/18 上午1:02
 * Author: Irony
 * Email: 892768447@qq.com
 * Site: https://pyqt.site , https://pyqt5.com
 */

package irony.ide.plugin.qsseditor

import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

private val Log = logger<QSSStartup>()

class QSSStartup : StartupActivity {
    override fun runActivity(project: Project) {
        // 启动客户端连接
        Log.info("project[${project.name}] opened")
        QSSClient.connect(QSSState.instance.host, QSSState.instance.port)
        QSSCompletion.load(this.javaClass.getResource("/data/qss.json") ?: return)
        QSSDocProvider.load(this.javaClass.getResource("/data/qss.json") ?: return)
    }
}
