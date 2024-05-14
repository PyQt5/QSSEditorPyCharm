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
                textField().bindText(QSSService.instance::host)
            }
            row(QSSBundle.message("setting.port.title", "Port")) {
                textField().bindIntText(QSSService.instance::port)
            }
            row(QSSBundle.message("setting.auto.title","Auto Apply")) {
                checkBox("").bindSelected(QSSService.instance::auto)
            }
        }
    }
}