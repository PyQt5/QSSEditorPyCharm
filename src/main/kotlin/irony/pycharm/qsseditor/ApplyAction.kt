package irony.pycharm.qsseditor

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages


class ApplyAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        // TODO: insert action logic here
        val project: Project? = e.project
        Messages.showInfoMessage(e.getData(CommonDataKeys.PSI_ELEMENT).toString(), "Caret Parameters Inside The Editor")
    }
}
