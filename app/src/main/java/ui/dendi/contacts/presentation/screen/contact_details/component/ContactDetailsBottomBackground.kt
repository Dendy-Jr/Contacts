package ui.dendi.contacts.presentation.screen.contact_details.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ContactDetailsBottomBackground(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        drawCircle(
            radius = size.width,
            center = Offset(center.x * 2f, size.width * 2.2f),
            color = color,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBackgroundPreview() {
    ContactDetailsBottomBackground(Color.Red, modifier = Modifier.fillMaxSize())
}