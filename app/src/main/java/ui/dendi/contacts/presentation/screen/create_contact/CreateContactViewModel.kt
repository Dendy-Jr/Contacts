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
import ui.dendi.contacts.core.UiEvent
import ui.dendi.contacts.domain.*
import javax.inject.Inject

@HiltViewModel
class CreateContactViewModel @Inject constructor(
    private val repository: ContactsRepository,
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var title by mutableStateOf("")
        private set
    var fullName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var firstName by mutableStateOf("")
        private set
    var gender by mutableStateOf("")
        private set
    var imagePath by mutableStateOf("")
        private set
    var birthday by mutableStateOf("")
        private set
    var occupation by mutableStateOf("")
        private set

    var phoneNumber by mutableStateOf("")
        private set
    var phoneNumberLabel by mutableStateOf("")
        private set
    var phoneNumberType by mutableStateOf("")
        private set

    var street by mutableStateOf("")
        private set
    var city by mutableStateOf("")
        private set
    var region by mutableStateOf("")
        private set
    var neighborhood by mutableStateOf("")
        private set
    var postCode by mutableStateOf("")
        private set
    var country by mutableStateOf("")
        private set
    var postalAddressLabel by mutableStateOf("")
        private set
    var postalAddressType by mutableStateOf("")
        private set

    var emailAddress by mutableStateOf("")
        private set
    var emailAddressType by mutableStateOf("")
        private set
    var emailAddressLabel by mutableStateOf("")
        private set

    var organizationName by mutableStateOf("")
        private set
    var organizationLabel by mutableStateOf("")
        private set
    var jobTitle by mutableStateOf("")
        private set
    var jobDescription by mutableStateOf("")
        private set
    var department by mutableStateOf("")
        private set

    var websiteLink by mutableStateOf("")
        private set
    var websiteLabel by mutableStateOf("")
        private set
    var websiteType by mutableStateOf("")
        private set

    var calendarLink by mutableStateOf("")
        private set
    var calendarLabel by mutableStateOf("")
        private set
    var calendarType by mutableStateOf("")
        private set

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateFullName(fullName: String) {
        this.fullName = fullName
    }

    fun updateLastName(lastName: String) {
        this.lastName = lastName
    }

    fun updateFirstName(firstName: String) {
        this.firstName = firstName
    }

    fun updateGender(gender: String) {
        this.gender = gender
    }

    fun updateImagePath(imagePath: String) {
        this.imagePath = imagePath
    }

    fun updateBirthday(birthday: String) {
        this.birthday = birthday
    }

    fun updateOccupation(occupation: String) {
        this.occupation = occupation
    }

    fun updatePhoneNumber(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }

    fun updatePhoneNumberLabel(phoneNumberLabel: String) {
        this.phoneNumberLabel = phoneNumberLabel
    }

    fun updatePhoneNumberType(phoneNumberType: String) {
        this.phoneNumberType = phoneNumberType
    }

    fun updateStreet(street: String) {
        this.street = street
    }

    fun updateCity(city: String) {
        this.city = city
    }

    fun updateRegion(region: String) {
        this.region = region
    }

    fun updateNeighborhood(neighborhood: String) {
        this.neighborhood = neighborhood
    }

    fun updatePostCode(postCode: String) {
        this.postCode = postCode
    }

    fun updateCountry(country: String) {
        this.country = country
    }

    fun updatePostalAddressLabel(postalAddressLabel: String) {
        this.postalAddressLabel = postalAddressLabel
    }

    fun updatePostalAddressType(postalAddressType: String) {
        this.postalAddressType = postalAddressType
    }

    fun updateEmailAddress(emailAddress: String) {
        this.emailAddress = emailAddress
    }

    fun updateEmailAddressType(emailAddressType: String) {
        this.emailAddressType = emailAddressType
    }

    fun updateEmailAddressLabel(emailAddressLabel: String) {
        this.emailAddressLabel = emailAddressLabel
    }

    fun updateOrganizationName(organizationName: String) {
        this.organizationName = organizationName
    }

    fun updateOrganizationLabel(organizationLabel: String) {
        this.organizationLabel = organizationLabel
    }

    fun updateJobTitle(jobTitle: String) {
        this.jobTitle = jobTitle
    }

    fun updateJobDescription(jobDescription: String) {
        this.jobDescription = jobDescription
    }

    fun updateDepartment(department: String) {
        this.department = department
    }

    fun updateWebsiteLink(websiteLink: String) {
        this.websiteLink = websiteLink
    }

    fun updateWebsiteLabel(websiteLabel: String) {
        this.websiteLabel = websiteLabel
    }

    fun updateWebsiteType(websiteType: String) {
        this.websiteType = websiteType
    }

    fun updateCalendarLink(calendarLink: String) {
        this.calendarLink = calendarLink
    }

    fun updateCalendarLabel(calendarLabel: String) {
        this.calendarLabel = calendarLabel
    }

    fun updateCalendarType(calendarType: String) {
        this.calendarType = calendarType
    }

    fun onSaveButtonClick() {
        viewModelScope.launch {
            insertContact()
            _uiEvent.send(UiEvent.Success)
        }
    }

    fun showDoneButton(): Boolean = firstName.isNotBlank() && lastName.isNotBlank()

    private suspend fun insertContact() {
        repository.insertContact(
            Person(
                title = title,
                fullName = fullName,
                lastName = lastName.trim(),
                firstName = firstName.trim(),
                gender = gender,
                imagePath = imagePath,
                birthday = birthday,
                occupation = occupation,
                phoneNumber = PhoneNumber(
                    phoneNumber = phoneNumber,
                    label = phoneNumberLabel,
                    type = phoneNumberType,
                ),
                postalAddress = PostalAddress(
                    street = street,
                    city = city,
                    region = region,
                    neighborhood = neighborhood,
                    postCode = postCode,
                    country = country,
                    label = postalAddressLabel,
                    type = postalAddressType,
                ),
                emailAddress = EmailAddress(
                    emailAddress = emailAddress,
                    type = emailAddressType,
                    label = emailAddressLabel,
                ),
                organization = Organization(
                    organizationName = organizationName,
                    label = organizationLabel,
                    jobTitle = jobTitle,
                    jobDescription = jobDescription,
                    department = department,
                ),
                website = Website(link = websiteLink, label = websiteLabel, type = websiteType),
                calendar = Calendar(
                    calendarLink = calendarLink,
                    label = calendarLabel,
                    type = calendarType,
                ),
            )
        )
    }
}