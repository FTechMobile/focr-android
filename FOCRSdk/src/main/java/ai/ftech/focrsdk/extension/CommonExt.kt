package ai.ftech.focrsdk.extension

import ai.ftech.focrsdk.sdk.OCRManager
import android.content.Context
import androidx.annotation.StringRes
import java.io.File

fun String?.getString(defaultValue: String = ""): String {
    return if(this.isNullOrEmpty()) defaultValue else this
}

fun getAppString(
    @StringRes stringId: Int,
    context: Context? = OCRManager.getApplicationContext()
): String {
    return context?.getString(stringId) ?: ""
}

fun File.copyTo(target: File): Boolean {
    inputStream().use { input ->
        target.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return true
}

fun Int.dpToPx(context: Context = OCRManager.getApplicationContext()): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}