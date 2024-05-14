package irony.pycharm.qsseditor

import com.intellij.DynamicBundle
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.Nullable
import org.jetbrains.annotations.PropertyKey
import java.util.function.Supplier

object QSSBundle {
    private const val BUNDLE: @NonNls String = "messages.QSSBundle"
    private val INSTANCE = DynamicBundle(QSSBundle::class.java, BUNDLE)

    fun message(
        key: @PropertyKey(resourceBundle = BUNDLE) String,
        vararg params: Any
    ): @Nls String {
        return INSTANCE.messageOrDefault(key, key, *params)
    }

    fun message(
        key: @PropertyKey(resourceBundle = BUNDLE) String,
        @Nullable @Nls defaultValue: String,
        vararg params: Any
    ): @Nls String {
        return INSTANCE.messageOrDefault(key, defaultValue, *params)
    }

    fun lazyMessage(
        @PropertyKey(resourceBundle = BUNDLE) key: String,
        vararg params: Any
    ): Supplier<@Nls String> {
        return INSTANCE.getLazyMessage(key, *params)
    }
}
