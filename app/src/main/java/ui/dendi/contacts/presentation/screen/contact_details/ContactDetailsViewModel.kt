package ui.dendi.contacts.presentation.screen.contact_details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ui.dendi.contacts.domain.ContactsRepository
import ui.dendi.contacts.domain.Person
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
) : ViewModel() {

    suspend fun getContactDetails(id: String): Person? {
        return contactsRepository.getContact(id)
    }
}