package ui.dendi.contacts.presentation.component.create_edit

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderDropdownMenu(
    modifier: Modifier = Modifier,
    options: List<String>,
    onChangeOption: (String) -> Unit,
    @StringRes labelTextId: Int,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    var rowSize by remember { mutableStateOf(Size.Zero) }

    Row(modifier = modifier.onGloballyPositioned { layoutCoordinates ->
        rowSize = layoutCoordinates.size.toSize()
    }) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 55.dp)
                    .border(
                        width = 0.5.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(25.dp),
                    )
                    .shadow(3.dp, RoundedCornerShape(25.dp))
                    .menuAnchor(),
                value = selectedOptionText,
                readOnly = true,
                onValueChange = {},
                label = { Text(stringResource(id = labelTextId)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(containerColor = Color.White),
            )
            ExposedDropdownMenu(
                modifier = Modifier
                    .background(Color.White)
                    .width(with(LocalDensity.current) { rowSize.width.toDp() }),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            onChangeOption(selectionOption)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}