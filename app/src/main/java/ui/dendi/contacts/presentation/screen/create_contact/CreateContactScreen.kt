package ui.dendi.contacts.presentation.screen.create_contact

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import ui.dendi.contacts.R
import ui.dendi.contacts.core.UiEvent
import ui.dendi.contacts.presentation.screen.create_contact.components.*

@Composable
fun CreateContactScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onDoneClick: () -> Unit,
    onCancelClick: () -> Unit,
    viewModel: CreateContactViewModel = hiltViewModel(),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            val titleTextSize: TextUnit = 24.sp
            val iconSize: Dp = with(LocalDensity.current) {
                titleTextSize.toDp()
            }

            IconButton(
                modifier = Modifier.size(iconSize),
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
                fontSize = titleTextSize,
                fontWeight = FontWeight.SemiBold,
            )
            IconButton(
                modifier = Modifier.size(iconSize),
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
                    Log.d("AAAA", it.toString())
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
                    color = Color.Blue,
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
                MandatoryTextFieldItem(
                    modifier = Modifier.weight(1F),
                    value = viewModel.firstName,
                    onTextChanged = viewModel::updateFirstName,
                    isError = viewModel.firstName.isBlank(),
                    placeholderResId = R.string.first_name,
                )
                IconEndField(onClick = {
                    showCollapsedMainFields = showCollapsedMainFields.not()
                }, showCollapsedFields = { showCollapsedMainFields })
            }
            MandatoryTextFieldItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                value = viewModel.lastName,
                onTextChanged = viewModel::updateLastName,
                isError = viewModel.lastName.isBlank(),
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
                MandatoryTextFieldItem(
                    modifier = Modifier.weight(1F),
                    value = viewModel.phoneNumber,
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
                MandatoryTextFieldItem(
                    modifier = Modifier.weight(1F),
                    value = viewModel.street,
                    onTextChanged = viewModel::updateStreet,
                    placeholderResId = R.string.street,
                )
                IconEndField(onClick = {
                    showCollapsedPostalAddressFields = showCollapsedPostalAddressFields.not()
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
                MandatoryTextFieldItem(
                    modifier = Modifier.weight(1F),
                    value = viewModel.emailAddress,
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
                MandatoryTextFieldItem(
                    modifier = Modifier.weight(1F),
                    value = viewModel.organizationName,
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
                MandatoryTextFieldItem(
                    modifier = Modifier.weight(1F),
                    value = viewModel.websiteLink,
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
                MandatoryTextFieldItem(
                    modifier = Modifier.weight(1F),
                    value = viewModel.calendarLink,
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

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            enabled = viewModel.showDoneButton(),
            onClick = {
                viewModel.onSaveButtonClick()
            },
        ) {
            Text(
                text = stringResource(R.string.done),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun CollapsedCalendarTextFields(viewModel: CreateContactViewModel) {
    CollapsedTextFieldItem(
        value = viewModel.calendarLabel,
        onTextChanged = viewModel::updateCalendarLabel,
        placeholderResId = R.string.label,
    )
    CollapsedTextFieldItem(
        value = viewModel.calendarType,
        onTextChanged = viewModel::updateCalendarType,
        placeholderResId = R.string.type,
    )
}

@Composable
fun CollapsedWebsiteTextFields(viewModel: CreateContactViewModel) {
    CollapsedTextFieldItem(
        value = viewModel.websiteLabel,
        onTextChanged = viewModel::updateWebsiteLabel,
        placeholderResId = R.string.label,
    )
    CollapsedTextFieldItem(
        value = viewModel.websiteType,
        onTextChanged = viewModel::updateWebsiteType,
        placeholderResId = R.string.type,
    )
}

@Composable
fun CollapsedOrganizationTextFields(viewModel: CreateContactViewModel) {
    CollapsedTextFieldItem(
        value = viewModel.organizationLabel,
        onTextChanged = viewModel::updateOrganizationLabel,
        placeholderResId = R.string.label,
    )
    CollapsedTextFieldItem(
        value = viewModel.jobTitle,
        onTextChanged = viewModel::updateJobTitle,
        placeholderResId = R.string.job_title,
    )
    CollapsedTextFieldItem(
        value = viewModel.jobDescription,
        onTextChanged = viewModel::updateJobDescription,
        placeholderResId = R.string.job_description,
    )
    CollapsedTextFieldItem(
        value = viewModel.department,
        onTextChanged = viewModel::updateDepartment,
        placeholderResId = R.string.department,
    )
}

@Composable
fun CollapsedPhoneTextFields(viewModel: CreateContactViewModel) {
    CollapsedTextFieldItem(
        value = viewModel.phoneNumberLabel,
        onTextChanged = viewModel::updatePhoneNumberLabel,
        placeholderResId = R.string.label,
    )
    CollapsedTextFieldItem(
        value = viewModel.phoneNumberType,
        onTextChanged = viewModel::updatePhoneNumberType,
        placeholderResId = R.string.type,
    )
}

@Composable
fun CollapsedEmailAddressTextFields(viewModel: CreateContactViewModel) {
    CollapsedTextFieldItem(
        value = viewModel.emailAddressLabel,
        onTextChanged = viewModel::updateEmailAddressLabel,
        placeholderResId = R.string.label,
    )
    CollapsedTextFieldItem(
        value = viewModel.emailAddressType,
        onTextChanged = viewModel::updateEmailAddressType,
        placeholderResId = R.string.type,
    )
}

@Composable
fun CollapsedPostalAddressTextFields(viewModel: CreateContactViewModel) {
    CollapsedTextFieldItem(
        value = viewModel.city,
        onTextChanged = viewModel::updateCity,
        placeholderResId = R.string.region,
    )
    CollapsedTextFieldItem(
        value = viewModel.region,
        onTextChanged = viewModel::updateRegion,
        placeholderResId = R.string.region,
    )
    CollapsedTextFieldItem(
        value = viewModel.neighborhood,
        onTextChanged = viewModel::updateNeighborhood,
        placeholderResId = R.string.neighborhood,
    )
    CollapsedTextFieldItem(
        value = viewModel.postCode,
        onTextChanged = viewModel::updatePostCode,
        placeholderResId = R.string.post_code,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    CollapsedTextFieldItem(
        value = viewModel.country,
        onTextChanged = viewModel::updateCountry,
        placeholderResId = R.string.country,
    )
    CollapsedTextFieldItem(
        value = viewModel.postalAddressLabel,
        onTextChanged = viewModel::updatePostalAddressLabel,
        placeholderResId = R.string.label,
    )
    CollapsedTextFieldItem(
        value = viewModel.postalAddressType,
        onTextChanged = viewModel::updatePostalAddressType,
        placeholderResId = R.string.type,
    )
}

@Composable
private fun CollapsedMainTextFields(viewModel: CreateContactViewModel) {
    CollapsedTextFieldItem(
        value = viewModel.title,
        onTextChanged = viewModel::updateTitle,
        placeholderResId = R.string.title,
    )

    CollapsedTextFieldItem(
        value = viewModel.fullName,
        onTextChanged = viewModel::updateFullName,
        placeholderResId = R.string.full_name,
    )

    CollapsedTextFieldItem(
        value = viewModel.gender,
        onTextChanged = viewModel::updateGender,
        placeholderResId = R.string.gender,
    )

    CollapsedTextFieldItem(
        value = viewModel.birthday,
        onTextChanged = viewModel::updateBirthday,
        placeholderResId = R.string.birthday,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )

    CollapsedTextFieldItem(
        value = viewModel.occupation,
        onTextChanged = viewModel::updateOccupation,
        placeholderResId = R.string.occupation,
    )
}