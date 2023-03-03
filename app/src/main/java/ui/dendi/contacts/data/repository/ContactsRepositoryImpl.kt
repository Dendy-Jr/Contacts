package ui.dendi.contacts.data.repository

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import timber.log.Timber
import ui.dendi.contacts.data.model.*
import ui.dendi.contacts.domain.model.*
import ui.dendi.contacts.domain.repository.ContactsRepository
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

    override suspend fun getContact(id: String): Person? {
        val person = realm.query<PersonObject>(query = "id == $0", ObjectId(id)).first().find()
        return person?.toPerson()
    }

    override suspend fun deleteContact(id: String) {
        realm.write {
            val person = query<PersonObject>(query = "id == $0", ObjectId(id)).first().find()
            try {
                person?.let { delete(it) }
            } catch (e: Exception) {
                Timber.tag("ContactsRepositoryImpl").d(e.message)
            }
        }
    }

    private fun PhoneNumberObject.toPhoneNumber(): PhoneNumber {
        return PhoneNumber(number = number, label = label, type = type)
    }

    private fun PostalAddressObject.toPostalAddress() = PostalAddress(
        street = street,
        city = city,
        region = region,
        neighborhood = neighborhood,
        postCode = postCode,
        country = country,
        label = label,
        type = type,
    )

    private fun EmailAddressObject.toEmailAddress(): EmailAddress {
        return EmailAddress(link = emailAddress, type = type, label = label)
    }

    private fun OrganizationObject.toOrganization(): Organization {
        return Organization(
            name = name,
            label = label,
            jobTitle = jobTitle,
            jobDescription = jobDescription,
            department = department,
        )
    }

    private fun WebsiteObject.toWebsite(): Website {
        return Website(link = link, label = label, type = type)
    }

    private fun CalendarObject.toCalendar(): Calendar {
        return Calendar(link = link, label = label, type = type)
    }

    private fun PersonObject.toPerson(): Person {
        return Person(
            id = id.toHexString(),
            fullName = fullName,
            lastName = lastName,
            firstName = firstName,
            gender = Gender.valueOf(gender),
            imagePath = imagePath,
            birthday = birthday,
            occupation = occupation,
            phoneNumber = phoneNumber?.toPhoneNumber() ?: PhoneNumber(
                number = "",
                label = "",
                type = "",
            ),
            postalAddress = postalAddress?.toPostalAddress() ?: PostalAddress(
                street = "",
                city = "",
                region = "",
                neighborhood = "",
                postCode = "",
                country = "",
                label = "",
                type = "",
            ),
            emailAddress = emailAddress?.toEmailAddress() ?: EmailAddress(
                link = "",
                type = "",
                label = "",
            ),
            organization = organization?.toOrganization() ?: Organization(
                name = "",
                label = "",
                jobTitle = "",
                jobDescription = "",
                department = "",
            ),
            website = website?.toWebsite() ?: Website(link = "", label = "", type = ""),
            calendar = calendar?.toCalendar() ?: Calendar(link = "", label = "", type = ""),
        )
    }

    private fun Calendar.toCalendarObject() = CalendarObject().apply {
        link = this@toCalendarObject.link
        label = this@toCalendarObject.label
        type = this@toCalendarObject.type
    }

    private fun Website.toWebsiteObject() = WebsiteObject().apply {
        link = this@toWebsiteObject.link
        label = this@toWebsiteObject.label
        type = this@toWebsiteObject.type
    }

    private fun Organization.toOrganizationObject() = OrganizationObject().apply {
        name = this@toOrganizationObject.name
        label = this@toOrganizationObject.label
        jobTitle = this@toOrganizationObject.jobTitle
        jobDescription = this@toOrganizationObject.jobDescription
        department = this@toOrganizationObject.department
    }

    private fun EmailAddress.toEmailAddressObject(): EmailAddressObject =
        EmailAddressObject().apply {
            emailAddress = this@toEmailAddressObject.link
            type = this@toEmailAddressObject.type
            label = this@toEmailAddressObject.label
        }

    private fun PhoneNumber.toPhoneNumberObject(): PhoneNumberObject = PhoneNumberObject().apply {
        number = this@toPhoneNumberObject.number
        label = this@toPhoneNumberObject.label
        type = this@toPhoneNumberObject.type
    }

    private fun PostalAddress.toPostalAddressObject() = PostalAddressObject().apply {
        street = this@toPostalAddressObject.street
        city = this@toPostalAddressObject.city
        region = this@toPostalAddressObject.region
        neighborhood = this@toPostalAddressObject.neighborhood
        postCode = this@toPostalAddressObject.postCode
        country = this@toPostalAddressObject.country
        label = this@toPostalAddressObject.label
        type = this@toPostalAddressObject.type
    }

    private fun Person.toPersonObject(): PersonObject {
        return PersonObject().apply {
            fullName = this@toPersonObject.fullName
            lastName = this@toPersonObject.lastName
            firstName = this@toPersonObject.firstName
            gender = this@toPersonObject.gender.name
            imagePath = this@toPersonObject.imagePath
            birthday = this@toPersonObject.birthday
            occupation = this@toPersonObject.occupation
            phoneNumber = this@toPersonObject.phoneNumber.toPhoneNumberObject()
            postalAddress = this@toPersonObject.postalAddress.toPostalAddressObject()
            emailAddress = this@toPersonObject.emailAddress.toEmailAddressObject()
            organization = this@toPersonObject.organization.toOrganizationObject()
            website = this@toPersonObject.website.toWebsiteObject()
            calendar = this@toPersonObject.calendar.toCalendarObject()
        }
    }
}