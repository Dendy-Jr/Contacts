package ui.dendi.contacts.presentation.screen.contacts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
    val isEnglishLetters = contact.firstName.all() { it in 'a'..'z' || it in 'A'..'Z' }
    val contactName = if (isEnglishLetters) {
        "${contact.firstName} ${contact.lastName}"
    } else {
        "${contact.lastName} ${contact.firstName}"
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable {
                onItemClick(contact.id)
            },
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val contactNameSize = 18.sp
        Image(
            modifier = Modifier
                .size(contactNameSize.textSizeToDp() * 2)
                .clip(CircleShape),
            painter = contact.imagePath.setImageByPath(R.drawable.ic_add_photo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Text(
            text = contactName,
            fontSize = contactNameSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.White,
        )
    }
}