package ui.dendi.contacts.presentation.screen.contacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
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
import ui.dendi.contacts.ui.theme.Acme

@OptIn(ExperimentalMaterial3Api::class)
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
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val titleTextSize: TextUnit = 36.sp
            val iconSize: Dp = with(LocalDensity.current) {
                titleTextSize.toDp()
            }
            Text(
                modifier = Modifier.weight(1F),
                text = stringResource(R.string.contacts_title),
                textAlign = TextAlign.Start,
                fontSize = titleTextSize,
                fontWeight = FontWeight.ExtraBold,
            )
            IconButton(
                onClick = { onNextClick() },
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        val searchText by viewModel.searchText.collectAsState()
        val contacts by viewModel.contacts.collectAsState()

        //TODO  143 contacts / 1 contact /
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 0.5.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(25.dp),
                )
                .height(55.dp)
                .shadow(3.dp, RoundedCornerShape(25.dp)),
            value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            placeholder = { Text(text = stringResource(R.string.search_hint)) },
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier,
        ) {
            if (contacts.isNotEmpty()) {
                item {
                    Divider(color = Color.LightGray, thickness = 1.dp)
                }
            }
            items(contacts) { contact ->
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
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //TODO TODO
        Image(
            modifier = Modifier
                .size(iconSize * 2)
                .clip(CircleShape),
            painter = if (contact.imagePath.isNotEmpty()) {
                rememberAsyncImagePainter(contact.imagePath)
            } else {
                painterResource(id = R.drawable.ic_add_photo)
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Text(
            text = contactName,
            fontSize = contactTextSize,
            fontFamily = Acme
        )
    }
    Divider(color = Color.LightGray, thickness = 1.dp)
}