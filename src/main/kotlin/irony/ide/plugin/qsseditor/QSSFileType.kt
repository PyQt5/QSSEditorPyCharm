/*
 * Copyright (c) 2024. Irony All Rights Reserved.
 * Project: QSSEditor
 * File: QSSFileType.kt
 * Date: 2024/5/30 下午9:35
 * Author: Irony
 * Email: 892768447@qq.com
 * Site: https://pyqt.site , https://pyqt5.com
 */

package irony.ide.plugin.qsseditor

import com.intellij.lang.css.CSSLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

class QSSLanguage : CSSLanguage("QSS", "text/css") {
    companion object {
        val INSTANCE: QSSLanguage = QSSLanguage()
    }
}

class QSSFileType : LanguageFileType(QSSLanguage.INSTANCE) {
    override fun getName(): String {
        return "QSS"
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
