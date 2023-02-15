package ui.dendi.contacts.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.Black,
    fontSize: TextUnit = 18.sp,
    gradient: Brush,
    fontWeight: FontWeight = FontWeight.Normal,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.padding(vertical = 18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
        enabled = enabled,
    )
    {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, color = textColor, fontSize = fontSize, fontWeight = fontWeight)
        }
    }
}