package ui.dendi.contacts.presentation.screen.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ui.dendi.contacts.R
import ui.dendi.contacts.core.extension_ui.circleLayout
import ui.dendi.contacts.presentation.screen.contacts.components.ContactItem
import ui.dendi.contacts.presentation.screen.contacts.components.SearchTextField
import ui.dendi.contacts.ui.theme.Tundora

@Composable
fun ContactsScreen(
    onNextClick: () -> Unit,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel = hiltViewModel(),
) {
    val contacts by viewModel.contacts.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Tundora)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
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
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                    IconButton(
                        modifier = Modifier
                            .size(35.dp)
                            .background(Color.Gray, shape = CircleShape)
                            .circleLayout()
                            .padding(8.dp),
                        onClick = { onNextClick() },
                    ) {
                        Icon(

                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = "Add",
                            tint = Color.White,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                val searchText by viewModel.searchText.collectAsState()
                Row(modifier = Modifier.fillMaxWidth()) {
                    SearchTextField(
                        searchText = searchText,
                        onSearchTextChange = viewModel::onSearchTextChange,
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .testTag("ContactList"),
            ) {
                itemsIndexed(contacts) { index, contact ->
                    ContactItem(contact, navHostController)

                    if (index < contacts.lastIndex) {
                        Divider(color = Color.LightGray, thickness = 1.dp)
                    }
                }
            }
        }
    }
}