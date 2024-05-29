package irony.ide.plugin.qsseditor

import com.intellij.codeInsight.completion.*
import com.intellij.openapi.diagnostic.logger
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.util.ProcessingContext

private val Log = logger<QSSCompletion>()

class QSSCompletion : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            psiElement().withName("Q"),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet,
                ) {
                }
            },
        )
    }
}
