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
import androidx.compose.ui.text.font.FontWeight
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
                            Color(0xFF2B2E4A),
                            Color(0xFFE84545),
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
                    tint = Color.White,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(0xFF2B2E4A),
                            Color(0xFFE84545),
                        )
                    )
                )
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
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${contact.lastName} ${contact.firstName}",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                )
                if (contact.phoneNumber.number.isNotEmpty()) {
                    Text(
                        text = contact.phoneNumber.number,
                        fontSize = 18.sp,
                        color = Color.White,
                    )
                }
            }

            val showMainSection = listOf(
                contact.fullName,
                contact.gender,
                contact.birthday,
                contact.occupation
            ).any { it.isNotEmpty() }

            if (showMainSection) {
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
            }

            val phone = contact.phoneNumber
            val showPhoneSection = listOf(
                phone.number,
                phone.label,
                phone.type,
            ).any { it.isNotEmpty() }

            if (showPhoneSection) {
                Spacer(modifier = Modifier.height(16.dp))
                CardSection {
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
            }

            val address = contact.postalAddress
            val showAddressSection = listOf(
                address.street,
                address.city,
                address.region,
                address.neighborhood,
                address.postCode,
                address.country,
                address.label,
                address.type,
            ).any { it.isNotEmpty() }

            if (showAddressSection) {
                Spacer(modifier = Modifier.height(16.dp))
                CardSection {
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
            }

            val email = contact.emailAddress
            val showEmailSection = listOf(
                email.link,
                email.label,
                email.type,
            ).any { it.isNotEmpty() }

            if (showEmailSection) {
                Spacer(modifier = Modifier.height(16.dp))
                CardSection {
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
            }

            val organization = contact.organization
            val showOrganizationSection = listOf(
                organization.name,
                organization.label,
                organization.jobTitle,
                organization.jobDescription,
                organization.department,
            ).any { it.isNotEmpty() }

            if (showOrganizationSection) {
                Spacer(modifier = Modifier.height(16.dp))
                CardSection {
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
            }

            val website = contact.website
            val showWebsiteSection = listOf(
                website.link,
                website.label,
                website.type,
            ).any { it.isNotEmpty() }

            if (showWebsiteSection) {
                Spacer(modifier = Modifier.height(16.dp))
                CardSection {
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
            }

            val calendar = contact.calendar
            val showCalendarSection = listOf(
                calendar.link,
                calendar.label,
                calendar.type,
            ).any { it.isNotEmpty() }

            if (showCalendarSection) {
                Spacer(modifier = Modifier.height(16.dp))
                CardSection {
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
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}