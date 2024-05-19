/*
 * Copyright (c) 2024. Irony All Rights Reserved.
 * Project: QSSEditor
 * File: QSSAction.kt
 * Date: 2024/5/18 上午1:02
 * Author: Irony
 * Email: 892768447@qq.com
 * Site: https://pyqt.site , https://pyqt5.com
 */

package irony.pycharm.qsseditor

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.vfs.VirtualFile

private val Log = logger<QSSAction>()

class QSSAction : AnAction() {
    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun update(e: AnActionEvent) {
        // 默认菜单不可用，需要判断当前文件类型
        e.presentation.isEnabledAndVisible = false
        val file: VirtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val ext: String = file.extension?.lowercase() ?: file.fileType.name.lowercase()
        if (!(ext == "qss" || ext == "css" || ext == "style")) {
            return
        }
        e.presentation.isEnabledAndVisible = true
    }

    override fun actionPerformed(e: AnActionEvent) {
        val editor: Editor = e.getData(CommonDataKeys.EDITOR) ?: return
        // 获取编辑器中的文本
        val text: String = editor.selectionModel.selectedText ?: editor.document.text
        if (text.isEmpty()) {
            return
        }
    }
}
