package irony.pycharm.qsseditor

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project

private val LOG = logger<QSSAction>()

class QSSAction : AnAction(QSSBundle.message("action.apply.title", "Apply Style")) {

    override fun update(e: AnActionEvent) {
        // 默认菜单不可用，需要判断当前文件类型
        e.presentation.isEnabledAndVisible = false
        val project: Project = e.project ?: return
        val editor: Editor = e.getData(CommonDataKeys.EDITOR) ?: return
    }

    override fun actionPerformed(e: AnActionEvent) {

    }
}
