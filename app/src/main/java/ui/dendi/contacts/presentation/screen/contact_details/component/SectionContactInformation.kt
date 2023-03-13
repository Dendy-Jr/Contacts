package ui.dendi.contacts.presentation.screen.contact_details.component

import androidx.compose.runtime.Composable

@Composable
fun SectionContactInformation(sectionMap: Map<Int, String>) {
    sectionMap.onEachIndexed { index, (titleResId, value) ->
        ContactInformation(
            value = value,
            titleResId = titleResId,
            showDivider = index < sectionMap.toList().lastIndex
        )
    }
}