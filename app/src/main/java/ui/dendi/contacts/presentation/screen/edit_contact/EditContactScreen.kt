package ui.dendi.contacts.presentation.screen.edit_contact

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import ui.dendi.contacts.R
import ui.dendi.contacts.core.extension_ui.circleLayout
import ui.dendi.contacts.core.extension_ui.setImageByPath
import ui.dendi.contacts.core.model.UiEvent
import ui.dendi.contacts.domain.model.Gender
import ui.dendi.contacts.presentation.component.create_edit.*
import ui.dendi.contacts.ui.theme.Tundora

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditContactScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onNavigateToDetails: (String) -> Unit,
    onCancelClick: () -> Unit,
    viewModel: EditContactViewModel = hiltViewModel(),
) {

    val person = viewModel.person
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> {
                    onNavigateToDetails(person.id)
                }
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message.asString(context))
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Tundora)
    ) {
        Column(modifier = modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color.Gray, shape = CircleShape)
                        .circleLayout()
                        .padding(6.dp),
                    onClick = {
                        onCancelClick()
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
                Text(
                    modifier = Modifier.weight(1F),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.edit_contact),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                IconButton(
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color.Gray, shape = CircleShape)
                        .circleLayout()
                        .padding(6.dp),
                    enabled = viewModel.enableDoneButton,
                    onClick = {
                        keyboardController?.hide()
                        viewModel.onDoneButtonClick()
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White,
                    )
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
                    .padding(horizontal = 16.dp),
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
                            it.setImageByPath()
                        } ?: if (person.imagePath.isEmpty()) {
                            painterResource(
                                id = R.drawable.ic_add_photo
                            )
                        } else {
                            person.imagePath.setImageByPath()
                        },
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
                            text = stringResource(R.string.edit_photo),
                            color = Color.Blue,
                        )
                    }
                    if (viewModel.showScreenMessage) {
                        Popup(
                            alignment = Alignment.Center,
                        ) {
                            Surface(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(width = 1.dp, color = Color.Red),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Error,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(35.dp),
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    viewModel.screenMessage?.asString()?.let {
                                        Text(
                                            text = it,
                                            color = Color.Red,
                                            textAlign = TextAlign.Center,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {

                    var showCollapsedMainFields by remember {
                        mutableStateOf(false)
                    }
                    SectionTextTitle(textId = R.string.main)
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

                    val genderOptions = Gender.values().map {
                        it.name
                    }
                    if (showCollapsedMainFields) {
                        CollapsedMainTextFields(
                            fullName = { person.fullName },
                            updateFullName = viewModel::updateFullName,
                            fullNameResId = R.string.full_name,
                            genderOptions = genderOptions,
                            updateGender = viewModel::updateGender,
                            genderResId = R.string.gender,
                            birthday = { person.birthday },
                            updateBirthday = viewModel::updateBirthday,
                            birthdayResId = R.string.birthday,
                            occupation = { person.occupation },
                            updateOccupation = viewModel::updateOccupation,
                            occupationResId = R.string.occupation,
                        )
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
                    SectionTextTitle(textId = R.string.phone_number_title)
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

                    val phoneNumber = viewModel.phoneNumber
                    if (showCollapsedPhoneFields) {
                        CollapsedPhoneTextFields(
                            label = { phoneNumber.label },
                            updatePhoneNumberLabel = viewModel::updatePhoneNumberLabel,
                            labelResId = R.string.label,
                            type = { phoneNumber.type },
                            updatePhoneNumberType = viewModel::updatePhoneNumberType,
                            typeResId = R.string.type,
                        )
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
                    SectionTextTitle(textId = R.string.postal_address_title)
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

                    val postalAddress = viewModel.postalAddress
                    if (showCollapsedPostalAddressFields) {
                        CollapsedPostalAddressTextFields(
                            city = { postalAddress.city },
                            updateCity = viewModel::updateCity,
                            cityResId = R.string.city,
                            region = { postalAddress.region },
                            updateRegion = viewModel::updateRegion,
                            regionResId = R.string.region,
                            neighborhood = { postalAddress.neighborhood },
                            updateNeighborhood = viewModel::updateNeighborhood,
                            neighborhoodResId = R.string.neighborhood,
                            postCode = { postalAddress.postCode },
                            updatePostCode = viewModel::updatePostCode,
                            postCodeResId = R.string.post_code,
                            country = { postalAddress.country },
                            updateCountry = viewModel::updateCountry,
                            countryResId = R.string.country,
                            label = { postalAddress.label },
                            updateAddressLabel = viewModel::updatePostalAddressLabel,
                            labelResId = R.string.label,
                            type = { postalAddress.type },
                            updateAddressType = viewModel::updatePostalAddressType,
                            typeResId = R.string.type,
                        )
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
                    SectionTextTitle(textId = R.string.email_address_title)
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

                    val emailAddress = viewModel.emailAddress
                    if (showCollapsedEmailAddressFields) {
                        CollapsedEmailAddressTextFields(
                            label = { emailAddress.label },
                            updateEmailAddressLabel = viewModel::updateEmailAddressLabel,
                            labelResId = R.string.label,
                            type = { emailAddress.type },
                            updateEmailAddressType = viewModel::updateEmailAddressType,
                            typeResId = R.string.type,
                        )
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
                    SectionTextTitle(textId = R.string.organization_title)
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
                        IconEndField(
                            onClick = {
                                showCollapsedOrganizationFields =
                                    showCollapsedOrganizationFields.not()
                            },
                            showCollapsedFields = { showCollapsedOrganizationFields })
                    }

                    val organization = viewModel.organization
                    if (showCollapsedOrganizationFields) {
                        CollapsedOrganizationTextFields(
                            label = { organization.label },
                            updateOrganizationLabel = viewModel::updateOrganizationLabel,
                            labelResId = R.string.label,
                            jobTitle = { organization.jobTitle },
                            updateJobTitle = viewModel::updateJobTitle,
                            jobTitleResId = R.string.job_title,
                            jobDescription = { organization.jobDescription },
                            updateJobDescription = viewModel::updateJobDescription,
                            jobDescriptionResId = R.string.job_description,
                            department = { organization.department },
                            updateDepartment = viewModel::updateDepartment,
                            departmentResId = R.string.department,
                        )
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
                    SectionTextTitle(textId = R.string.website_title)
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

                    val website = viewModel.website
                    if (showCollapsedWebsiteFields) {
                        CollapsedWebsiteTextFields(
                            label = { website.label },
                            updateWebsiteLabel = viewModel::updateWebsiteLabel,
                            labelResId = R.string.label,
                            type = { website.type },
                            updateWebsiteType = viewModel::updateWebsiteType,
                            typeResId = R.string.type,
                        )
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
                    SectionTextTitle(textId = R.string.calendar_title)
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

                    val calendar = viewModel.calendar
                    if (showCollapsedCalendarFields) {
                        CollapsedCalendarTextFields(
                            label = { calendar.label },
                            updateCalendarLabel = viewModel::updateCalendarLabel,
                            labelResId = R.string.label,
                            type = { calendar.type },
                            updateCalendarType = viewModel::updateCalendarType,
                            typeResId = R.string.type,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}