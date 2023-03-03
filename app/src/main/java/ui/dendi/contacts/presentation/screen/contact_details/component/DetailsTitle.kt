package ui.dendi.contacts.presentation.screen.contact_details.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.dendi.contacts.presentation.component.textSizeToDp

@Composable
fun DetailsTitle(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    @StringRes textId: Int,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val textSize = 16.sp
        Icon(
            modifier = Modifier.size(textSize.textSizeToDp()),
            painter = painterResource(iconId),
            contentDescription = null,
            tint = Color.White,
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = stringResource(textId),
            fontSize = textSize,
            fontWeight = FontWeight.Medium,
            color = Color.White,
        )
    }
    Spacer(modifier = Modifier.height(6.dp))
}