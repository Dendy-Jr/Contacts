package ui.dendi.contacts.presentation.screen.create_contact.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CollapsedEmailAddressTextFields(
    label: () -> String,
    updateEmailAddressLabel: (String) -> Unit,
    @StringRes labelResId: Int,
    type: () -> String,
    updateEmailAddressType: (String) -> Unit,
    @StringRes typeResId: Int,
) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp)

    TextFieldItem(
        modifier = modifier,
        value = label(),
        onTextChanged = updateEmailAddressLabel,
        placeholderResId = labelResId,
    )
    TextFieldItem(
        modifier = modifier,
        value = type(),
        onTextChanged = updateEmailAddressType,
        placeholderResId = typeResId,
    )
}