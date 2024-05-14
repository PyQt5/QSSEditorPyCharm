package irony.pycharm.qsseditor

import com.intellij.openapi.options.BoundSearchableConfigurable
import com.intellij.openapi.options.Configurable.NoScroll
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.bindIntText
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel

class SettingsConfigurable : BoundSearchableConfigurable(
    "QSS Editor",
    LangBundle.message("setting.topic.title", "QSS Editor Configurable")
), NoScroll {

    override fun createPanel() : DialogPanel {
        return panel {
            row(LangBundle.message("setting.host.title", "Host")) {
                textField().bindText(SettingsState.instance::host)
            }
            row(LangBundle.message("setting.port.title", "Port")) {
                textField().bindIntText(SettingsState.instance::port)
            }
            row(LangBundle.message("setting.auto.title","Auto Apply")) {
                checkBox("").bindSelected(SettingsState.instance::auto)
            }
        }
    }
}