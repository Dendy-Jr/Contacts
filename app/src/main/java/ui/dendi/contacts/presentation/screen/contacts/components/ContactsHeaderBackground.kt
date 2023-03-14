package ui.dendi.contacts.presentation.screen.contacts.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ContactsHeaderBackground(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        drawCircle(
            radius = size.width,
            center = Offset(center.x, -size.width / 3f),
            color = color,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderBackgroundPreview() {
    ContactsHeaderBackground(
        color = Color.Blue,
        modifier = Modifier.fillMaxSize()
    )
}