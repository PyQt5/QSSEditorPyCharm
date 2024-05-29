/*
 * Copyright (c) 2024. Irony All Rights Reserved.
 * Project: QSSEditor
 * File: QSSDynamic.kt
 * Date: 2024/5/25 上午11:59
 * Author: Irony
 * Email: 892768447@qq.com
 * Site: https://pyqt.site , https://pyqt5.com
 */

package irony.ide.plugin.qsseditor

import com.intellij.ide.plugins.DynamicPluginListener
import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.openapi.diagnostic.logger

private val Log = logger<QSSDynamic>()

class QSSDynamic : DynamicPluginListener {
    override fun beforePluginLoaded(pluginDescriptor: IdeaPluginDescriptor) {
        Log.info("beforePluginLoaded")
    }

    override fun beforePluginUnload(
        pluginDescriptor: IdeaPluginDescriptor,
        isUpdate: Boolean,
    ) {
        Log.info("beforePluginUnload")
        QSSClient.shutdown()
    }

    override fun checkUnloadPlugin(pluginDescriptor: IdeaPluginDescriptor) {
        Log.info("checkUnloadPlugin")
        QSSClient.shutdown()
    }

    override fun pluginLoaded(pluginDescriptor: IdeaPluginDescriptor) {
        Log.info("pluginLoaded")
    }

    override fun pluginUnloaded(
        pluginDescriptor: IdeaPluginDescriptor,
        isUpdate: Boolean,
    ) {
        Log.info("pluginUnloaded, isUpdate: $isUpdate")
        QSSClient.shutdown()
    }
}
