package irony.pycharm.qsseditor

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

class ConfigState : BaseState() {
    var auto: Boolean by property(true)
    var host: String? by string("localhost")
    var port: Int by property(61052)
}

@State(name = "irony.pycharm.qsseditor.QSSState", storages = [Storage("QSSEditorSetting.xml", roamingType = RoamingType.DEFAULT)])
internal class QSSState() : SimplePersistentStateComponent<ConfigState>(ConfigState()) {

    var auto: Boolean
        get() = state.auto
        set(value) {
            state.auto = value
        }

    var host: String
        get() = state.host ?: "localhost"
        set(value) {
            state.host = value
        }

    var port: Int
        get() = state.port
        set(value) {
            state.port = value
        }

    companion object {
        val instance: QSSState
            get() = ApplicationManager.getApplication().getService(
                QSSState::class.java
            )
    }
}
