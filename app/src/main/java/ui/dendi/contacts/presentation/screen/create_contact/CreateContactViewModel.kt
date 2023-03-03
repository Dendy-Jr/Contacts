package ui.dendi.contacts.presentation.screen.create_contact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import ui.dendi.contacts.R
import ui.dendi.contacts.core.model.UiEvent
import ui.dendi.contacts.core.model.UiText
import ui.dendi.contacts.domain.model.*
import ui.dendi.contacts.domain.repository.ContactsRepository
import ui.dendi.contacts.domain.use_case.ValidateContactInputUseCase
import javax.inject.Inject

@HiltViewModel
class CreateContactViewModel @Inject constructor(
    private val repository: ContactsRepository,
    private val validateContactInputUseCase: ValidateContactInputUseCase,
) : ViewModel() {

    var person by mutableStateOf(Person())
        private set
    var phoneNumber by mutableStateOf(PhoneNumber())
        private set
    var postalAddress by mutableStateOf(PostalAddress())
        private set
    var emailAddress by mutableStateOf(EmailAddress())
        private set
    var organization by mutableStateOf(Organization())
        private set
    var website by mutableStateOf(Website())
        private set
    var calendar by mutableStateOf(Calendar())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var enableDoneButton by mutableStateOf(false)
        private set

    var screenMessage by mutableStateOf<UiText?>(null)
        private set
    var showScreenMessage by mutableStateOf(false)
        private set

    fun updateFullName(fullName: String) {
        person = person.copy(fullName = fullName)
    }

    fun updateLastName(lastName: String) {
        person = person.copy(lastName = lastName.trim())
        checkInputValidation()
    }

    fun updateFirstName(firstName: String) {
        person = person.copy(firstName = firstName.trim())
        checkInputValidation()
    }

    fun updateGender(type: String) {
        person = person.copy(gender = Gender.valueOf(type))
    }

    fun updateImagePath(imagePath: String) {
        person = person.copy(imagePath = imagePath)
    }

    fun updateBirthday(birthday: String) {
        person = person.copy(birthday = birthday)
    }

    fun updateOccupation(occupation: String) {
        person = person.copy(occupation = occupation)
    }

    fun updatePhoneNumber(number: String) {
        phoneNumber = phoneNumber.copy(number = number)
    }

    fun updatePhoneNumberLabel(label: String) {
        phoneNumber = phoneNumber.copy(label = label)
    }

    fun updatePhoneNumberType(type: String) {
        phoneNumber = phoneNumber.copy(type = type)
    }

    fun updateStreet(street: String) {
        postalAddress = postalAddress.copy(street = street)
    }

    fun updateCity(city: String) {
        postalAddress = postalAddress.copy(city = city)
    }

    fun updateRegion(region: String) {
        postalAddress = postalAddress.copy(region = region)
    }

    fun updateNeighborhood(neighborhood: String) {
        postalAddress = postalAddress.copy(neighborhood = neighborhood)
    }

    fun updatePostCode(postCode: String) {
        postalAddress = postalAddress.copy(postCode = postCode)
    }

    fun updateCountry(country: String) {
        postalAddress = postalAddress.copy(country = country)
    }

    fun updatePostalAddressLabel(label: String) {
        postalAddress = postalAddress.copy(label = label)
    }

    fun updatePostalAddressType(type: String) {
        postalAddress = postalAddress.copy(type = type)
    }

    fun updateEmailAddress(link: String) {
        emailAddress = emailAddress.copy(link = link)
    }

    fun updateEmailAddressType(type: String) {
        emailAddress = emailAddress.copy(type = type)
    }

    fun updateEmailAddressLabel(label: String) {
        emailAddress = emailAddress.copy(label = label)
    }

    fun updateOrganizationName(name: String) {
        organization = organization.copy(name = name)
    }

    fun updateOrganizationLabel(label: String) {
        organization = organization.copy(label = label)
    }

    fun updateJobTitle(jobTitle: String) {
        organization = organization.copy(jobTitle = jobTitle)
    }

    fun updateJobDescription(jobDescription: String) {
        organization = organization.copy(jobDescription = jobDescription)
    }

    fun updateDepartment(department: String) {
        organization = organization.copy(department = department)
    }

    fun updateWebsiteLink(link: String) {
        website = website.copy(link = link)
    }

    fun updateWebsiteLabel(label: String) {
        website = website.copy(label = label)
    }

    fun updateWebsiteType(type: String) {
        website = website.copy(type = type)
    }

    fun updateCalendarLink(link: String) {
        calendar = calendar.copy(link = link)
    }

    fun updateCalendarLabel(label: String) {
        calendar = calendar.copy(label = label)
    }

    fun updateCalendarType(type: String) {
        calendar = calendar.copy(type = type)
    }

    fun onDoneButtonClick() {
        viewModelScope.launch {
            repository.insertContact(
                person = person.copy(
                    id = ObjectId.invoke().toString(),
                    phoneNumber = phoneNumber,
                    postalAddress = postalAddress,
                    emailAddress = emailAddress,
                    organization = organization,
                    website = website,
                    calendar = calendar
                )
            )
            _uiEvent.send(UiEvent.Success)
//            _uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(resId = R.string.contact_created)))
        }
    }

    private fun checkInputValidation() {
        viewModelScope.launch {
            val validationResult = validateContactInputUseCase(
                firstName = person.firstName,
                lastName = person.lastName
            )
            processInputValidationType(validationResult)
        }
    }

    private fun processInputValidationType(type: ContactInputValidationType) {
        when (type) {
            ContactInputValidationType.EmptyField -> {
                enableDoneButton = false
                showScreenMessage = true
                screenMessage = UiText.StringResource(resId = R.string.name_cannot_be_empty)
            }
            ContactInputValidationType.FieldContainsNumber -> {
                enableDoneButton = false
                showScreenMessage = true
                screenMessage = UiText.StringResource(resId = R.string.name_cannot_contain_numbers)
            }
            ContactInputValidationType.FieldContainsSpecialCharacter -> {
                enableDoneButton = false
                showScreenMessage = true
                screenMessage =
                    UiText.StringResource(resId = R.string.name_cannot_contain_special_characters)
            }
            ContactInputValidationType.Valid -> {
                enableDoneButton = true
                showScreenMessage = false
            }
        }
    }
}