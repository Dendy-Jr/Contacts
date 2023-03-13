package ui.dendi.contacts.presentation.screen.create_contact

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
class CreateContactViewModel @Inject constructor(
    private val repository: ContactsRepository,
    private val sectionMainDelegate: SectionMainDelegate,
    private val sectionPhoneNumberDelegate: SectionPhoneNumberDelegate,
    private val sectionPostalAddressDelegate: SectionPostalAddressDelegate,
    private val sectionEmailAddressDelegate: SectionEmailAddressDelegate,
    private val sectionOrganizationDelegate: SectionOrganizationDelegate,
    private val sectionWebsiteDelegate: SectionWebsiteDelegate,
    private val sectionCalendarDelegate: SectionCalendarDelegate,
    private val inputValidationDelegate: InputValidationDelegate,
) : ViewModel(),
    SectionMainDelegate by sectionMainDelegate,
    SectionPhoneNumberDelegate by sectionPhoneNumberDelegate,
    SectionPostalAddressDelegate by sectionPostalAddressDelegate,
    SectionEmailAddressDelegate by sectionEmailAddressDelegate,
    SectionOrganizationDelegate by sectionOrganizationDelegate,
    SectionWebsiteDelegate by sectionWebsiteDelegate,
    SectionCalendarDelegate by sectionCalendarDelegate,
    InputValidationDelegate by inputValidationDelegate {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onDoneButtonClick() {
        viewModelScope.launch {
            repository.insertContact(
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
}