package ui.dendi.contacts.core.delegate

import ui.dendi.contacts.core.model.UiText
import ui.dendi.contacts.domain.model.ContactInputValidationType

interface InputValidationDelegate {

    var enableDoneButton: Boolean
    var screenMessage: UiText?
    var showScreenMessage: Boolean
    fun checkInputValidation()
    fun processInputValidationType(type: ContactInputValidationType)
}