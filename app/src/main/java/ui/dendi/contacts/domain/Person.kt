package ui.dendi.contacts.domain

data class Person(
    val title: String,
    val fullName: String,
    val familyName: String,
    val givenName: String,
    val gender: String,
    val birthday: String,
    val occupation: String,
    val phoneNumber: PhoneNumber,
    val postalAddress: PostalAddress,
)

data class PhoneNumber(
    val phoneNumber: String,
    val type: String,
)

data class PostalAddress(
    val street: String,
    val city: String,
    val region: String,
    val neighborhood: String,
    val postCode: String,
    val poBox: String,
    val country: String,
    val type: String,
)