package ui.dendi.contacts.presentation.screen.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ui.dendi.contacts.domain.model.Person
import ui.dendi.contacts.domain.repository.ContactsRepository
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val repository: ContactsRepository,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _contacts = MutableStateFlow(listOf<Person>())
    val contacts = searchText
        .combine(_contacts) { text, contacts ->
            if (text.isBlank()) {
                contacts
            } else {
                contacts.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), _contacts.value)

    init {
        getContacts()
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    private fun getContacts() {
        viewModelScope.launch {
            repository.getContacts().collect {
                _contacts.value = it
            }
        }
    }
}