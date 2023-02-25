package ui.dendi.contacts.presentation.screen.contacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import ui.dendi.contacts.R
import ui.dendi.contacts.domain.model.Person
import ui.dendi.contacts.navigation.Route
import ui.dendi.contacts.presentation.component.BubbleAnimation
import ui.dendi.contacts.presentation.component.textSizeToDp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    onNextClick: () -> Unit,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel = hiltViewModel(),
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(0xFF2B2E4A),
                                Color(0xFFE84545),
                            )
                        )
                    )
                    .padding(all = 16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.weight(1F),
                        text = stringResource(R.string.contacts),
                        textAlign = TextAlign.Start,
                        fontSize = 40.sp,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    IconButton(
                        onClick = { onNextClick() },
                    ) {
                        Icon(
                            modifier = Modifier.size(40.sp.textSizeToDp()),
                            imageVector = Icons.Filled.Add,
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                val searchText by viewModel.searchText.collectAsState()

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 0.5.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(25.dp),
                        )
                        .height(50.dp)
                        .shadow(3.dp, RoundedCornerShape(25.dp)),
                    value = searchText,
                    onValueChange = viewModel::onSearchTextChange,
                    placeholder = { Text(text = stringResource(R.string.search_hint)) },
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                    },
                    trailingIcon = {
                        if (searchText.isNotBlank()) {
                            IconButton(onClick = {
                                viewModel.onSearchTextChange("")
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = null,
                                )
                            }
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        placeholderColor = Color.LightGray,
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            val contacts by viewModel.contacts.collectAsState()
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                items(contacts, key = { it.id }) { contact ->
                    ContactItem(contact, navHostController)
                }
            }
        }
        BubbleAnimation(
            bubbleColor1 = Color(0xFF903749),
            bubbleColor2 = Color(0xFF53354A),
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun ContactItem(contact: Person, navHostController: NavHostController) {
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
                navHostController.navigate(Route.DETAILS + "/${contact.id}")
            },
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(16.sp.textSizeToDp() * 2)
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
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
    Divider(color = Color.LightGray, thickness = 1.dp)
}