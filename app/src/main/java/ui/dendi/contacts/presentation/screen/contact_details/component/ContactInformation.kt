package ui.dendi.contacts.presentation.screen.contact_details.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
    @StringRes titleResId: Int,
    showDivider: Boolean = true,
    value: String,
) {
    if (value.isNotEmpty()) {
        Text(
            text = stringResource(titleResId),
            fontWeight = FontWeight.Normal,
            color = Color.White,
        )
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF417efd),
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