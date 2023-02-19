package ui.dendi.contacts.domain

data class Person(
    val title: String,
    val fullName: String,
    val lastName: String,
    val firstName: String,
    val gender: String,
    val imagePath: String,
    val birthday: String,
    val occupation: String,
    val phoneNumber: PhoneNumber,
    val postalAddress: PostalAddress,
    val emailAddress: EmailAddress,
    val organization: Organization,
    val website: Website,
    val calendar: Calendar,
)

data class PhoneNumber(
    val phoneNumber: String,
    val label: String,
    val type: String,
)

data class PostalAddress(
    val street: String,
    val city: String,
    val region: String,
    val neighborhood: String,
    val postCode: String,
    val country: String,
    val label: String,
    val type: String,
)

data class EmailAddress(
    val emailAddress: String,
    val type: String,
    val label: String,
)

data class Organization(
    val organizationName: String,
    val label: String,
    val jobTitle: String,
    val jobDescription: String,
    val department: String,
)

data class Website(
    val link: String,
    val label: String,
    val type: String,
)

data class Calendar(
    val calendarLink: String,
    val label: String,
    val type: String,
)