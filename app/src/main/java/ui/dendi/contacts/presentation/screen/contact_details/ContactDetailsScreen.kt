package ui.dendi.contacts.presentation.screen.contact_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import ui.dendi.contacts.R
import ui.dendi.contacts.domain.model.Person
import ui.dendi.contacts.presentation.screen.contact_details.component.CardSection
import ui.dendi.contacts.presentation.screen.contact_details.component.ContactInformation

@Composable
fun ContactDetailsScreen(
    id: String,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ContactDetailsViewModel = hiltViewModel(),
) {
    val contact = produceState<Person?>(initialValue = null) {
        value = viewModel.getContactDetails(id)
    }.value ?: return

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(0xFF11999E),
                            Color(0xFF40514E),
                        )
                    )
                )
                .padding(all = 16.dp),
        ) {
            IconButton(
                modifier = Modifier.size(25.dp),
                onClick = { onBackClicked() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                )
            }
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFE4F9F5))
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape),
                    painter =
                    if (contact.imagePath.isNotEmpty()) {
                        rememberAsyncImagePainter(contact.imagePath)
                    } else {
                        painterResource(
                            id = R.drawable.ic_add_photo
                        )
                    },
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Text(
                    text = "${contact.lastName} ${contact.firstName}",
                    fontSize = 24.sp
                )
                Text(
                    text = contact.phoneNumber.number,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            CardSection {
                ContactInformation(
                    titleResId = R.string.full_name,
                    value = contact.fullName,
                )
                ContactInformation(
                    titleResId = R.string.gender,
                    value = contact.gender,
                )
                ContactInformation(
                    titleResId = R.string.birthday,
                    value = contact.birthday,
                )
                ContactInformation(
                    titleResId = R.string.occupation,
                    value = contact.occupation,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            CardSection {
                val phone = contact.phoneNumber
                ContactInformation(
                    titleResId = R.string.phone_number,
                    value = phone.number,
                )
                ContactInformation(
                    titleResId = R.string.label,
                    value = phone.label,
                )
                ContactInformation(
                    titleResId = R.string.type,
                    value = phone.type,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            CardSection {
                val address = contact.postalAddress
                ContactInformation(
                    titleResId = R.string.street,
                    value = address.street,
                )
                ContactInformation(
                    titleResId = R.string.city,
                    value = address.city,
                )
                ContactInformation(
                    titleResId = R.string.region,
                    value = address.region,
                )
                ContactInformation(
                    titleResId = R.string.neighborhood,
                    value = address.neighborhood,
                )
                ContactInformation(
                    titleResId = R.string.post_code,
                    value = address.postCode,
                )
                ContactInformation(
                    titleResId = R.string.country,
                    value = address.country,
                )
                ContactInformation(
                    titleResId = R.string.label,
                    value = address.label,
                )
                ContactInformation(
                    titleResId = R.string.type,
                    value = address.type,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            CardSection {
                val email = contact.emailAddress
                ContactInformation(
                    titleResId = R.string.link,
                    value = email.link,
                )
                ContactInformation(
                    titleResId = R.string.label,
                    value = email.label,
                )
                ContactInformation(
                    titleResId = R.string.type,
                    value = email.type,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            CardSection {
                val organization = contact.organization
                ContactInformation(
                    titleResId = R.string.organization_name,
                    value = organization.name,
                )
                ContactInformation(
                    titleResId = R.string.label,
                    value = organization.label,
                )
                ContactInformation(
                    titleResId = R.string.job_title,
                    value = organization.jobTitle,
                )
                ContactInformation(
                    titleResId = R.string.job_description,
                    value = organization.jobDescription,
                )
                ContactInformation(
                    titleResId = R.string.department,
                    value = organization.department,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            CardSection {
                val website = contact.website
                ContactInformation(
                    titleResId = R.string.department,
                    value = website.link,
                )
                ContactInformation(
                    titleResId = R.string.department,
                    value = website.label,
                )
                ContactInformation(
                    titleResId = R.string.department,
                    value = website.type,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            CardSection {
                val calendar = contact.calendar
                ContactInformation(
                    titleResId = R.string.link,
                    value = calendar.link,
                )
                ContactInformation(
                    titleResId = R.string.label,
                    value = calendar.label,
                )
                ContactInformation(
                    titleResId = R.string.type,
                    value = calendar.type,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}