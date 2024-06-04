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
import com.intellij.openapi.util.io.FileUtilRt
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.util.ProcessingContext

private val Log = logger<QSSCompletion>()

class QSSCompletion : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            psiElement(),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet,
                ) {
                    if (!isSupport(parameters)) {
                        return
                    }

                    Log.info("addCompletions")
                    result.addElement(LookupElementBuilder.create("QPushButton"))
                    result.addElement(LookupElementBuilder.create("QLabel"))
                }
            },
        )
    }

    private fun isSupport(parameters: CompletionParameters): Boolean {
        val file = parameters.originalFile
        val extension: CharSequence = FileUtilRt.getExtension(file.name, "")
        val ext: String =
            if (extension == "") {
                file.fileType.name.lowercase()
            } else {
                extension.toString()
            }
        return ext == "qss" || ext == "css" || ext == "style"
    }
}
