package ui.dendi.contacts.domain

import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    fun getContacts(): Flow<List<Person>>
    suspend fun insertContact(person: Person)
    suspend fun deleteContact(id: String)
}