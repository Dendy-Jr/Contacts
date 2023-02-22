package ui.dendi.contacts.core.model

sealed class UiEvent {
    object Success : UiEvent()
    data class ShowSnackbar(val message: UiText) : UiEvent()
}