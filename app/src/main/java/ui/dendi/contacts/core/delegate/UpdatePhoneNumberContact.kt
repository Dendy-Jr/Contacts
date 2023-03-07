package ui.dendi.contacts.core.delegate

import ui.dendi.contacts.domain.model.PhoneNumber

interface UpdatePhoneNumberContact {

    var phoneNumber: PhoneNumber
    fun updatePhoneNumber(number: String)
    fun updatePhoneNumberLabel(label: String)
    fun updatePhoneNumberType(type: String)
}