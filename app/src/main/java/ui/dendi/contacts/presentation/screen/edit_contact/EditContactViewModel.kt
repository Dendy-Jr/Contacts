package ui.dendi.contacts.presentation.screen.edit_contact

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ui.dendi.contacts.core.delegate.*
import ui.dendi.contacts.core.model.UiEvent
import ui.dendi.contacts.domain.repository.ContactsRepository
import javax.inject.Inject

@HiltViewModel
class EditContactViewModel @Inject constructor(
    private val repository: ContactsRepository,
    savedStateHandle: SavedStateHandle,
    private val updateMainContact: UpdateMainContact,
    private val updatePhoneNumberContact: UpdatePhoneNumberContact,
    private val updatePostalAddressContact: UpdatePostalAddressContact,
    private val updateEmailAddressContact: UpdateEmailAddressContact,
    private val updateOrganizationContact: UpdateOrganizationContact,
    private val updateWebsiteContact: UpdateWebsiteContact,
    private val updateCalendarContact: UpdateCalendarContact,
    private val inputValidation: InputValidation,
) : ViewModel(), UpdateMainContact by updateMainContact,
    UpdatePhoneNumberContact by updatePhoneNumberContact,
    UpdatePostalAddressContact by updatePostalAddressContact,
    UpdateEmailAddressContact by updateEmailAddressContact,
    UpdateOrganizationContact by updateOrganizationContact,
    UpdateWebsiteContact by updateWebsiteContact,
    UpdateCalendarContact by updateCalendarContact,
    InputValidation by inputValidation {

    private val id = checkNotNull(savedStateHandle.get<String>("id"))

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getContactById()
        checkInputValidation()
    }

    fun onDoneButtonClick() {
        viewModelScope.launch {
            repository.updateContact(
                person = person.copy(
                    phoneNumber = phoneNumber,
                    postalAddress = postalAddress,
                    emailAddress = emailAddress,
                    organization = organization,
                    website = website,
                    calendar = calendar
                )
            )
            _uiEvent.send(UiEvent.Success)
        }
    }

    private fun getContactById() {
        viewModelScope.launch {
            repository.getContact(id)?.let { oldDataPerson ->
                person = oldDataPerson
                phoneNumber = oldDataPerson.phoneNumber
                postalAddress = oldDataPerson.postalAddress
                emailAddress = oldDataPerson.emailAddress
                organization = oldDataPerson.organization
                website = oldDataPerson.website
                calendar = oldDataPerson.calendar
            } ?: return@launch
        }
    }
}