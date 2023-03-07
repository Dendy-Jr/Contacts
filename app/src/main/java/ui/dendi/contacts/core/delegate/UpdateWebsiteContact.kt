package ui.dendi.contacts.core.delegate

import ui.dendi.contacts.domain.model.Website

interface UpdateWebsiteContact {

    var website: Website
    fun updateWebsiteLink(link: String)
    fun updateWebsiteLabel(label: String)
    fun updateWebsiteType(type: String)
}