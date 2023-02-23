package ui.dendi.contacts.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ui.dendi.contacts.presentation.screen.contact_details.ContactDetailsScreen
import ui.dendi.contacts.presentation.screen.contacts.ContactsScreen
import ui.dendi.contacts.presentation.screen.create_contact.CreateContactScreen

@Composable
fun Navigator(snackbarHostState: SnackbarHostState) {
    val navController = rememberNavController()

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