package ui.dendi.contacts.presentation.screen.contacts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ui.dendi.contacts.domain.ContactsRepository
import ui.dendi.contacts.domain.Person
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val repository: ContactsRepository,
) : ViewModel() {

    var contacts by mutableStateOf(emptyList<Person>())
        private set

    init {
        getContacts()
    }

    private fun getContacts() {
        viewModelScope.launch {
            repository.getContacts().collect {
                contacts = it
            }
        }
    }
}