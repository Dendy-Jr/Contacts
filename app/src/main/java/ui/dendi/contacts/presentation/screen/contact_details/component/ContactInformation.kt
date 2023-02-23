package ui.dendi.contacts.presentation.screen.contact_details.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
    titleColor: Color = Color.White,
    value: String,
    valueColor: Color = Color.White,
) {
    if (value.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(titleResId),
            color = titleColor,
        )
        Text(
            text = value,
            fontSize = 20.sp,
            color = valueColor,
            fontWeight = FontWeight.Medium,
        )
    }
}