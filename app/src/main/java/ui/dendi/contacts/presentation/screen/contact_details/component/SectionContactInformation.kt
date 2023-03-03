package ui.dendi.contacts.presentation.screen.contact_details.component

import androidx.compose.runtime.Composable

@Composable
fun SectionContactInformation(sectionMap: Map<String, Int>) {
    sectionMap.onEachIndexed { index, (value, titleResId) ->
        ContactInformation(
            value = value,
            titleResId = titleResId,
            showDivider = index < sectionMap.toList().lastIndex
        )
    }
}