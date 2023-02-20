package ui.dendi.contacts.data

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import ui.dendi.contacts.domain.*
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

    private fun PhoneNumberObject.toPhoneNumber(): PhoneNumber {
        return PhoneNumber(phoneNumber = phoneNumber, label = label, type = type)
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
        return EmailAddress(emailAddress = emailAddress, type = type, label = label)
    }

    private fun OrganizationObject.toOrganization(): Organization {
        return Organization(
            organizationName = organizationName,
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
        return Calendar(calendarLink = calendarLink, label = label, type = type)
    }

    private fun PersonObject.toPerson(): Person {
        return Person(
            id = id.toString(),
            title = title,
            fullName = fullName,
            lastName = lastName,
            firstName = firstName,
            gender = gender,
            imagePath = imagePath,
            birthday = birthday,
            occupation = occupation,
            phoneNumber = phoneNumber?.toPhoneNumber() ?: PhoneNumber(
                phoneNumber = "",
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
                emailAddress = "",
                type = "",
                label = "",
            ),
            organization = organization?.toOrganization() ?: Organization(
                organizationName = "",
                label = "",
                jobTitle = "",
                jobDescription = "",
                department = "",
            ),
            website = website?.toWebsite() ?: Website(link = "", label = "", type = ""),
            calendar = calendar?.toCalendar() ?: Calendar(calendarLink = "", label = "", type = ""),
        )
    }

    private fun Calendar.toCalendarObject() = CalendarObject().apply {
        calendarLink = this@toCalendarObject.calendarLink
        label = this@toCalendarObject.label
        type = this@toCalendarObject.type
    }

    private fun Website.toWebsiteObject() = WebsiteObject().apply {
        link = this@toWebsiteObject.link
        label = this@toWebsiteObject.label
        type = this@toWebsiteObject.type
    }

    private fun Organization.toOrganizationObject() = OrganizationObject().apply {
        organizationName = this@toOrganizationObject.organizationName
        label = this@toOrganizationObject.label
        jobTitle = this@toOrganizationObject.jobTitle
        jobDescription = this@toOrganizationObject.jobDescription
        department = this@toOrganizationObject.department
    }

    private fun EmailAddress.toEmailAddressObject(): EmailAddressObject =
        EmailAddressObject().apply {
            emailAddress = this@toEmailAddressObject.emailAddress
            type = this@toEmailAddressObject.type
            label = this@toEmailAddressObject.label
        }

    private fun PhoneNumber.toPhoneNumberObject(): PhoneNumberObject = PhoneNumberObject().apply {
        phoneNumber = this@toPhoneNumberObject.phoneNumber
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
            title = this@toPersonObject.title
            fullName = this@toPersonObject.fullName
            lastName = this@toPersonObject.lastName
            firstName = this@toPersonObject.firstName
            gender = this@toPersonObject.gender
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