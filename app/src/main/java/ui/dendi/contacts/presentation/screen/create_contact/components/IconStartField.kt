package ui.dendi.contacts.presentation.screen.create_contact.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconStartField(
    size: Dp = 25.dp,
    iconResId: Int,
) {
    Icon(
        modifier = Modifier.size(size),
        painter = painterResource(id = iconResId),
        contentDescription = null,
    )
}