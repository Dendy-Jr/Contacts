package ui.dendi.contacts.presentation.screen.contacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import ui.dendi.contacts.R
import ui.dendi.contacts.domain.Person

@Composable
fun ContactsScreen(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit,
    viewModel: ContactsViewModel = hiltViewModel(),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1F),
                text = stringResource(R.string.contacts_title),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
            )
            IconButton(
                modifier = Modifier.size(30.dp),
                onClick = { onNextClick() },
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                )
            }
        }

        LazyColumn(
            modifier = Modifier,
        ) {
            if (viewModel.contacts.isNotEmpty()) {
                item {
                    Divider(color = Color.LightGray, thickness = 1.dp)
                }
            }
            items(viewModel.contacts) { contact ->
                ContactItem(contact)
            }
        }
    }
}

@Composable
private fun ContactItem(contact: Person) {
    val isEnglishLetters = contact.firstName.all() { it in 'a'..'z' || it in 'A'..'Z' }
    val contactName = if (isEnglishLetters) {
        "${contact.firstName} ${contact.lastName}"
    } else {
        "${contact.lastName} ${contact.firstName}"
    }

    val contactTextSize: TextUnit = 20.sp
    val iconSize: Dp = with(LocalDensity.current) {
        contactTextSize.toDp()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(iconSize * 2)
                .clip(CircleShape),
            painter = rememberAsyncImagePainter(contact.imagePath),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = contactName,
            fontSize = contactTextSize,
        )
    }
    Divider(color = Color.LightGray, thickness = 1.dp)
}