package ui.dendi.contacts.presentation.screen.create_contact.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CreateContactBottomBackground(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        drawCircle(
            radius = size.width,
            center = Offset(center.x * 2.5f, size.width * 2f),
            color = color,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBackgroundPreview() {
    CreateContactBottomBackground(Color.Red, modifier = Modifier.fillMaxSize())
}