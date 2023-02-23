package ui.dendi.contacts.presentation.screen.create_contact.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ui.dendi.contacts.R

@Composable
fun IconEndField(onClick: () -> Unit, showCollapsedFields: () -> Boolean) {
    IconButton(
        modifier = Modifier.size(25.dp),
        onClick = { onClick() },
    ) {
        Icon(
            painter = painterResource(id = if (showCollapsedFields.invoke()) R.drawable.ic_arrow_top else R.drawable.ic_arrow_bottom),
            contentDescription = null,
            tint = Color.White,
        )
    }
}