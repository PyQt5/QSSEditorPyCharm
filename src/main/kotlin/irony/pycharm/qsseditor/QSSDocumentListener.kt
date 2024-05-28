package irony.pycharm.qsseditor

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener

class QSSDocumentListener : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        if (QSSState.instance::auto.get()) {
            QSSClient.applyStyle(listOf(event.document.text), true)
        }
    }
}
