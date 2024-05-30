/*
 * Copyright (c) 2024. Irony All Rights Reserved.
 * Project: QSSEditor
 * File: QSSListener.kt
 * Date: 2024/5/30 下午9:35
 * Author: Irony
 * Email: 892768447@qq.com
 * Site: https://pyqt.site , https://pyqt5.com
 */

package irony.ide.plugin.qsseditor

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener

class QSSListener : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        if (QSSState.instance::auto.get()) {
            QSSClient.applyStyle(listOf(event.document.text), true)
        }
    }
}
