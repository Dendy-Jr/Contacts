package ui.dendi.contacts.presentation.screen.create_contact.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsedTextFieldItem(
    modifier: Modifier = Modifier,
    value: String,
    onTextChanged: (String) -> Unit,
    @StringRes placeholderResId: Int,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        value = value,
        onValueChange = onTextChanged,
        placeholder = {
            Text(text = stringResource(placeholderResId))
        },
        trailingIcon = {
            if (value.isNotBlank()) {
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