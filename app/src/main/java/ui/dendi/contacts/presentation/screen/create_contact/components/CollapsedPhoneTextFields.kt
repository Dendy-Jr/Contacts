package ui.dendi.contacts.presentation.screen.create_contact.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CollapsedPhoneTextFields(
    label: () -> String,
    updatePhoneNumberLabel: (String) -> Unit,
    @StringRes labelResId: Int,
    type: () -> String,
    updatePhoneNumberType: (String) -> Unit,
    @StringRes typeResId: Int,
) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp)

    TextFieldItem(
        modifier = modifier,
        value = label(),
        onTextChanged = updatePhoneNumberLabel,
        placeholderResId = labelResId,
    )
    TextFieldItem(
        modifier = modifier,
        value = type(),
        onTextChanged = updatePhoneNumberType,
        placeholderResId = typeResId,
    )
}