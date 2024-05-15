package irony.pycharm.qsseditor

import com.intellij.openapi.options.BoundSearchableConfigurable
import com.intellij.openapi.options.Configurable.NoScroll
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.bindIntText
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel

class QSSConfigurable : BoundSearchableConfigurable(
    "QSS Editor",
    QSSBundle.message("setting.topic.title", "QSS Editor Configurable")
), NoScroll {

    override fun createPanel() : DialogPanel {
        return panel {
            row(QSSBundle.message("setting.host.title", "Host")) {
                textField().bindText(QSSState.instance::host)
            }
            row(QSSBundle.message("setting.port.title", "Port")) {
                intTextField(IntRange(1000, 65535)).bindIntText(QSSState.instance::port)
            }
            row(QSSBundle.message("setting.auto.title","Auto Apply")) {
                checkBox("").bindSelected(QSSState.instance::auto)
            }
        }
    }
}