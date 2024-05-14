package irony.pycharm.qsseditor

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@State(name = "irony.pycharm.qsseditor.QSSService", storages = [Storage("QSSEditorSetting.xml")])
internal class QSSService(private val cs: CoroutineScope) : PersistentStateComponent<QSSService> {
    private var inited: Boolean = false;
    var auto: Boolean = true
    var host: String = "localhost"
    var port: Int = 61052

    override fun getState(): QSSService {
        return this
    }

    override fun loadState(state: QSSService) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: QSSService
            get() = ApplicationManager.getApplication().getService(
                QSSService::class.java
            )
    }

    fun initialize() {
        if (!inited)    {
        }
    }
}
