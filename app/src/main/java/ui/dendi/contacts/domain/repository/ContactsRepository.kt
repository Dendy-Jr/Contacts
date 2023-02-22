package ui.dendi.contacts.domain.repository

import kotlinx.coroutines.flow.Flow
import ui.dendi.contacts.domain.model.Person

interface ContactsRepository {
    fun getContacts(): Flow<List<Person>>
    suspend fun insertContact(person: Person)

    suspend fun getContact(id: String): Person?
    suspend fun deleteContact(id: String)
}