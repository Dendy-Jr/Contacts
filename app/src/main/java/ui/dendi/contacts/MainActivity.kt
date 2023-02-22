package ui.dendi.contacts

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.contacts.navigation.Route
import ui.dendi.contacts.presentation.screen.contact_details.ContactDetailsScreen
import ui.dendi.contacts.presentation.screen.contacts.ContactsScreen
import ui.dendi.contacts.presentation.screen.create_contact.CreateContactScreen
import ui.dendi.contacts.ui.theme.ContactsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactsTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.CONTACTS,
                    ) {
                        composable(Route.CONTACTS) {
                            ContactsScreen(
                                onNextClick = {
                                    navController.navigate(Route.CREATE_CONTACT)
                                },
                                navHostController = navController,
                            )
                        }
                        composable(Route.CREATE_CONTACT) {
                            CreateContactScreen(
                                snackbarHostState = snackbarHostState,
                                onDoneClick = {
                                    navController.navigate(Route.CONTACTS) {
                                        popUpTo(0)
                                    }
                                },
                                onCancelClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            route = Route.DETAILS + "/{id}",
                            arguments = listOf(navArgument("id") {
                                type = NavType.StringType
                            })
                        ) {
                            ContactDetailsScreen(
                                id = it.arguments?.getString("id") ?: return@composable,
                                onBackClicked = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}