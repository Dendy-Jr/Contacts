package ui.dendi.contacts.presentation.screen.create_contact.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SectionTextTitle(@StringRes textId: Int) {
    Text(
        modifier = Modifier.padding(start = 30.dp),
        text = stringResource(textId),
        fontWeight = FontWeight.Medium,
        color = Color.White,
    )
}