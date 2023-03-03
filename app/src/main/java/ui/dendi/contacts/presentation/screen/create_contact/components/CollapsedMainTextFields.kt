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
fun CollapsedMainTextFields(
    fullName: () -> String,
    updateFullName: (String) -> Unit,
    @StringRes fullNameResId: Int,
    genderOptions: List<String>,
    updateGender: (String) -> Unit,
    @StringRes genderResId: Int,
    birthday: () -> String,
    updateBirthday: (String) -> Unit,
    @StringRes birthdayResId: Int,
    occupation: () -> String,
    updateOccupation: (String) -> Unit,
    @StringRes occupationResId: Int,
) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp)

    TextFieldItem(
        modifier = modifier,
        value = fullName(),
        onTextChanged = updateFullName,
        placeholderResId = fullNameResId,
    )
    GenderDropdownMenu(
        modifier = modifier,
        options = genderOptions,
        onChangeOption = { updateGender(it) },
        labelTextId = genderResId,
    )
    TextFieldItem(
        modifier = modifier,
        value = birthday(),
        onTextChanged = updateBirthday,
        placeholderResId = birthdayResId,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    TextFieldItem(
        modifier = modifier,
        value = occupation(),
        onTextChanged = updateOccupation,
        placeholderResId = occupationResId,
    )
}