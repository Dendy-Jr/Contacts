package ui.dendi.contacts.domain.model

data class Person(
    val id: String = "",
    val fullName: String = "",
    val lastName: String = "",
    val firstName: String = "",
    val gender: String = "",
    val imagePath: String = "",
    val birthday: String = "",
    val occupation: String = "",
    val phoneNumber: PhoneNumber = PhoneNumber.DEFAULT,
    val postalAddress: PostalAddress = PostalAddress.DEFAULT,
    val emailAddress: EmailAddress = EmailAddress.DEFAULT,
    val organization: Organization = Organization.DEFAULT,
    val website: Website = Website.DEFAULT,
    val calendar: Calendar = Calendar.DEFAULT,
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$firstName$lastName",
            "$firstName $lastName",
            "${firstName.first()} ${lastName.first()}",
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

data class PhoneNumber(
    val number: String = "",
    val label: String = "",
    val type: String = "",
) {
    companion object {
        val DEFAULT = PhoneNumber(number = "", label = "", type = "")
    }
}

data class PostalAddress(
    val street: String = "",
    val city: String = "",
    val region: String = "",
    val neighborhood: String = "",
    val postCode: String = "",
    val country: String = "",
    val label: String = "",
    val type: String = "",
) {
    companion object {
        val DEFAULT = PostalAddress(
            street = "",
            city = "",
            region = "",
            neighborhood = "",
            postCode = "",
            country = "",
            label = "",
            type = "",
        )
    }
}

data class EmailAddress(
    val link: String = "",
    val type: String = "",
    val label: String = "",
) {
    companion object {
        val DEFAULT = EmailAddress(link = "", type = "", label = "")
    }
}

data class Organization(
    val name: String = "",
    val label: String = "",
    val jobTitle: String = "",
    val jobDescription: String = "",
    val department: String = "",
) {
    companion object {
        val DEFAULT = Organization(
            name = "",
            label = "",
            jobTitle = "",
            jobDescription = "",
            department = "",
        )
    }
}

data class Website(
    val link: String = "",
    val label: String = "",
    val type: String = "",
) {
    companion object {
        val DEFAULT = Website(link = "", label = "", type = "")
    }
}

data class Calendar(
    val link: String = "",
    val label: String = "",
    val type: String = "",
) {
    companion object {
        val DEFAULT = Calendar(link = "", label = "", type = "")
    }
}