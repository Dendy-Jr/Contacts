package ui.dendi.contacts.core.delegate

import ui.dendi.contacts.domain.model.EmailAddress

interface SectionEmailAddressDelegate {

    var emailAddress: EmailAddress
    fun updateEmailAddress(link: String)
    fun updateEmailAddressType(type: String)
    fun updateEmailAddressLabel(label: String)
}