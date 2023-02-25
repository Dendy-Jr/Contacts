package ui.dendi.contacts.presentation.screen.create_contact.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CollapsedPostalAddressTextFields(
    city: () -> String,
    updateCity: (String) -> Unit,
    @StringRes cityResId: Int,
    region: () -> String,
    updateRegion: (String) -> Unit,
    @StringRes regionResId: Int,
    neighborhood: () -> String,
    updateNeighborhood: (String) -> Unit,
    @StringRes neighborhoodResId: Int,
    postCode: () -> String,
    updatePostCode: (String) -> Unit,
    @StringRes postCodeResId: Int,
    country: () -> String,
    updateCountry: (String) -> Unit,
    @StringRes countryResId: Int,
    label: () -> String,
    updateAddressLabel: (String) -> Unit,
    @StringRes labelResId: Int,
    type: () -> String,
    updateAddressType: (String) -> Unit,
    @StringRes typeResId: Int,
) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp)

    TextFieldItem(
        modifier = modifier,
        value = city(),
        onTextChanged = updateCity,
        placeholderResId = cityResId,
    )
    TextFieldItem(
        modifier = modifier,
        value = region(),
        onTextChanged = updateRegion,
        placeholderResId = regionResId,
    )
    TextFieldItem(
        modifier = modifier,
        value = neighborhood(),
        onTextChanged = updateNeighborhood,
        placeholderResId = neighborhoodResId,
    )
    TextFieldItem(
        modifier = modifier,
        value = postCode(),
        onTextChanged = updatePostCode,
        placeholderResId = postCodeResId,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    TextFieldItem(
        modifier = modifier,
        value = country(),
        onTextChanged = updateCountry,
        placeholderResId = countryResId,
    )
    TextFieldItem(
        modifier = modifier,
        value = label(),
        onTextChanged = updateAddressLabel,
        placeholderResId = labelResId,
    )
    TextFieldItem(
        modifier = modifier,
        value = type(),
        onTextChanged = updateAddressType,
        placeholderResId = typeResId,
    )
}