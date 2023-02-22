package ui.dendi.contacts.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun TextUnit.textSizeToDp(): Dp = with(LocalDensity.current) {
    this@textSizeToDp.toDp()
}