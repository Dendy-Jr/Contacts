package ui.dendi.contacts.presentation.screen.create_contact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ui.dendi.contacts.domain.ContactsRepository
import ui.dendi.contacts.domain.Person
import ui.dendi.contacts.domain.PhoneNumber
import ui.dendi.contacts.domain.PostalAddress
import javax.inject.Inject

@HiltViewModel
class CreateContactViewModel @Inject constructor(
    private val repository: ContactsRepository,
) : ViewModel() {

    var title by mutableStateOf("")
        private set
    var fullName by mutableStateOf("")
        private set
    var familyName by mutableStateOf("")
        private set
    var givenName by mutableStateOf("")
        private set
    var gender by mutableStateOf("")
        private set
    var birthday by mutableStateOf("")
        private set
    var occupation by mutableStateOf("")
        private set
    var phoneNumber by mutableStateOf("")
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
    var poBox by mutableStateOf("")
        private set
    var country by mutableStateOf("")
        private set
    var postalAddressType by mutableStateOf("")
        private set

    var isError by mutableStateOf(true)
        private set

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateFullName(fullName: String) {
        this.fullName = fullName
    }

    fun updateFamilyName(familyName: String) {
        this.familyName = familyName
    }

    fun updateGivenName(givenName: String) {
        this.givenName = givenName
    }

    fun updateGender(gender: String) {
        this.gender = gender
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

    fun updatePoBox(poBox: String) {
        this.poBox = poBox
    }

    fun updateCountry(country: String) {
        this.country = country
    }

    fun updatePostalAddressType(postalAddressType: String) {
        this.postalAddressType = postalAddressType
    }

    fun insertPerson() {
        viewModelScope.launch {
            isError = isFieldsEmpty()
            if (isError) return@launch
            repository.insertContact(
                Person(
                    title = title,
                    fullName = fullName,
                    familyName = familyName,
                    givenName = givenName,
                    gender = gender,
                    birthday = birthday,
                    occupation = occupation,
                    phoneNumber = PhoneNumber(
                        phoneNumber = phoneNumber,
                        type = phoneNumberType,
                    ),
                    postalAddress = PostalAddress(
                        street = street,
                        city = city,
                        region = region,
                        neighborhood = neighborhood,
                        postCode = postCode,
                        poBox = poBox,
                        country = country,
                        type = postalAddressType,
                    )
                )
            )
        }
    }

    private fun isFieldsEmpty(): Boolean {
        return listOf(
            fullName,
            familyName,
            givenName,
            gender,
            birthday,
            phoneNumber,
            phoneNumberType,
            street,
            city,
            region,
            postCode,
            country,
            postalAddressType,
        ).any { it.isBlank() }
    }
}