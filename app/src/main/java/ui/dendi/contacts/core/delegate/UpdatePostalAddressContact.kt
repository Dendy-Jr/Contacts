package ui.dendi.contacts.core.delegate

import ui.dendi.contacts.domain.model.PostalAddress

interface UpdatePostalAddressContact {

    var postalAddress: PostalAddress
    fun updateStreet(street: String)
    fun updateCity(city: String)
    fun updateRegion(region: String)
    fun updateNeighborhood(neighborhood: String)
    fun updatePostCode(postCode: String)
    fun updateCountry(country: String)
    fun updatePostalAddressLabel(label: String)
    fun updatePostalAddressType(type: String)
}