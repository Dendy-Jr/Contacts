package ui.dendi.contacts.presentation.screen.contact_details.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.dendi.contacts.ui.theme.MineShaft

@Composable
fun InteractionItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconResId: Int,
    @StringRes textResId: Int,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .width(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MineShaft)
            .clickable(enabled) { onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            //TODO create new color -> 0xFF3d3c41
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = if (enabled) Color.White else Color(0xFF3d3c41),
            )
            Text(
                text = stringResource(id = textResId),
                fontSize = 12.sp,
                color = if (enabled) Color.White else Color(0xFF3d3c41),
            )
        }
    }
}