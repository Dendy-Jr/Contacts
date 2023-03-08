package ui.dendi.contacts.core.extension

import android.content.Context
import android.content.Intent

fun Context.shareText(text: String) {
    val sendIntent = Intent(
        Intent.ACTION_SEND
    ).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(
        sendIntent, null
    )
    startActivity(shareIntent)
}