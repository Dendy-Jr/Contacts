package ui.dendi.contacts.presentation.screen.contact_details

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonemetadata
import com.google.i18n.phonenumbers.Phonenumber
import ui.dendi.contacts.R
import ui.dendi.contacts.core.extension.shareText
import ui.dendi.contacts.core.extension_ui.setImageByPath
import ui.dendi.contacts.core.model.UiEvent
import ui.dendi.contacts.domain.model.Person
import ui.dendi.contacts.presentation.screen.contact_details.component.CardSection
import ui.dendi.contacts.presentation.screen.contact_details.component.ContactDetailsBottomBackground
import ui.dendi.contacts.presentation.screen.contact_details.component.ContactDetailsHeaderBackground
import ui.dendi.contacts.presentation.screen.contact_details.component.DetailsTitle
import ui.dendi.contacts.presentation.screen.contact_details.component.InteractionItem
import ui.dendi.contacts.presentation.screen.contact_details.component.SectionContactInformation
import ui.dendi.contacts.ui.theme.Mandy
import ui.dendi.contacts.ui.theme.MulledWine
import ui.dendi.contacts.ui.theme.Neptune
import ui.dendi.contacts.ui.theme.TreePoppy
import java.util.Locale

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

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Mandy)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            ContactDetailsHeaderBackground(
                color = MulledWine, modifier = Modifier.fillMaxSize()
            )
            ContactDetailsBottomBackground(
                color = Neptune, modifier = Modifier.fillMaxSize()
            )
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(
                    modifier = Modifier
                        .size(30.dp)
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
                            painter = contact.imagePath.setImageByPath(R.drawable.ic_add_photo_white),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )

                        IconButton(modifier = Modifier
                            .padding(start = 8.dp)
                            .size(35.dp)
                            .constrainAs(shareButton) {
                                top.linkTo(parent.top)
                                start.linkTo(avatarImage.end)
                                bottom.linkTo(parent.bottom)
                            },
                            onClick = { context.shareText("${contact.firstName} ${contact.lastName}") }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_share),
                                contentDescription = null,
                                tint = TreePoppy,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "${contact.firstName} ${contact.lastName}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                    if (contact.phoneNumber.number.isNotEmpty()) {
                        Text(
                            text = contact.phoneNumber.number,
                            fontSize = 18.sp,
                            color = Color.White,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                val phone = contact.phoneNumber
                val email = contact.emailAddress

                val showPermissionDeniedDialog = remember { mutableStateOf(false) }
                val tryToCall = {
                    val intent = Intent(Intent.ACTION_CALL).apply {
                        data = Uri.parse("tel:${phone.number}")
                    }
                    if (ContextCompat.checkSelfPermission(
                            context, Manifest.permission.CALL_PHONE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        context.startActivity(intent)
                    } else {
                        showPermissionDeniedDialog.value = true
                    }
                }
                val callLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult()
                ) { activityResult ->
                    if (ContextCompat.checkSelfPermission(
                            context, Manifest.permission.CALL_PHONE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        tryToCall.invoke()
                    } else {
                        showPermissionDeniedDialog.value = true
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    InteractionItem(
                        iconResId = R.drawable.ic_message,
                        textResId = R.string.message,
                        enabled = phone.number.isNotEmpty()
                    ) {
                        sendSms(number = phone.number, context = context)

                    }
                    InteractionItem(
                        iconResId = R.drawable.ic_phone_call,
                        textResId = R.string.call,
                        enabled = phone.number.isNotEmpty()
                    ) {
                        tryToCall.invoke()
                    }
                    InteractionItem(
                        iconResId = R.drawable.ic_mail,
                        textResId = R.string.mail,
                        enabled = email.link.isNotEmpty()
                    ) {
                        sendMail(email = email.link, context = context)
                    }
                    if (showPermissionDeniedDialog.value) {
                        //TODO move to another file
                        AlertDialog(onDismissRequest = { showPermissionDeniedDialog.value = false },
                            title = { Text(text = stringResource(R.string.call_permission_required)) },
                            text = { Text(text = stringResource(R.string.to_make_call)) },
                            confirmButton = {
                                Button(onClick = {
                                    showPermissionDeniedDialog.value = false

                                    val intent = Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.parse("package:${context.packageName}")
                                    )
                                    callLauncher.launch(intent)
                                }) {
                                    Text(text = stringResource(R.string.grant_permission))
                                }
                            },
                            dismissButton = {
                                Button(onClick = {
                                    showPermissionDeniedDialog.value = false
                                }) {
                                    Text(text = stringResource(R.string.cancel))
                                }
                            })
                    }
                }

                val mainSection = mapOf(
                    R.string.full_name to contact.fullName,
                    R.string.gender to contact.gender.name,
                    R.string.birthday to contact.birthday,
                    R.string.occupation to contact.occupation,
                ).filter { it.value.isNotEmpty() }
                val showMainSection = mainSection.any { it.value.isNotEmpty() }

                if (showMainSection) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailsTitle(iconId = R.drawable.ic_person, textId = R.string.main)
                    CardSection {
                        SectionContactInformation(mainSection)
                    }
                }

                val phoneSection = mapOf(
                    R.string.phone_number to (getFormattedNumber(phone.number) ?: ""),
                    R.string.label to phone.label,
                    R.string.type to phone.type,
                ).filter { it.value.isNotEmpty() }
                val showPhoneSection = phoneSection.any { it.value.isNotEmpty() }

                if (showPhoneSection) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailsTitle(iconId = R.drawable.ic_phone, textId = R.string.phone_number_title)
                    CardSection {
                        SectionContactInformation(phoneSection)
                    }
                }

                val address = contact.postalAddress
                val addressSection = mapOf(
                    R.string.street to address.street,
                    R.string.city to address.city,
                    R.string.region to address.region,
                    R.string.neighborhood to address.neighborhood,
                    R.string.post_code to address.postCode,
                    R.string.country to address.country,
                    R.string.label to address.label,
                    R.string.type to address.type,
                ).filter { it.value.isNotEmpty() }
                val showAddressSection = addressSection.any { it.value.isNotEmpty() }

                if (showAddressSection) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailsTitle(
                        iconId = R.drawable.ic_address, textId = R.string.postal_address_title
                    )
                    CardSection {
                        SectionContactInformation(addressSection)
                    }
                }

                val emailSection = mapOf(
                    R.string.link to email.link,
                    R.string.label to email.label,
                    R.string.type to email.type,
                ).filter { it.value.isNotEmpty() }
                val showEmailSection = emailSection.any { it.value.isNotEmpty() }

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
                    R.string.organization_name to organization.name,
                    R.string.label to organization.label,
                    R.string.job_title to organization.jobTitle,
                    R.string.job_description to organization.jobDescription,
                    R.string.department to organization.department,
                ).filter { it.value.isNotEmpty() }
                val showOrganizationSection = organizationSection.any { it.value.isNotEmpty() }

                if (showOrganizationSection) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailsTitle(iconId = R.drawable.ic_job, textId = R.string.organization_title)
                    CardSection {
                        SectionContactInformation(organizationSection)
                    }
                }

                val website = contact.website
                val websiteSection = mapOf(
                    R.string.link to website.link,
                    R.string.label to website.label,
                    R.string.type to website.type,
                ).filter { it.value.isNotEmpty() }
                val showWebsiteSection = websiteSection.any { it.value.isNotEmpty() }

                if (showWebsiteSection) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailsTitle(iconId = R.drawable.ic_web, textId = R.string.website_title)
                    CardSection {
                        SectionContactInformation(websiteSection)
                    }
                }

                val calendar = contact.calendar
                val calendarSection = mapOf(
                    R.string.link to calendar.link,
                    R.string.label to calendar.label,
                    R.string.type to calendar.type,
                ).filter { it.value.isNotEmpty() }
                val showCalendarSection = calendarSection.any { it.value.isNotEmpty() }

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
                        }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
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
}

private fun sendSms(
    number: String,
    context: Context,
) {
    val smsIntent = Intent(Intent.ACTION_SENDTO)
    smsIntent.data = Uri.parse("smsto:" + Uri.encode(number))
    context.startActivity(Intent.createChooser(smsIntent, "Sms"))
}

private fun sendMail(
    email: String,
    context: Context,
) {
    val mailIntent = Intent(Intent.ACTION_SENDTO)
    mailIntent.data = Uri.parse("mailto:$email")
    context.startActivity(Intent.createChooser(mailIntent, "Email"))
}

private fun getFormattedNumber(numberString: String): String? {
    val phoneNumberUtil = PhoneNumberUtil.getInstance()
    val numberFormat = Phonemetadata.NumberFormat().apply {
        pattern = "(\\d{3})(\\d{3})(\\d+)"
        format = "($1) $2-$3"
    }
    try {
        val phoneNumberPN: Phonenumber.PhoneNumber =
            phoneNumberUtil.parse(numberString, Locale.US.country)

        return phoneNumberUtil.formatByPattern(
            phoneNumberPN, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL, listOf(numberFormat)
        )
    } catch (e: NumberParseException) {
        e.printStackTrace()
    }
    return null
}