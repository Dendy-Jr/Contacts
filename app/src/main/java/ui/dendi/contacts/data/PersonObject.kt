package ui.dendi.contacts.data

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class PersonObject : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var title: String = ""
    var fullName: String = ""
    var lastName: String = ""
    var firstName: String = ""
    var gender: String = ""
    var birthday: String = ""
    var occupation: String = ""
    var phoneNumber: PhoneNumberObject? = null
    var postalAddress: PostalAddressObject? = null
    var emailAddress: EmailAddressObject? = null
    var organization: OrganizationObject? = null
    var website: WebsiteObject? = null
    var calendar: CalendarObject? = null
}

class PhoneNumberObject : RealmObject {
    var phoneNumber: String = ""
    var label: String = ""
    var type: String = ""
}

class PostalAddressObject : RealmObject {
    var street: String = ""
    var city: String = ""
    var region: String = ""
    var neighborhood: String = ""
    var postCode: String = ""
    var country: String = ""
    var label: String = ""
    var type: String = ""
}

class EmailAddressObject : RealmObject {
    var emailAddress: String = ""
    var type: String = ""
    var label: String = ""
}

class OrganizationObject : RealmObject {
    var organizationName: String = ""
    var label: String = ""
    var jobTitle: String = ""
    var jobDescription: String = ""
    var department: String = ""
}

class WebsiteObject : RealmObject {
    var link: String = ""
    var label: String = ""
    var type: String = ""
}

class CalendarObject : RealmObject {
    var calendarLink: String = ""
    var label: String = ""
    var type: String = ""
}