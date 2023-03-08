package ui.dendi.contacts.presentation.screen.contact_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import ui.dendi.contacts.R
import ui.dendi.contacts.core.extension.shareText
import ui.dendi.contacts.core.extension_ui.circleLayout
import ui.dendi.contacts.core.extension_ui.setImageByPath
import ui.dendi.contacts.core.model.UiEvent
import ui.dendi.contacts.domain.model.Person
import ui.dendi.contacts.presentation.screen.contact_details.component.CardSection
import ui.dendi.contacts.presentation.screen.contact_details.component.DetailsTitle
import ui.dendi.contacts.presentation.screen.contact_details.component.SectionContactInformation
import ui.dendi.contacts.ui.theme.Tundora

@Composable
fun ContactDetailsScreen(
    id: String,
    onNavigateToContacts: () -> Unit,
    onEditClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ContactDetailsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val contact = produceState<Person?>(initialValue = null) {
        value = viewModel.getContactDetails(id)
    }.value ?: return

    LaunchedEffect(key1 = true) {
        viewModel.event.collect { event ->
            when (event) {
                is UiEvent.Success -> onNavigateToContacts()
                is UiEvent.ShowSnackbar -> {}
            }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Tundora)
                .padding(all = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.Gray, shape = CircleShape)
                    .circleLayout()
                    .padding(6.dp),
                onClick = {
                    onNavigateToContacts()
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    tint = Color.White,
                )
            }

            IconButton(
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.Gray, shape = CircleShape)
                    .circleLayout()
                    .padding(6.dp),
                onClick = {
                    onEditClicked(id)
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Tundora)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                    val (avatarImage, shareButton) = createRefs()
                    Image(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .constrainAs(avatarImage) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            },
                        painter = contact.imagePath.setImageByPath(R.drawable.ic_add_photo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )

                    IconButton(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(35.dp)
                            .constrainAs(shareButton) {
                                top.linkTo(parent.top)
                                start.linkTo(avatarImage.end)
                                bottom.linkTo(parent.bottom)
                            },
                        onClick = { context.shareText("${contact.firstName} ${contact.lastName}") }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${contact.lastName} ${contact.firstName}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                )
                if (contact.phoneNumber.number.isNotEmpty()) {
                    Text(
                        text = contact.phoneNumber.number,
                        fontSize = 18.sp,
                        color = Color.White,
                    )
                }
            }

            val mainSection = mapOf(
                contact.fullName to R.string.full_name,
                contact.gender.name to R.string.gender,
                contact.birthday to R.string.birthday,
                contact.occupation to R.string.occupation,
            ).filter { it.key.isNotEmpty() }
            val showMainSection = mainSection.any { it.key.isNotEmpty() }

            if (showMainSection) {
                Spacer(modifier = Modifier.height(24.dp))
                DetailsTitle(iconId = R.drawable.ic_person, textId = R.string.main)
                CardSection {
                    SectionContactInformation(mainSection)
                }
            }

            val phone = contact.phoneNumber
            val phoneSection = mapOf(
                phone.number to R.string.phone_number,
                phone.label to R.string.label,
                phone.type to R.string.type,
            ).filter { it.key.isNotEmpty() }
            val showPhoneSection = phoneSection.any { it.key.isNotEmpty() }

            if (showPhoneSection) {
                Spacer(modifier = Modifier.height(16.dp))
                DetailsTitle(iconId = R.drawable.ic_phone, textId = R.string.phone_number_title)
                CardSection {
                    SectionContactInformation(phoneSection)
                }
            }

            val address = contact.postalAddress
            val addressSection = mapOf(
                address.street to R.string.street,
                address.city to R.string.city,
                address.region to R.string.region,
                address.neighborhood to R.string.neighborhood,
                address.postCode to R.string.post_code,
                address.country to R.string.country,
                address.label to R.string.label,
                address.type to R.string.type,
            ).filter { it.key.isNotEmpty() }
            val showAddressSection = addressSection.any { it.key.isNotEmpty() }

            if (showAddressSection) {
                Spacer(modifier = Modifier.height(16.dp))
                DetailsTitle(iconId = R.drawable.ic_address, textId = R.string.postal_address_title)
                CardSection {
                    SectionContactInformation(addressSection)
                }
            }

            val email = contact.emailAddress
            val emailSection = mapOf(
                email.link to R.string.link,
                email.label to R.string.label,
                email.type to R.string.type,
            ).filter { it.key.isNotEmpty() }
            val showEmailSection = emailSection.any { it.key.isNotEmpty() }

            if (showEmailSection) {
                Spacer(modifier = Modifier.height(16.dp))
                DetailsTitle(
                    iconId = R.drawable.ic_email_address, textId = R.string.email_address_title
                )
                CardSection {
                    SectionContactInformation(emailSection)
                }
            }

            val organization = contact.organization
            val organizationSection = mapOf(
                organization.name to R.string.organization_name,
                organization.label to R.string.label,
                organization.jobTitle to R.string.job_title,
                organization.jobDescription to R.string.job_description,
                organization.department to R.string.department,
            ).filter { it.key.isNotEmpty() }
            val showOrganizationSection = organizationSection.any { it.key.isNotEmpty() }

            if (showOrganizationSection) {
                Spacer(modifier = Modifier.height(16.dp))
                DetailsTitle(iconId = R.drawable.ic_job, textId = R.string.organization_title)
                CardSection {
                    SectionContactInformation(organizationSection)
                }
            }

            val website = contact.website
            val websiteSection = mapOf(
                website.link to R.string.link,
                website.label to R.string.label,
                website.type to R.string.type,
            ).filter { it.key.isNotEmpty() }
            val showWebsiteSection = websiteSection.any { it.key.isNotEmpty() }

            if (showWebsiteSection) {
                Spacer(modifier = Modifier.height(16.dp))
                DetailsTitle(iconId = R.drawable.ic_web, textId = R.string.website_title)
                CardSection {
                    SectionContactInformation(websiteSection)
                }
            }

            val calendar = contact.calendar
            val calendarSection = mapOf(
                calendar.link to R.string.link,
                calendar.label to R.string.label,
                calendar.type to R.string.type,
            ).filter { it.key.isNotEmpty() }
            val showCalendarSection = calendarSection.any { it.key.isNotEmpty() }

            if (showCalendarSection) {
                Spacer(modifier = Modifier.height(16.dp))
                DetailsTitle(iconId = R.drawable.ic_calendar, textId = R.string.calendar_title)
                CardSection {
                    SectionContactInformation(calendarSection)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp), onClick = {
                        viewModel.onDeleteButtonClick(id)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(
                        text = stringResource(R.string.delete_contact),
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                    )
                }
            }
        }
    }
}