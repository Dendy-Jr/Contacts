@file:OptIn(ExperimentalMaterial3Api::class)

package ui.dendi.contacts.presentation.screen.create_contact

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ui.dendi.contacts.R
import ui.dendi.contacts.core.UiEvent

@Composable
fun CreateContactScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateContactViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.create_contact),
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
        )
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
                    placeholder = { Text(text = stringResource(R.string.first_name)) },
                )
                IconEndField(onClick = {
                    showCollapsedMainFields = showCollapsedMainFields.not()
                }, showCollapsedFields = { showCollapsedMainFields })
            }
            TextFieldItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                value = viewModel.lastName,
                onTextChanged = viewModel::updateLastName,
                placeholder = { Text(text = stringResource(R.string.last_name)) },
            )

            if (showCollapsedMainFields) {
                CollapsedMainTextFields(viewModel)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
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
                    placeholder = { Text(text = stringResource(R.string.phone_number)) },
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

        Spacer(modifier = Modifier.height(14.dp))
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
                    placeholder = { Text(text = stringResource(R.string.street)) },
                )
                IconEndField(onClick = {
                    showCollapsedPostalAddressFields = showCollapsedPostalAddressFields.not()
                }, showCollapsedFields = { showCollapsedPostalAddressFields })
            }
            TextFieldItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                value = viewModel.city,
                onTextChanged = viewModel::updateCity,
                placeholder = { Text(text = stringResource(R.string.city)) },
            )
            if (showCollapsedPostalAddressFields) {
                CollapsedPostalAddressTextFields(viewModel)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            var showCollapsedEmailAddressFields by remember {
                mutableStateOf(false)
            }
            TextSectionTitle(textId = R.string.email_address)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                IconStartField(iconResId = R.drawable.ic_email_address)
                MandatoryTextFieldItem(
                    modifier = Modifier.weight(1F),
                    value = viewModel.emailAddress,
                    onTextChanged = viewModel::updateEmailAddress,
                    placeholder = { Text(text = stringResource(R.string.link)) },
                )
                IconEndField(onClick = {
                    showCollapsedEmailAddressFields = showCollapsedEmailAddressFields.not()
                }, showCollapsedFields = { showCollapsedEmailAddressFields })
            }
            if (showCollapsedEmailAddressFields) {
                CollapsedEmailAddressTextFields(viewModel)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
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
                    placeholder = { Text(text = stringResource(R.string.organization_name)) },
                )
                IconEndField(onClick = {
                    showCollapsedOrganizationFields = showCollapsedOrganizationFields.not()
                }, showCollapsedFields = { showCollapsedOrganizationFields })
            }
            if (showCollapsedOrganizationFields) {
                CollapsedOrganizationTextFields(viewModel)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            var showCollapsedWebsiteFields by remember {
                mutableStateOf(false)
            }
            TextSectionTitle(textId = R.string.website)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                IconStartField(iconResId = R.drawable.ic_web)
                MandatoryTextFieldItem(
                    modifier = Modifier.weight(1F),
                    value = viewModel.websiteLink,
                    onTextChanged = viewModel::updateWebsiteLink,
                    placeholder = { Text(text = stringResource(R.string.link)) },
                )
                IconEndField(onClick = {
                    showCollapsedWebsiteFields = showCollapsedWebsiteFields.not()
                }, showCollapsedFields = { showCollapsedWebsiteFields })
            }
            if (showCollapsedWebsiteFields) {
                CollapsedWebsiteTextFields(viewModel)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            var showCollapsedCalendarFields by remember {
                mutableStateOf(false)
            }
            TextSectionTitle(textId = R.string.calendar)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                IconStartField(iconResId = R.drawable.ic_calendar)
                MandatoryTextFieldItem(
                    modifier = Modifier.weight(1F),
                    value = viewModel.calendarLink,
                    onTextChanged = viewModel::updateCalendarLink,
                    placeholder = { Text(text = stringResource(R.string.link)) },
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
                    is UiEvent.Success -> {
                        // TODO In future, navigate to `Contacts Screen`
                    }
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
            enabled = viewModel.showSaveContactBtn(),
            onClick = {
                viewModel.onSaveButtonClick()
            },
        ) {
            Text(
                text = stringResource(R.string.save_contact),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun IconStartField(iconResId: Int) {
    Icon(
        modifier = Modifier.size(25.dp),
        painter = painterResource(id = iconResId),
        contentDescription = null,
    )
}

@Composable
fun IconEndField(onClick: () -> Unit, showCollapsedFields: () -> Boolean) {
    Icon(
        modifier = Modifier
            .size(25.dp)
            .clickable { onClick() },
        painter = painterResource(id = if (showCollapsedFields.invoke()) R.drawable.ic_arrow_top else R.drawable.ic_arrow_bottom),
        contentDescription = null,
    )
}

@Composable
private fun TextSectionTitle(textId: Int) {
    Text(
        modifier = Modifier.padding(start = 30.dp),
        text = stringResource(textId),
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
    )
}

@Composable
fun CollapsedCalendarTextFields(viewModel: CreateContactViewModel) {
    CollapsedTextFieldItem(
        value = viewModel.calendarLabel,
        onTextChanged = viewModel::updateCalendarLabel,
        placeholder = { Text(text = stringResource(R.string.label)) },
    )
    CollapsedTextFieldItem(
        value = viewModel.calendarType,
        onTextChanged = viewModel::updateCalendarType,
        placeholder = { Text(text = stringResource(R.string.type)) },
    )
}

@Composable
fun CollapsedWebsiteTextFields(viewModel: CreateContactViewModel) {
    CollapsedTextFieldItem(
        value = viewModel.websiteLabel,
        onTextChanged = viewModel::updateWebsiteLabel,
        placeholder = { Text(text = stringResource(R.string.label)) },
    )
    CollapsedTextFieldItem(
        value = viewModel.websiteType,
        onTextChanged = viewModel::updateWebsiteType,
        placeholder = { Text(text = stringResource(R.string.type)) },
    )
}

@Composable
fun CollapsedOrganizationTextFields(viewModel: CreateContactViewModel) {
    CollapsedTextFieldItem(
        value = viewModel.organizationLabel,
        onTextChanged = viewModel::updateOrganizationLabel,
        placeholder = { Text(text = stringResource(R.string.label)) },
    )
    CollapsedTextFieldItem(
        value = viewModel.jobTitle,
        onTextChanged = viewModel::updateJobTitle,
        placeholder = { Text(text = stringResource(R.string.job_title)) },
    )
    CollapsedTextFieldItem(
        value = viewModel.jobDescription,
        onTextChanged = viewModel::updateJobDescription,
        placeholder = { Text(text = stringResource(R.string.job_description)) },
    )
    CollapsedTextFieldItem(
        value = viewModel.department,
        onTextChanged = viewModel::updateDepartment,
        placeholder = { Text(text = stringResource(R.string.department)) },
    )
}

@Composable
fun CollapsedPhoneTextFields(viewModel: CreateContactViewModel) {
    CollapsedTextFieldItem(
        value = viewModel.phoneNumberLabel,
        onTextChanged = viewModel::updatePhoneNumberLabel,
        placeholder = { Text(text = stringResource(R.string.label)) },
    )
    CollapsedTextFieldItem(
        value = viewModel.phoneNumberType,
        onTextChanged = viewModel::updatePhoneNumberType,
        placeholder = { Text(text = stringResource(R.string.type)) },
    )
}

@Composable
fun CollapsedEmailAddressTextFields(viewModel: CreateContactViewModel) {
    CollapsedTextFieldItem(
        value = viewModel.emailAddressLabel,
        onTextChanged = viewModel::updateEmailAddressLabel,
        placeholder = { Text(text = stringResource(id = R.string.label)) },
    )
    CollapsedTextFieldItem(
        value = viewModel.emailAddressType,
        onTextChanged = viewModel::updateEmailAddressType,
        placeholder = { Text(text = stringResource(id = R.string.type)) },
    )
}

@Composable
fun CollapsedPostalAddressTextFields(viewModel: CreateContactViewModel) {
    CollapsedTextFieldItem(
        value = viewModel.region,
        onTextChanged = viewModel::updateRegion,
        placeholder = { Text(text = stringResource(R.string.region)) },
    )
    CollapsedTextFieldItem(
        value = viewModel.neighborhood,
        onTextChanged = viewModel::updateNeighborhood,
        placeholder = { Text(text = stringResource(R.string.neighborhood)) },
    )
    CollapsedTextFieldItem(
        value = viewModel.postCode,
        onTextChanged = viewModel::updatePostCode,
        placeholder = { Text(text = stringResource(R.string.post_code)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    CollapsedTextFieldItem(
        value = viewModel.country,
        onTextChanged = viewModel::updateCountry,
        placeholder = { Text(text = stringResource(R.string.country)) },
    )
    CollapsedTextFieldItem(
        value = viewModel.postalAddressLabel,
        onTextChanged = viewModel::updatePostalAddressLabel,
        placeholder = { Text(text = stringResource(R.string.label)) },
    )
    CollapsedTextFieldItem(
        value = viewModel.postalAddressType,
        onTextChanged = viewModel::updatePostalAddressType,
        placeholder = { Text(text = stringResource(R.string.type)) },
    )
}

@Composable
private fun CollapsedMainTextFields(viewModel: CreateContactViewModel) {
    CollapsedTextFieldItem(
        value = viewModel.title,
        onTextChanged = viewModel::updateTitle,
        placeholder = { Text(text = stringResource(R.string.title)) },
    )

    CollapsedTextFieldItem(
        value = viewModel.fullName,
        onTextChanged = viewModel::updateFullName,
        placeholder = { Text(text = stringResource(R.string.full_name)) },
    )

    CollapsedTextFieldItem(
        value = viewModel.gender,
        onTextChanged = viewModel::updateGender,
        placeholder = { Text(text = stringResource(R.string.gender)) },
    )

    CollapsedTextFieldItem(
        value = viewModel.birthday,
        onTextChanged = viewModel::updateBirthday,
        placeholder = { Text(text = stringResource(R.string.birthday)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )

    CollapsedTextFieldItem(
        value = viewModel.occupation,
        onTextChanged = viewModel::updateOccupation,
        placeholder = { Text(text = stringResource(R.string.occupation)) },
    )
}

@Composable
private fun MandatoryTextFieldItem(
    modifier: Modifier = Modifier,
    value: String,
    onTextChanged: (String) -> Unit,
    placeholder: @Composable () -> Unit,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onTextChanged,
        placeholder = { placeholder.invoke() },
        isError = isError,
        trailingIcon = {
            if (isError) {
                Icon(
                    Icons.Rounded.Error,
                    "Error",
                    tint = MaterialTheme.colorScheme.error,
                )
            } else if (value.isNotBlank()) {
                IconButton(onClick = {
                    onTextChanged("")
                }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear",
                    )
                }
            }
        },
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(percent = 15),
    )
}

@Composable
private fun TextFieldItem(
    modifier: Modifier = Modifier,
    value: String,
    onTextChanged: (String) -> Unit,
    placeholder: @Composable () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onTextChanged,
        placeholder = { placeholder.invoke() },
        trailingIcon = {
            if (value.isNotBlank()) {
                IconButton(onClick = {
                    onTextChanged("")
                }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear",
                    )
                }
            }
        },
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(percent = 15),
    )
}

@Composable
private fun CollapsedTextFieldItem(
    modifier: Modifier = Modifier,
    value: String,
    onTextChanged: (String) -> Unit,
    placeholder: @Composable () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = value,
        onValueChange = onTextChanged,
        placeholder = { placeholder.invoke() },
        trailingIcon = {
            if (value.isNotBlank()) {
                IconButton(onClick = {
                    onTextChanged("")
                }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear",
                    )
                }
            }
        },
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(percent = 15),
    )
}