package ui.dendi.contacts.data

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import ui.dendi.contacts.domain.ContactsRepository
import ui.dendi.contacts.domain.Person
import ui.dendi.contacts.domain.PhoneNumber
import ui.dendi.contacts.domain.PostalAddress
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(val realm: Realm) : ContactsRepository {

    override fun getContacts(): Flow<List<Person>> {
        return realm.query<PersonObject>().asFlow().map {
            it.list.map {
                it.toPerson()
            }
        }
    }

    override suspend fun insertContact(person: Person) {
        realm.write { copyToRealm(person.toPersonObject()) }
    }

    override suspend fun deleteContact(id: String) {
        realm.write {
            val person = query<PersonObject>(query = "_id == $0", ObjectId(id)).first().find()
            try {
                person?.let { delete(it) }
            } catch (e: Exception) {
                Log.d("ContactsRepositoryImpl", "${e.message}")
            }
        }
    }

    private fun PhoneNumberObject.toPhoneNumber(): PhoneNumber =
        PhoneNumber(phoneNumber = phoneNumber, type = type)

    private fun PostalAddressObject.toPostalAddress() = PostalAddress(
        street = street,
        city = city,
        region = region,
        neighborhood = neighborhood,
        postCode = postCode,
        poBox = poBox,
        country = country,
        type = type,
    )

    private fun PersonObject.toPerson(): Person {
        return Person(
//            id = id.toString(),
            title = title,
            fullName = fullName,
            familyName = familyName,
            givenName = givenName,
            gender = gender,
            birthday = birthday,
            occupation = occupation,
            phoneNumber = phoneNumber?.toPhoneNumber() ?: PhoneNumber(
                phoneNumber = "",
                type = "",
            ),
            postalAddress = postalAddress?.toPostalAddress() ?: PostalAddress(
                street = "",
                city = "",
                region = "",
                neighborhood = "",
                postCode = "",
                poBox = "",
                country = "",
                type = "",
            ),
        )
    }

    private fun PhoneNumber.toPhoneNumberObject(): PhoneNumberObject = PhoneNumberObject().apply {
        phoneNumber = this@toPhoneNumberObject.phoneNumber
        type = this@toPhoneNumberObject.type
    }

    private fun PostalAddress.toPostalAddressObject() = PostalAddressObject().apply {
        street = this@toPostalAddressObject.street
        city = this@toPostalAddressObject.city
        region = this@toPostalAddressObject.region
        neighborhood = this@toPostalAddressObject.neighborhood
        postCode = this@toPostalAddressObject.postCode
        poBox = this@toPostalAddressObject.poBox
        country = this@toPostalAddressObject.country
        type = this@toPostalAddressObject.type
    }

    private fun Person.toPersonObject(): PersonObject {
        return PersonObject().apply {
            title = this@toPersonObject.title
            fullName = this@toPersonObject.fullName
            familyName = this@toPersonObject.familyName
            givenName = this@toPersonObject.givenName
            gender = this@toPersonObject.gender
            birthday = this@toPersonObject.birthday
            occupation = this@toPersonObject.occupation
            phoneNumber = this@toPersonObject.phoneNumber.toPhoneNumberObject()
            postalAddress = this@toPersonObject.postalAddress.toPostalAddressObject()
        }
    }
}

