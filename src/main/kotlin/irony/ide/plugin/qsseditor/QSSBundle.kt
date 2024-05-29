/*
 * Copyright (c) 2024. Irony All Rights Reserved.
 * Project: QSSEditor
 * File: QSSBundle.kt
 * Date: 2024/5/18 上午1:02
 * Author: Irony
 * Email: 892768447@qq.com
 * Site: https://pyqt.site , https://pyqt5.com
 */

package irony.ide.plugin.qsseditor

import com.intellij.DynamicBundle
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.Nullable
import org.jetbrains.annotations.PropertyKey

private const val BUNDLE: @NonNls String = "messages.QSSBundle"

object QSSBundle : DynamicBundle(BUNDLE) {
    fun message(
        key:
            @PropertyKey(resourceBundle = BUNDLE)
            String,
        @Nullable @Nls defaultValue: String,
        vararg params: Any,
    ): @Nls String {
        return messageOrDefault(key, defaultValue, *params)
    }
}
