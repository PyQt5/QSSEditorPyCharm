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
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.util.io.FileUtilRt
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.util.ProcessingContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.net.URL

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
                    result.addAllElements(elements)
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

    companion object {
        val elements: MutableList<LookupElement> = ArrayList()

        fun load(url: URL) {
            if (elements.isNotEmpty()) {
                return
            }
            Log.info("prepare some qss class")
            val words =
                listOf(
                    "QAbstractButton",
                    "QAbstractItemView",
                    "QAbstractScrollArea",
                    "QAbstractSlider",
                    "QAbstractSpinBox",
                    "QCalendarWidget",
                    "QCheckBox",
                    "QColorDialog",
                    "QColumnView",
                    "QComboBox",
                    "QCommandLinkButton",
                    "QDateEdit",
                    "QDateTimeEdit",
                    "QDial",
                    "QDialog",
                    "QDialogButtonBox",
                    "QDockWidget",
                    "QDoubleSpinBox",
                    "QErrorMessage",
                    "QFileDialog",
                    "QFocusFrame",
                    "QFontComboBox",
                    "QFontDialog",
                    "QFrame",
                    "QGraphicsView",
                    "QGroupBox",
                    "QHeaderView",
                    "QInputDialog",
                    "QKeySequenceEdit",
                    "QLCDNumber",
                    "QLabel",
                    "QLineEdit",
                    "QListView",
                    "QListWidget",
                    "QMainWindow",
                    "QMdiArea",
                    "QMdiSubWindow",
                    "QMenu",
                    "QMenuBar",
                    "QMessageBox",
                    "QOpenGLWidget",
                    "QPlainTextEdit",
                    "QProgressBar",
                    "QProgressDialog",
                    "QPushButton",
                    "QRadioButton",
                    "QRubberBand",
                    "QScrollArea",
                    "QScrollBar",
                    "QSizeGrip",
                    "QSlider",
                    "QSpinBox",
                    "QSplashScreen",
                    "QSplitter",
                    "QSplitterHandle",
                    "QStackedWidget",
                    "QStatusBar",
                    "QTabBar",
                    "QTabWidget",
                    "QTableView",
                    "QTableWidget",
                    "QTextBrowser",
                    "QTextEdit",
                    "QTimeEdit",
                    "QToolBar",
                    "QToolBox",
                    "QToolButton",
                    "QTreeView",
                    "QTreeWidget",
                    "QUndoView",
                    "QWidget",
                    "QWizard",
                    "QWizardPage",
                )
            words.forEach { v ->
                elements.add(LookupElementBuilder.create(v))
            }

            Log.info("load qss.json")
            val json = Json.parseToJsonElement(url.readText()).jsonObject
            Log.info("load qss properties")
            json.getValue("properties").jsonArray.forEach { v ->
                elements.add(LookupElementBuilder.create(v.jsonObject.getValue("name").jsonPrimitive.content))
            }
            Log.info("load qss pseudoClasses")
            json.getValue("pseudoClasses").jsonArray.forEach { v ->
                elements.add(LookupElementBuilder.create(v.jsonObject.getValue("name").jsonPrimitive.content))
            }
            Log.info("load qss pseudoElements")
            json.getValue("pseudoElements").jsonArray.forEach { v ->
                elements.add(LookupElementBuilder.create(v.jsonObject.getValue("name").jsonPrimitive.content))
            }
        }
    }
}
