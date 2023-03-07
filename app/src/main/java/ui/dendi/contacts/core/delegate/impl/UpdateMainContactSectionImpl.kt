package ui.dendi.contacts.core.delegate.impl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ui.dendi.contacts.core.delegate.InputValidation
import ui.dendi.contacts.core.delegate.UpdateMainContact
import ui.dendi.contacts.domain.model.Gender
import ui.dendi.contacts.domain.model.Person
import javax.inject.Inject
import javax.inject.Singleton

class UpdateMainContactSectionImpl @Inject constructor(
    private val inputValidation: InputValidation,
) : UpdateMainContact {

    override var person: Person by mutableStateOf(Person())

    override fun updateFullName(fullName: String) {
        person = person.copy(fullName = fullName)
    }

    override fun updateLastName(lastName: String) {
        person = person.copy(lastName = lastName.trim())
        inputValidation.checkInputValidation()
    }

    override fun updateFirstName(firstName: String) {
        person = person.copy(firstName = firstName.trim())
        inputValidation.checkInputValidation()
    }

    override fun updateGender(type: String) {
        person = person.copy(gender = Gender.valueOf(type))
    }

    override fun updateImagePath(imagePath: String) {
        person = person.copy(imagePath = imagePath)
    }

    override fun updateBirthday(birthday: String) {
        person = person.copy(birthday = birthday)
    }

    override fun updateOccupation(occupation: String) {
        person = person.copy(occupation = occupation)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface UpdateMainContactSectionModule {

    @Singleton
    @Binds
    fun binds(impl: UpdateMainContactSectionImpl): UpdateMainContact
}