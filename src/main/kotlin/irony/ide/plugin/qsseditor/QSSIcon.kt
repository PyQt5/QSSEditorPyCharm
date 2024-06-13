/*
 * Copyright (c) 2024. Irony All Rights Reserved.
 * Project: QSSEditor
 * File: QSSIcon.kt
 * Date: 2024/6/10 下午9:57
 * Author: Irony
 * Email: 892768447@qq.com
 * Site: https://pyqt.site , https://pyqt5.com
 */

package irony.ide.plugin.qsseditor

import com.intellij.openapi.util.IconLoader

object QSSIcon {
    @JvmField
    val IconProperty = IconLoader.getIcon("/icons/property.svg", QSSIcon::class.java)

    @JvmField
    val IconPseudoClass = IconLoader.getIcon("/icons/pseudo-class.svg", QSSIcon::class.java)

    @JvmField
    val IconPseudoElement = IconLoader.getIcon("/icons/pseudo-element.svg", QSSIcon::class.java)

    @JvmField
    val IconStatusOn = IconLoader.getIcon("/icons/status_on.svg", QSSIcon::class.java)

    @JvmField
    val IconStatusOff = IconLoader.getIcon("/icons/status_off.svg", QSSIcon::class.java)
}
