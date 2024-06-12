/*
 * Copyright (c) 2024. Irony All Rights Reserved.
 * Project: QSSEditor
 * File: QSSDocProvider.kt
 * Date: 2024/6/10 下午10:25
 * Author: Irony
 * Email: 892768447@qq.com
 * Site: https://pyqt.site , https://pyqt5.com
 */

@file:Suppress("ktlint:standard:no-wildcard-imports")

package irony.ide.plugin.qsseditor

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.io.FileUtilRt
import com.intellij.psi.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import java.net.URL

private val Log = logger<QSSDocProvider>()

class QSSDocProvider : AbstractDocumentationProvider() {
    override fun generateDoc(
        element: PsiElement?,
        originalElement: PsiElement?,
    ): String? {
        if (element != null) {
            // val range = element.getUserData(KEY_RANGE)
            val text = element.getUserData(KEY_TEXT) ?: element.text
            return text
        }
        return null
    }

    override fun generateHoverDoc(
        element: PsiElement,
        originalElement: PsiElement?,
    ): String? {
        return generateDoc(element, originalElement)
    }

    override fun getQuickNavigateInfo(
        element: PsiElement?,
        originalElement: PsiElement?,
    ): String? {
        // Log.info("getQuickNavigateInfo")
        return super.getQuickNavigateInfo(element, originalElement)
    }

    override fun getCustomDocumentationElement(
        editor: Editor,
        file: PsiFile,
        contextElement: PsiElement?,
        targetOffset: Int,
    ): PsiElement? {
        if (contextElement != null && isSupport(file)) {
            var range: TextRange = contextElement.textRange
            var text = contextElement.text
            for (i in 2 downTo 0) {
                range = TextRange(contextElement.textRange.startOffset - i, contextElement.textRange.endOffset)
                text = editor.document.getText(range).trimEnd('!', ':')
                if (getDescription(text).isNotEmpty()) {
                    contextElement.putUserData(KEY_RANGE, range)
                    contextElement.putUserData(KEY_TEXT, getDescription(text))
                    return contextElement
                }
            }
        }
        return super.getCustomDocumentationElement(editor, file, contextElement, targetOffset)
    }

    private fun isSupport(file: PsiFile): Boolean {
        val extension: CharSequence = FileUtilRt.getExtension(file.name, "")
        val ext: String =
            if (extension == "") {
                file.fileType.name.lowercase()
            } else {
                extension.toString()
            }
        return ext == "qss" || ext == "css" || ext == "style"
    }

    private fun getDescription(key: String): String {
        return descriptions.getOrDefault(key, "")
    }

    companion object {
        private val KEY_RANGE: Key<TextRange> = Key.create(TextRange::class.java.name)
        private val KEY_TEXT: Key<String> = Key.create(String::class.java.name)
        val descriptions: MutableMap<String, String> = HashMap()

        fun load(url: URL) {
            if (descriptions.isNotEmpty()) {
                return
            }

            Log.info("load qss.json")
            val json = Json.decodeFromString<QSSDocJson>(url.readText())

            Log.info("load qss properties")
            json.properties.forEach { v ->
                var syntax = ""
                if (v.syntax.isNotEmpty()) {
                    syntax = "(" + v.syntax.replace("\n", " ") + ")"
                }
                var refs = ""
                v.references.forEach { vv ->
                    refs += "[${vv.name}](${vv.url})"
                }
                descriptions[
                    v.name,
                ] = generateHtml(v.description.value + "\n\n" + syntax + "\n\n" + refs)
            }

            Log.info("load qss pseudoClasses")
            json.pseudoClasses.forEach { v ->
                descriptions[
                    v.name,
                ] = generateHtml(v.description)
            }

            Log.info("load qss pseudoElements")
            json.pseudoElements.forEach { v ->
                descriptions[
                    v.name,
                ] = generateHtml(v.description)
            }
        }

        private fun generateHtml(text: String): String {
            val flavour = CommonMarkFlavourDescriptor()
            val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(text)
            return HtmlGenerator(text, parsedTree, flavour, false).generateHtml()
        }
    }
}
