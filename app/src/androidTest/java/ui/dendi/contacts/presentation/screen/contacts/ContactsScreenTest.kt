package ui.dendi.contacts.presentation.screen.contacts

import android.content.Context
import androidx.compose.ui.test.*
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import ui.dendi.contacts.R
import ui.dendi.contacts.base.BaseTest
import ui.dendi.contacts.domain.repository.ContactsRepository
import javax.inject.Inject

@HiltAndroidTest
class ContactsScreenTest() : BaseTest() {

    @Inject
    lateinit var contactsRepository: ContactsRepository

    @Test
    fun check_everything_when_no_contacts(): Unit = runBlocking {
        val context: Context = composeRule.activity.applicationContext
        val contacts = contactsRepository.getContacts().first()
        val listSize = contacts.size
        composeRule.onNodeWithText(context.getString(R.string.contacts)).assertIsDisplayed()
        composeRule.onNodeWithTag("Search").performTextInput("No contacts")
        composeRule.onNodeWithTag("Search").assert(hasText("No contacts", ignoreCase = true))
        composeRule.onNodeWithTag("ContactList").onChildren().assertCountEquals(listSize)
    }
}