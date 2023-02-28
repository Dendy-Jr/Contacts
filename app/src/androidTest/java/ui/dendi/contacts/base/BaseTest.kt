package ui.dendi.contacts.base

import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import ui.dendi.contacts.MainActivity
import ui.dendi.contacts.navigation.Navigator
import ui.dendi.contacts.ui.theme.ContactsTheme

open class BaseTest {

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltTestRule.inject()
        composeRule.activity.setContent {
            ContactsTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                Navigator(snackbarHostState)
            }
        }
    }
}