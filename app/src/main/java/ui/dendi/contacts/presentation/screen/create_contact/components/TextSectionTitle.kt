package ui.dendi.contacts.presentation.screen.create_contact.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextSectionTitle(@StringRes textId: Int) {
    Text(
        modifier = Modifier.padding(start = 30.dp),
        text = stringResource(textId),
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
    )
}