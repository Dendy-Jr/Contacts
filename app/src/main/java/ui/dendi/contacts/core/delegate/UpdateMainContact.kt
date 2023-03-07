package ui.dendi.contacts.core.delegate

import ui.dendi.contacts.domain.model.Person

interface UpdateMainContact {

    var person: Person
    fun updateFullName(fullName: String)
    fun updateLastName(lastName: String)
    fun updateFirstName(firstName: String)
    fun updateGender(type: String)
    fun updateImagePath(imagePath: String)
    fun updateBirthday(birthday: String)
    fun updateOccupation(occupation: String)
}