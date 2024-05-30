/*
 * Copyright (c) 2024. Irony All Rights Reserved.
 * Project: QSSEditor
 * File: QSSCompletion.kt
 * Date: 2024/5/30 下午9:35
 * Author: Irony
 * Email: 892768447@qq.com
 * Site: https://pyqt.site , https://pyqt5.com
 */

@file:Suppress("ktlint:standard:no-wildcard-imports")

package irony.ide.plugin.qsseditor

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.diagnostic.logger
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.xml.XmlAttributeValue
import com.intellij.util.ProcessingContext

private val Log = logger<QSSCompletion>()

class QSSCompletion : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            psiElement(XmlAttributeValue::class.java),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet,
                ) {
                    Log.info("addCompletions")
                    result.addElement(LookupElementBuilder.create("QPushButton"))
                    result.addElement(LookupElementBuilder.create("QLabel"))
                }
            },
        )
    }
}
