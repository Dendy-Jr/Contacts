package ui.dendi.contacts.presentation.screen.create_contact

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import ui.dendi.contacts.R
import ui.dendi.contacts.core.model.UiEvent
import ui.dendi.contacts.presentation.component.textSizeToDp
import ui.dendi.contacts.presentation.screen.create_contact.components.IconEndField
import ui.dendi.contacts.presentation.screen.create_contact.components.IconStartField
import ui.dendi.contacts.presentation.screen.create_contact.components.TextFieldItem
import ui.dendi.contacts.presentation.screen.create_contact.components.TextSectionTitle

@Composable
fun CreateContactScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onDoneClick: () -> Unit,
    onCancelClick: () -> Unit,
    viewModel: CreateContactViewModel = hiltViewModel(),
) {
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
            verticalAlignment = Alignment.CenterVertically,

            ) {
            IconButton(
                modifier = Modifier.size(25.sp.textSizeToDp()),
                onClick = {
                    onCancelClick()
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = null,
                )
            }
            Text(
                modifier = Modifier.weight(1F),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.new_contact),
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
            )
            IconButton(
                modifier = Modifier.size(25.sp.textSizeToDp()),
                enabled = viewModel.showDoneButton(),
                onClick = {
                    viewModel.onSaveButtonClick()
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_done),
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
            Spacer(modifier = Modifier.height(56.dp))

            var selectedImageUri by remember {
                mutableStateOf<Uri?>(null)
            }
            val photoPickerLauncher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = { uri -> selectedImageUri = uri })

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .clickable {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    painter = selectedImageUri?.let {
                        viewModel.updateImagePath(it.toString())
                        rememberAsyncImagePainter(it)
                    } ?: painterResource(
                        id = R.drawable.ic_add_photo
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                TextButton(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                ) {
                    Text(
                        text = stringResource(R.string.add_photo),
                        fontSize = 16.sp,
                        color = Color(0xFF30E3CA),
                    )
                }
            }

            Spacer(modifier = Modifier.height(35.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                var showCollapsedMainFields by remember {
                    mutableStateOf(false)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    IconStartField(iconResId = R.drawable.ic_person)
                    TextFieldItem(
                        modifier = Modifier.weight(1F),
                        value = viewModel.person.firstName,
                        onTextChanged = viewModel::updateFirstName,
                        isError = viewModel.person.firstName.isBlank(),
                        placeholderResId = R.string.first_name,
                    )

                    IconEndField(onClick = {
                        showCollapsedMainFields = showCollapsedMainFields.not()
                    }, showCollapsedFields = { showCollapsedMainFields })
                }
                TextFieldItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    value = viewModel.person.lastName,
                    onTextChanged = viewModel::updateLastName,
                    isError = viewModel.person.lastName.isBlank(),
                    placeholderResId = R.string.last_name,
                )

                if (showCollapsedMainFields) {
                    CollapsedMainTextFields(viewModel)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                var showCollapsedPhoneFields by remember {
                    mutableStateOf(false)
                }
                TextSectionTitle(textId = R.string.phone_number_title)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    IconStartField(iconResId = R.drawable.ic_phone)
                    TextFieldItem(
                        modifier = Modifier.weight(1F),
                        value = viewModel.phoneNumber.number,
                        onTextChanged = viewModel::updatePhoneNumber,
                        placeholderResId = R.string.phone_number,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    )
                    IconEndField(onClick = {
                        showCollapsedPhoneFields = showCollapsedPhoneFields.not()
                    }, showCollapsedFields = { showCollapsedPhoneFields })
                }
                if (showCollapsedPhoneFields) {
                    CollapsedPhoneTextFields(viewModel)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                var showCollapsedPostalAddressFields by remember {
                    mutableStateOf(false)
                }
                TextSectionTitle(textId = R.string.postal_address_title)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    IconStartField(iconResId = R.drawable.ic_address)
                    TextFieldItem(
                        modifier = Modifier.weight(1F),
                        value = viewModel.postalAddress.street,
                        onTextChanged = viewModel::updateStreet,
                        placeholderResId = R.string.street,
                    )
                    IconEndField(onClick = {
                        showCollapsedPostalAddressFields =
                            showCollapsedPostalAddressFields.not()
                    }, showCollapsedFields = { showCollapsedPostalAddressFields })
                }
                if (showCollapsedPostalAddressFields) {
                    CollapsedPostalAddressTextFields(viewModel)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                var showCollapsedEmailAddressFields by remember {
                    mutableStateOf(false)
                }
                TextSectionTitle(textId = R.string.email_address_title)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    IconStartField(iconResId = R.drawable.ic_email_address)
                    TextFieldItem(
                        modifier = Modifier.weight(1F),
                        value = viewModel.emailAddress.link,
                        onTextChanged = viewModel::updateEmailAddress,
                        placeholderResId = R.string.link,
                    )
                    IconEndField(onClick = {
                        showCollapsedEmailAddressFields = showCollapsedEmailAddressFields.not()
                    }, showCollapsedFields = { showCollapsedEmailAddressFields })
                }
                if (showCollapsedEmailAddressFields) {
                    CollapsedEmailAddressTextFields(viewModel)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                var showCollapsedOrganizationFields by remember {
                    mutableStateOf(false)
                }
                TextSectionTitle(textId = R.string.organization_title)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    IconStartField(iconResId = R.drawable.ic_job)
                    TextFieldItem(
                        modifier = Modifier.weight(1F),
                        value = viewModel.organization.name,
                        onTextChanged = viewModel::updateOrganizationName,
                        placeholderResId = R.string.organization_name,
                    )
                    IconEndField(onClick = {
                        showCollapsedOrganizationFields = showCollapsedOrganizationFields.not()
                    }, showCollapsedFields = { showCollapsedOrganizationFields })
                }
                if (showCollapsedOrganizationFields) {
                    CollapsedOrganizationTextFields(viewModel)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                var showCollapsedWebsiteFields by remember {
                    mutableStateOf(false)
                }
                TextSectionTitle(textId = R.string.website_title)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    IconStartField(iconResId = R.drawable.ic_web)
                    TextFieldItem(
                        modifier = Modifier.weight(1F),
                        value = viewModel.website.link,
                        onTextChanged = viewModel::updateWebsiteLink,
                        placeholderResId = R.string.link,
                    )
                    IconEndField(onClick = {
                        showCollapsedWebsiteFields = showCollapsedWebsiteFields.not()
                    }, showCollapsedFields = { showCollapsedWebsiteFields })
                }
                if (showCollapsedWebsiteFields) {
                    CollapsedWebsiteTextFields(viewModel)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                var showCollapsedCalendarFields by remember {
                    mutableStateOf(false)
                }
                TextSectionTitle(textId = R.string.calendar_title)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    IconStartField(iconResId = R.drawable.ic_calendar)
                    TextFieldItem(
                        modifier = Modifier.weight(1F),
                        value = viewModel.calendar.link,
                        onTextChanged = viewModel::updateCalendarLink,
                        placeholderResId = R.string.link,
                    )
                    IconEndField(onClick = {
                        showCollapsedCalendarFields = showCollapsedCalendarFields.not()
                    }, showCollapsedFields = { showCollapsedCalendarFields })
                }
                if (showCollapsedCalendarFields) {
                    CollapsedCalendarTextFields(viewModel)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            val context = LocalContext.current
            LaunchedEffect(key1 = true) {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        is UiEvent.Success -> onDoneClick()
                        is UiEvent.ShowSnackbar -> {
                            snackbarHostState.showSnackbar(event.message.asString(context))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CollapsedCalendarTextFields(viewModel: CreateContactViewModel) {
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.calendar.label,
        onTextChanged = viewModel::updateCalendarLabel,
        placeholderResId = R.string.label,
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.calendar.type,
        onTextChanged = viewModel::updateCalendarType,
        placeholderResId = R.string.type,
    )
}

@Composable
fun CollapsedWebsiteTextFields(viewModel: CreateContactViewModel) {
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.website.label,
        onTextChanged = viewModel::updateWebsiteLabel,
        placeholderResId = R.string.label,
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.website.type,
        onTextChanged = viewModel::updateWebsiteType,
        placeholderResId = R.string.type,
    )
}

@Composable
fun CollapsedOrganizationTextFields(viewModel: CreateContactViewModel) {
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.organization.label,
        onTextChanged = viewModel::updateOrganizationLabel,
        placeholderResId = R.string.label,
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.organization.jobTitle,
        onTextChanged = viewModel::updateJobTitle,
        placeholderResId = R.string.job_title,
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.organization.jobDescription,
        onTextChanged = viewModel::updateJobDescription,
        placeholderResId = R.string.job_description,
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.organization.department,
        onTextChanged = viewModel::updateDepartment,
        placeholderResId = R.string.department,
    )
}

@Composable
fun CollapsedPhoneTextFields(viewModel: CreateContactViewModel) {
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.phoneNumber.label,
        onTextChanged = viewModel::updatePhoneNumberLabel,
        placeholderResId = R.string.label,
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.phoneNumber.type,
        onTextChanged = viewModel::updatePhoneNumberType,
        placeholderResId = R.string.type,
    )
}

@Composable
fun CollapsedEmailAddressTextFields(viewModel: CreateContactViewModel) {
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.emailAddress.label,
        onTextChanged = viewModel::updateEmailAddressLabel,
        placeholderResId = R.string.label,
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.emailAddress.type,
        onTextChanged = viewModel::updateEmailAddressType,
        placeholderResId = R.string.type,
    )
}

@Composable
fun CollapsedPostalAddressTextFields(viewModel: CreateContactViewModel) {
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.postalAddress.city,
        onTextChanged = viewModel::updateCity,
        placeholderResId = R.string.city,
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.postalAddress.region,
        onTextChanged = viewModel::updateRegion,
        placeholderResId = R.string.region,
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.postalAddress.neighborhood,
        onTextChanged = viewModel::updateNeighborhood,
        placeholderResId = R.string.neighborhood,
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.postalAddress.postCode,
        onTextChanged = viewModel::updatePostCode,
        placeholderResId = R.string.post_code,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.postalAddress.country,
        onTextChanged = viewModel::updateCountry,
        placeholderResId = R.string.country,
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.postalAddress.label,
        onTextChanged = viewModel::updatePostalAddressLabel,
        placeholderResId = R.string.label,
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.postalAddress.type,
        onTextChanged = viewModel::updatePostalAddressType,
        placeholderResId = R.string.type,
    )
}

@Composable
private fun CollapsedMainTextFields(viewModel: CreateContactViewModel) {
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.person.fullName,
        onTextChanged = viewModel::updateFullName,
        placeholderResId = R.string.full_name,
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.person.gender,
        onTextChanged = viewModel::updateGender,
        placeholderResId = R.string.gender,
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.person.birthday,
        onTextChanged = viewModel::updateBirthday,
        placeholderResId = R.string.birthday,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    TextFieldItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = viewModel.person.occupation,
        onTextChanged = viewModel::updateOccupation,
        placeholderResId = R.string.occupation,
    )
}