package ui.dendi.contacts.presentation.delegate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ui.dendi.contacts.core.delegate.InputValidationDelegate
import ui.dendi.contacts.core.delegate.SectionMainDelegate
import ui.dendi.contacts.domain.model.Gender
import ui.dendi.contacts.domain.model.Person
import javax.inject.Inject

class SectionMainImpl @Inject constructor(
    private val inputValidationDelegate: InputValidationDelegate,
) : SectionMainDelegate {

    override var person: Person by mutableStateOf(Person())

    override fun updateFullName(fullName: String) {
        person = person.copy(fullName = fullName)
    }

    override fun updateLastName(lastName: String) {
        person = person.copy(lastName = lastName.trim())
        inputValidationDelegate.checkInputValidation()
    }

    override fun updateFirstName(firstName: String) {
        person = person.copy(firstName = firstName.trim())
        inputValidationDelegate.checkInputValidation()
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
@InstallIn(ViewModelComponent::class)
interface SectionMainModule {

    @ViewModelScoped
    @Binds
    fun binds(impl: SectionMainImpl): SectionMainDelegate
}