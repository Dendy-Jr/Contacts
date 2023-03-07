package ui.dendi.contacts.presentation.component.create_edit

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CollapsedOrganizationTextFields(
    label: () -> String,
    updateOrganizationLabel: (String) -> Unit,
    @StringRes labelResId: Int,
    jobTitle: () -> String,
    updateJobTitle: (String) -> Unit,
    @StringRes jobTitleResId: Int,
    jobDescription: () -> String,
    updateJobDescription: (String) -> Unit,
    @StringRes jobDescriptionResId: Int,
    department: () -> String,
    updateDepartment: (String) -> Unit,
    @StringRes departmentResId: Int,
) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp)

    TextFieldItem(
        modifier = modifier,
        value = label(),
        onTextChanged = updateOrganizationLabel,
        placeholderResId = labelResId,
    )
    TextFieldItem(
        modifier = modifier,
        value = jobTitle(),
        onTextChanged = updateJobTitle,
        placeholderResId = jobTitleResId,
    )
    TextFieldItem(
        modifier = modifier,
        value = jobDescription(),
        onTextChanged = updateJobDescription,
        placeholderResId = jobDescriptionResId,
    )
    TextFieldItem(
        modifier = modifier,
        value = department(),
        onTextChanged = updateDepartment,
        placeholderResId = departmentResId,
    )
}