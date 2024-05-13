package irony.pycharm.qsseditor

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "irony.pycharm.qsseditor.SettingsState", storages = [Storage("QSSEditorSetting.xml")])
internal class SettingsState : PersistentStateComponent<SettingsState> {
    var auto: Boolean = true
    var host: String = "localhost"
    var port: Int = 61052

    override fun getState(): SettingsState {
        return this
    }

    override fun loadState(state: SettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: SettingsState
            get() = ApplicationManager.getApplication().getService(
                SettingsState::class.java
            )
    }
}
