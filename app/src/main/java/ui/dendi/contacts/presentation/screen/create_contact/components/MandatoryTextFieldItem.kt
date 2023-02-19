package ui.dendi.contacts.presentation.screen.create_contact.components

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandatoryTextFieldItem(
    modifier: Modifier = Modifier,
    value: String,
    onTextChanged: (String) -> Unit,
    @StringRes placeholderResId: Int,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onTextChanged,
        placeholder = {
            Text(text = stringResource(placeholderResId))
        },
        isError = isError,
        trailingIcon = {
            if (isError) {
                Icon(
                    imageVector = Icons.Rounded.Error,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                )
            } else if (value.isNotBlank()) {
                IconButton(onClick = {
                    onTextChanged("")
                }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null,
                    )
                }
            }
        },
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(percent = 15),
    )
}