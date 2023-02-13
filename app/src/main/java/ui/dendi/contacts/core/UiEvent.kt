package ui.dendi.contacts.core

sealed class UiEvent {
    object Success : UiEvent()
    data class ShowSnackbar(val message: UiText) : UiEvent()
}