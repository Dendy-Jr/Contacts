package ui.dendi.contacts.presentation.screen.edit_contact.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EditContactBottomBackground(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        drawCircle(
            radius = size.width,
            center = Offset(center.x * 2f, size.width * 1.9f),
            color = color,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBackgroundPreview() {
    EditContactBottomBackground(Color.Red, modifier = Modifier.fillMaxSize())
}