package ui.dendi.contacts.presentation.screen.contacts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.dendi.contacts.R
import ui.dendi.contacts.core.extension_ui.setImageByPath
import ui.dendi.contacts.domain.model.Person
import ui.dendi.contacts.presentation.component.textSizeToDp

@Composable
fun ContactItem(contact: Person, onItemClick: (String) -> Unit) {
    Card(
        modifier = Modifier.padding(bottom = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        val contactName = "${contact.firstName} ${contact.lastName}"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick(contact.id)
                }
                .padding(all = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val contactNameSize = 18.sp
            Image(
                modifier = Modifier
                    .size(contactNameSize.textSizeToDp() * 2)
                    .clip(CircleShape),
                painter = contact.imagePath.setImageByPath(R.drawable.ic_add_photo_gray),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Text(
                text = contactName,
                fontSize = contactNameSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}