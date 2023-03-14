package ui.dendi.contacts.presentation.screen.contact_details.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ContactInformation(
    value: String,
    @StringRes titleResId: Int,
    showDivider: Boolean = true,
) {
    if (value.isNotEmpty()) {
        Text(text = stringResource(titleResId))
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        )
        if (showDivider) {
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.LightGray,
                thickness = 0.5.dp,
            )
        }
    }
}