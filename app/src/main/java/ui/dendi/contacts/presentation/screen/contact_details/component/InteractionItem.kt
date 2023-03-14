package ui.dendi.contacts.presentation.screen.contact_details.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.dendi.contacts.R

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
            .height(60.dp)
            .clickable(enabled) { onClick() },
    ) {
        Card(
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    tint = if (enabled) Color.Black else Color.LightGray,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = textResId),
                    fontSize = 12.sp,
                    color = if (enabled) Color.Black else Color.LightGray
                )
            }
        }
        if (enabled.not()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.ic_abort),
                    contentDescription = null,
                    alpha = 0.6f
                )
            }
        }
    }
}