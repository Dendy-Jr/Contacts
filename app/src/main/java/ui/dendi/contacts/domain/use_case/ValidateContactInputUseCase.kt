package ui.dendi.contacts.domain.use_case

import ui.dendi.contacts.core.extension.containsNumber
import ui.dendi.contacts.core.extension.containsSpecialChar
import ui.dendi.contacts.domain.model.ContactInputValidationType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidateContactInputUseCase @Inject constructor() {

    operator fun invoke(firstName: String, lastName: String): ContactInputValidationType {
        if (firstName.isBlank() || lastName.isBlank()) {
            return ContactInputValidationType.EmptyField
        }
        if (firstName.containsNumber() || lastName.containsNumber()) {
            return ContactInputValidationType.FieldContainsNumber
        }
        if (firstName.containsSpecialChar() || lastName.containsSpecialChar()) {
            return ContactInputValidationType.FieldContainsSpecialCharacter
        }
        return ContactInputValidationType.Valid
    }
}