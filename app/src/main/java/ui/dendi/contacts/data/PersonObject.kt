package ui.dendi.contacts.data

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class PersonObject : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var title: String = ""
    var fullName: String = ""
    var familyName: String = ""
    var givenName: String = ""
    var gender: String = ""
    var birthday: String = ""
    var occupation: String = ""
    var phoneNumber: PhoneNumberObject? = null
    var postalAddress: PostalAddressObject? = null
}

class PhoneNumberObject : RealmObject {
    var phoneNumber: String = ""
    var type: String = ""
}

class PostalAddressObject : RealmObject {
    var street: String = ""
    var city: String = ""
    var region: String = ""
    var neighborhood: String = ""
    var postCode: String = ""
    var poBox: String = ""
    var country: String = ""
    var type: String = ""
}