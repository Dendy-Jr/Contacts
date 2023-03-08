package ui.dendi.contacts.core.extension_ui

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun String.setImageByPath(@DrawableRes imageResId: Int): Painter {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(this)
            .size(Size.ORIGINAL)
            .build()
    )
    return if (this.isNotEmpty()) {
        painter
    } else {
        painterResource(id = imageResId)
    }
}

@Composable
fun String.setImageByPath(): Painter {
    return rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(this)
            .size(Size.ORIGINAL)
            .build()
    )
}

//TODO Move to another file
@Composable
fun Uri.setImageByPath(): Painter {
    return rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(this)
            .size(Size.ORIGINAL)
            .build()
    )
}