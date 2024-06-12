/*
 * Copyright (c) 2024. Irony All Rights Reserved.
 * Project: QSSEditor
 * File: QSSDocJson.kt
 * Date: 2024/6/12 下午9:27
 * Author: Irony
 * Email: 892768447@qq.com
 * Site: https://pyqt.site , https://pyqt5.com
 */

package irony.ide.plugin.qsseditor

import kotlinx.serialization.Serializable

@Serializable
data class QSSDocDescription(val kind: String = "", val value: String = "")

@Serializable
data class QSSDocReference(val name: String = "", val url: String = "")

@Serializable
data class QSSDocProperty(
    val name: String,
    val description: QSSDocDescription = QSSDocDescription(),
    val references: List<QSSDocReference> = listOf(),
    val syntax: String = "",
)

@Serializable
data class QSSDocPseudo(val name: String, val description: String = "")

@Serializable
data class QSSDocJson(
    val version: Float,
    val properties: List<QSSDocProperty>,
    val pseudoClasses: List<QSSDocPseudo>,
    val pseudoElements: List<QSSDocPseudo>,
)
