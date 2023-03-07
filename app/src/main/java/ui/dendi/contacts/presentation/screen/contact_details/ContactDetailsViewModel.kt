package ui.dendi.contacts.presentation.screen.contact_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ui.dendi.contacts.core.model.UiEvent
import ui.dendi.contacts.domain.model.Person
import ui.dendi.contacts.domain.repository.ContactsRepository
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
) : ViewModel() {

    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()
    suspend fun getContactDetails(id: String): Person? {
        return contactsRepository.getContact(id)
    }

    fun onDeleteButtonClick(id: String) {
        viewModelScope.launch {
            contactsRepository.deleteContact(id)
            _event.send(UiEvent.Success)
        }
    }
}