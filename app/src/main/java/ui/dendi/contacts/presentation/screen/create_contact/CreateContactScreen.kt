package ui.dendi.contacts.presentation.screen.create_contact

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ui.dendi.contacts.R
import ui.dendi.contacts.core.UiEvent
import ui.dendi.contacts.presentation.screen.GradientButton

@Composable
fun CreateContactScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateContactViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = stringResource(R.string.create_contact),
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextFieldItem(
            value = viewModel.title,
            onTextChanged = viewModel::updateTitle,
            label = { Text(text = stringResource(R.string.title_label)) },
            placeholder = { Text(text = stringResource(R.string.title)) },
            isError = false,
        )

        TextFieldItem(
            value = viewModel.fullName,
            onTextChanged = viewModel::updateFullName,
            label = { Text(text = stringResource(R.string.full_name_label)) },
            placeholder = { Text(text = stringResource(R.string.full_name)) },
        )

        TextFieldItem(
            value = viewModel.familyName,
            onTextChanged = viewModel::updateFamilyName,
            label = { Text(text = stringResource(R.string.family_name_label)) },
            placeholder = { Text(text = stringResource(R.string.family_name)) },
        )

        TextFieldItem(
            value = viewModel.givenName,
            onTextChanged = viewModel::updateGivenName,
            label = { Text(text = stringResource(R.string.given_name_label)) },
            placeholder = { Text(text = stringResource(R.string.given_name)) },
        )

        TextFieldItem(
            value = viewModel.gender,
            onTextChanged = viewModel::updateGender,
            label = { Text(text = stringResource(R.string.gender_label)) },
            placeholder = { Text(text = stringResource(R.string.gender)) },
        )

        TextFieldItem(
            value = viewModel.birthday,
            onTextChanged = viewModel::updateBirthday,
            label = { Text(text = stringResource(R.string.birthday_label)) },
            placeholder = { Text(text = stringResource(R.string.birthday)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        TextFieldItem(
            value = viewModel.occupation,
            onTextChanged = viewModel::updateOccupation,
            placeholder = { Text(text = stringResource(R.string.occupation)) },
            isError = false,
        )

        TextFieldItem(
            value = viewModel.phoneNumber,
            onTextChanged = viewModel::updatePhoneNumber,
            placeholder = { Text(text = stringResource(R.string.phone_number)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        )

        TextFieldItem(
            value = viewModel.phoneNumberType,
            onTextChanged = viewModel::updatePhoneNumberType,
            label = { Text(text = stringResource(R.string.phone_number_type_label)) },
            placeholder = { Text(text = stringResource(R.string.phone_number_type)) },
        )

        TextFieldItem(
            value = viewModel.street,
            onTextChanged = viewModel::updateStreet,
            placeholder = { Text(text = stringResource(R.string.street)) },
        )

        TextFieldItem(
            value = viewModel.city,
            onTextChanged = viewModel::updateCity,
            placeholder = { Text(text = stringResource(R.string.city)) },
        )

        TextFieldItem(
            value = viewModel.region,
            onTextChanged = viewModel::updateRegion,
            label = { Text(text = stringResource(R.string.region_label)) },
            placeholder = { Text(text = stringResource(R.string.region)) },
        )

        TextFieldItem(
            value = viewModel.neighborhood,
            onTextChanged = viewModel::updateNeighborhood,
            placeholder = { Text(text = stringResource(R.string.neighborhood)) },
            isError = false,
        )

        TextFieldItem(
            value = viewModel.postCode,
            onTextChanged = viewModel::updatePostCode,
            placeholder = { Text(text = stringResource(R.string.post_code)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        TextFieldItem(
            value = viewModel.poBox,
            onTextChanged = viewModel::updatePoBox,
            placeholder = { Text(text = stringResource(R.string.po_box)) },
            isError = false,
        )

        TextFieldItem(
            value = viewModel.country,
            onTextChanged = viewModel::updateCountry,
            placeholder = { Text(text = stringResource(R.string.country)) },
        )

        TextFieldItem(
            value = viewModel.postalAddressType,
            onTextChanged = viewModel::updatePostalAddressType,
            label = { Text(text = stringResource(R.string.postal_address_type_label)) },
            placeholder = { Text(text = stringResource(R.string.postal_address_type)) },
            spacer = 16.dp,
        )

        val context = LocalContext.current
        LaunchedEffect(key1 = true) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.Success -> { // TODO
                    }
                    is UiEvent.ShowSnackbar -> {
                        snackbarHostState.showSnackbar(event.message.asString(context))
                    }
                }
            }
        }

        val gradient =
            Brush.horizontalGradient(listOf(Color(0xFF4109A1), Color(0xFF4195E2)))

        GradientButton(
            text = stringResource(R.string.save_contact),
            textColor = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            gradient = gradient
        ) {
            viewModel.onSaveButtonClick()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TextFieldItem(
    modifier: Modifier = Modifier,
    value: String,
    onTextChanged: (String) -> Unit,
    placeholder: @Composable () -> Unit,
    label: @Composable () -> Unit,
    spacer: Dp = 8.dp,
    isError: Boolean = value.isBlank(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onTextChanged,
        placeholder = { placeholder.invoke() },
        label = { label.invoke() },
        trailingIcon = {
            if (isError) {
                Icon(Icons.Rounded.Error, "error", tint = MaterialTheme.colorScheme.error)
            }
        },
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(percent = 15),
    )
    Spacer(modifier = Modifier.height(spacer))
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TextFieldItem(
    modifier: Modifier = Modifier,
    value: String,
    onTextChanged: (String) -> Unit,
    placeholder: @Composable () -> Unit,
    spacer: Dp = 8.dp,
    isError: Boolean = value.isBlank(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onTextChanged,
        placeholder = { placeholder.invoke() },
        trailingIcon = {
            if (isError) {
                Icon(Icons.Rounded.Error, "error", tint = MaterialTheme.colorScheme.error)
            }
        },
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(percent = 15),
    )
    Spacer(modifier = Modifier.height(spacer))
}