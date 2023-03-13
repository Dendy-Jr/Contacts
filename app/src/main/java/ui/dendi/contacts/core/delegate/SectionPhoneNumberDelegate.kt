package ui.dendi.contacts.core.delegate

import ui.dendi.contacts.domain.model.PhoneNumber

interface SectionPhoneNumberDelegate {

    var phoneNumber: PhoneNumber
    fun updatePhoneNumber(number: String)
    fun updatePhoneNumberLabel(label: String)
    fun updatePhoneNumberType(type: String)
}