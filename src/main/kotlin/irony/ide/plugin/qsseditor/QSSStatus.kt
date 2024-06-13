/*
 * Copyright (c) 2024. Irony All Rights Reserved.
 * Project: QSSEditor
 * File: QSSStatus.kt
 * Date: 2024/6/13 下午10:37
 * Author: Irony
 * Email: 892768447@qq.com
 * Site: https://pyqt.site , https://pyqt5.com
 */

@file:Suppress("ktlint:standard:no-wildcard-imports")

package irony.ide.plugin.qsseditor

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory
import com.intellij.util.Consumer
import com.intellij.util.messages.Topic
import java.awt.event.MouseEvent
import java.util.*
import javax.swing.Icon

private const val ID = "QSSEditor"

val STATUS_TOPIC: Topic<QSSStatusListener> = Topic(QSSStatusListener::class.java)

interface QSSStatusListener : EventListener {
    fun onStatusChanged(status: Boolean)
}

class QSSStatus : StatusBarWidgetFactory {
    companion object {
//        val widget: QSSStatusWidget = QSSStatusWidget()
    }

    override fun getId(): String {
        return ID
    }

    override fun getDisplayName(): String {
        return ID
    }

    override fun isAvailable(project: Project): Boolean {
        return true
    }

    override fun createWidget(project: Project): StatusBarWidget {
        return QSSStatusWidget()
    }

    override fun disposeWidget(widget: StatusBarWidget) {
        Disposer.dispose(widget)
    }

    override fun canBeEnabledOn(statusBar: StatusBar): Boolean {
        return true
    }
}

class QSSStatusWidget : StatusBarWidget, StatusBarWidget.IconPresentation {
    private var enabled: Boolean = false
    private var bar: StatusBar? = null

    override fun dispose() {
        enabled = false
        bar = null
    }

    override fun ID(): String {
        return ID
    }

    override fun install(statusBar: StatusBar) {
        bar = statusBar
        enabled = QSSClient.isConnected()
        updateWidget()

        ApplicationManager.getApplication().messageBus.connect(this).subscribe(
            STATUS_TOPIC,
            object : QSSStatusListener {
                override fun onStatusChanged(status: Boolean) {
                    enabled = status
                    updateWidget()
                }
            },
        )
    }

    override fun getPresentation(): StatusBarWidget.WidgetPresentation {
        return this
    }

    override fun getTooltipText(): String {
        return if (enabled) {
            "Connected"
        } else {
            "Disconnected"
        }
    }

    override fun getClickConsumer(): Consumer<MouseEvent>? {
        return null
    }

    override fun getIcon(): Icon {
        return if (enabled) {
            QSSIcon.IconStatusOn
        } else {
            QSSIcon.IconStatusOff
        }
    }

    private fun updateWidget() {
        bar?.updateWidget(ID)
    }
}
