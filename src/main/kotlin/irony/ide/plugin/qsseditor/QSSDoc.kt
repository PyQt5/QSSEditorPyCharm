/*
 * Copyright (c) 2024. Irony All Rights Reserved.
 * Project: QSSEditor
 * File: QSSDoc.kt
 * Date: 2024/6/10 下午10:25
 * Author: Irony
 * Email: 892768447@qq.com
 * Site: https://pyqt.site , https://pyqt5.com
 */

@file:Suppress("ktlint:standard:no-wildcard-imports")

package irony.ide.plugin.qsseditor

import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.io.FileUtilRt
import com.intellij.psi.*
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.SearchScope
import kotlinx.serialization.json.*
import java.net.URL
import javax.swing.Icon

private val Log = logger<QSSDoc>()

class QSSPsiElement(context: PsiElement) : PsiElement {
    private val that: PsiElement = context

    override fun <T : Any?> getUserData(key: Key<T>): T? {
        return that.getUserData(key)
    }

    override fun <T : Any?> putUserData(
        key: Key<T>,
        value: T?,
    ) {
        that.putUserData(key, value)
    }

    override fun getIcon(flags: Int): Icon {
        return that.getIcon(flags)
    }

    override fun getProject(): Project {
        return that.project
    }

    override fun getLanguage(): Language {
        return that.language
    }

    override fun getManager(): PsiManager {
        return that.manager
    }

    override fun getChildren(): Array<PsiElement> {
        return that.children
    }

    override fun getParent(): PsiElement {
        return that.parent
    }

    override fun getFirstChild(): PsiElement {
        return that.firstChild
    }

    override fun getLastChild(): PsiElement {
        return that.lastChild
    }

    override fun getNextSibling(): PsiElement {
        return that.nextSibling
    }

    override fun getPrevSibling(): PsiElement {
        return that.prevSibling
    }

    override fun getContainingFile(): PsiFile {
        return that.containingFile
    }

    override fun getTextRange(): TextRange {
        return that.textRange
    }

    override fun getStartOffsetInParent(): Int {
        return that.startOffsetInParent
    }

    override fun getTextLength(): Int {
        return that.textLength
    }

    override fun findElementAt(offset: Int): PsiElement? {
        return that.findElementAt(offset)
    }

    override fun findReferenceAt(offset: Int): PsiReference? {
        return that.findReferenceAt(offset)
    }

    override fun getTextOffset(): Int {
        return that.textOffset
    }

    override fun getText(): String {
        return that.text
    }

    override fun textToCharArray(): CharArray {
        return that.textToCharArray()
    }

    override fun getNavigationElement(): PsiElement {
        return that.navigationElement
    }

    override fun getOriginalElement(): PsiElement {
        return that.originalElement
    }

    override fun textMatches(text: CharSequence): Boolean {
        return that.textMatches(text)
    }

    override fun textMatches(element: PsiElement): Boolean {
        return that.textMatches(element)
    }

    override fun textContains(c: Char): Boolean {
        return that.textContains(c)
    }

    override fun accept(visitor: PsiElementVisitor) {
        return that.accept(visitor)
    }

    override fun acceptChildren(visitor: PsiElementVisitor) {
        return that.acceptChildren(visitor)
    }

    override fun copy(): PsiElement {
        return that.copy()
    }

    override fun add(element: PsiElement): PsiElement {
        return that.add(element)
    }

    override fun addBefore(
        element: PsiElement,
        anchor: PsiElement?,
    ): PsiElement {
        return that.addBefore(element, anchor)
    }

    override fun addAfter(
        element: PsiElement,
        anchor: PsiElement?,
    ): PsiElement {
        return that.addAfter(element, anchor)
    }

    @Deprecated("Deprecated in Java")
    override fun checkAdd(element: PsiElement) {
        return that.checkAdd(element)
    }

    override fun addRange(
        first: PsiElement?,
        last: PsiElement?,
    ): PsiElement {
        return that.addRange(first, last)
    }

    override fun addRangeBefore(
        first: PsiElement,
        last: PsiElement,
        anchor: PsiElement?,
    ): PsiElement {
        return that.addRangeBefore(first, last, anchor)
    }

    override fun addRangeAfter(
        first: PsiElement?,
        last: PsiElement?,
        anchor: PsiElement?,
    ): PsiElement {
        return that.addRangeAfter(first, last, anchor)
    }

    override fun delete() {
        return that.delete()
    }

    @Deprecated("Deprecated in Java")
    override fun checkDelete() {
        return that.checkDelete()
    }

    override fun deleteChildRange(
        first: PsiElement?,
        last: PsiElement?,
    ) {
        return that.deleteChildRange(first, last)
    }

    override fun replace(newElement: PsiElement): PsiElement {
        return that.replace(newElement)
    }

    override fun isValid(): Boolean {
        return that.isValid
    }

    override fun isWritable(): Boolean {
        return that.isWritable
    }

    override fun getReference(): PsiReference? {
        return that.reference
    }

    override fun getReferences(): Array<PsiReference> {
        return that.references
    }

    override fun <T : Any?> getCopyableUserData(key: Key<T>): T? {
        return that.getCopyableUserData(key)
    }

    override fun <T : Any?> putCopyableUserData(
        key: Key<T>,
        value: T?,
    ) {
        return that.putCopyableUserData(key, value)
    }

    override fun processDeclarations(
        processor: PsiScopeProcessor,
        state: ResolveState,
        lastParent: PsiElement?,
        place: PsiElement,
    ): Boolean {
        return that.processDeclarations(processor, state, lastParent, place)
    }

    override fun getContext(): PsiElement? {
        return that.context
    }

    override fun isPhysical(): Boolean {
        return that.isPhysical
    }

    override fun getResolveScope(): GlobalSearchScope {
        return that.resolveScope
    }

    override fun getUseScope(): SearchScope {
        return that.useScope
    }

    override fun getNode(): ASTNode {
        return that.node
    }

    override fun isEquivalentTo(another: PsiElement?): Boolean {
        return that.isEquivalentTo(another)
    }
}

class QSSDoc : AbstractDocumentationProvider() {
    override fun generateDoc(
        element: PsiElement?,
        originalElement: PsiElement?,
    ): String? {
        Log.info("generateDoc")
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
                text = editor.document.getText(range)
                Log.info(text)
                if (getDescription(text).isNotEmpty()) {
                    Log.info(getDescription(text))
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
            val json = Json.parseToJsonElement(url.readText()).jsonObject

            Log.info("load qss properties")
            json.getValue("properties").jsonArray.forEach { v ->
                val doc = v.jsonObject.getValue("description").jsonObject.getValue("value").jsonPrimitive.content
                var syntax = ""
                if (v.jsonObject.containsKey("syntax")) {
                    syntax = "(" + v.jsonObject.getValue("syntax").jsonPrimitive.content.replace("\n", " ") + ")"
                }
                var refs = ""
                v.jsonObject.getValue("references").jsonArray.forEach { vv ->
                    refs += "[${vv.jsonObject.getValue(
                        "name",
                    ).jsonPrimitive.content}](${vv.jsonObject.getValue("url").jsonPrimitive.content})"
                }
                descriptions[
                    v.jsonObject.getValue(
                        "name",
                    ).jsonPrimitive.content,
                ] = (doc + "\n\n" + syntax + "\n\n" + refs)
            }

            Log.info("load qss pseudoClasses")
            json.getValue("pseudoClasses").jsonArray.forEach { v ->
                descriptions[
                    v.jsonObject.getValue(
                        "name",
                    ).jsonPrimitive.content,
                ] = v.jsonObject.getValue("description").jsonPrimitive.content
            }

            Log.info("load qss pseudoElements")
            json.getValue("pseudoElements").jsonArray.forEach { v ->
                descriptions[
                    v.jsonObject.getValue(
                        "name",
                    ).jsonPrimitive.content,
                ] = v.jsonObject.getValue("description").jsonPrimitive.content
            }
        }
    }
}
