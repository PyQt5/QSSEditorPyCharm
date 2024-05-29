package irony.ide.plugin.qsseditor

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener

class QSSListener : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        if (QSSState.instance::auto.get()) {
            QSSClient.applyStyle(listOf(event.document.text), true)
        }
    }
}
