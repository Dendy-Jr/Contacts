package ui.dendi.contacts.data.model

import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId

class PersonObject : RealmObject {
    @PrimaryKey
    var id: String = BsonObjectId().toHexString()
    var fullName: String = ""
    var lastName: String = ""
    var firstName: String = ""
    var gender: String = ""
    var imagePath: String = ""
    var birthday: String = ""
    var occupation: String = ""
    var phoneNumber: PhoneNumberObject? = null
    var postalAddress: PostalAddressObject? = null
    var emailAddress: EmailAddressObject? = null
    var organization: OrganizationObject? = null
    var website: WebsiteObject? = null
    var calendar: CalendarObject? = null
}

class PhoneNumberObject : EmbeddedRealmObject {
    var number: String = ""
    var label: String = ""
    var type: String = ""
}

class PostalAddressObject : EmbeddedRealmObject {
    var street: String = ""
    var city: String = ""
    var region: String = ""
    var neighborhood: String = ""
    var postCode: String = ""
    var country: String = ""
    var label: String = ""
    var type: String = ""
}

class EmailAddressObject : EmbeddedRealmObject {
    var emailAddress: String = ""
    var type: String = ""
    var label: String = ""
}

class OrganizationObject : EmbeddedRealmObject {
    var name: String = ""
    var label: String = ""
    var jobTitle: String = ""
    var jobDescription: String = ""
    var department: String = ""
}

class WebsiteObject : EmbeddedRealmObject {
    var link: String = ""
    var label: String = ""
    var type: String = ""
}

class CalendarObject : EmbeddedRealmObject {
    var link: String = ""
    var label: String = ""
    var type: String = ""
}