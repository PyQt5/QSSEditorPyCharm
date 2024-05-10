package irony.pycharm.qsseditor

import com.intellij.openapi.options.BoundSearchableConfigurable
import com.intellij.openapi.options.Configurable.NoScroll
import com.intellij.ui.dsl.builder.panel

class QSSConfigurable : BoundSearchableConfigurable(
    "QSS",
    "QSS Editor Configurable"
), NoScroll {
    override fun createPanel() =  panel {
        row {
        }
    }
}