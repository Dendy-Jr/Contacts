package ui.dendi.contacts.presentation.component.create_edit

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CollapsedWebsiteTextFields(
    label: () -> String,
    updateWebsiteLabel: (String) -> Unit,
    @StringRes labelResId: Int,
    type: () -> String,
    updateWebsiteType: (String) -> Unit,
    @StringRes typeResId: Int,
) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp)

    TextFieldItem(
        modifier = modifier,
        value = label(),
        onTextChanged = updateWebsiteLabel,
        placeholderResId = labelResId,
    )
    TextFieldItem(
        modifier = modifier,
        value = type(),
        onTextChanged = updateWebsiteType,
        placeholderResId = typeResId,
    )
}