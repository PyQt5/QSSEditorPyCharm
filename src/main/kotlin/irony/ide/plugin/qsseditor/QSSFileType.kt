package irony.ide.plugin.qsseditor

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

class QSSLanguage : Language("QSS") {
    companion object {
        val INSTANCE: QSSLanguage = QSSLanguage()
    }
}

class QSSFileType : LanguageFileType(QSSLanguage.INSTANCE) {
    override fun getName(): String {
        return "qss"
    }

    override fun getDescription(): String {
        return "Qt style sheet file"
    }

    override fun getDefaultExtension(): String {
        return "qss"
    }

    override fun getIcon(): Icon {
        return IconLoader.getIcon("/icons/qss.png", QSSFileType::class.java)
    }

    companion object {
        val INSTANCE: QSSFileType = QSSFileType()
    }
}
