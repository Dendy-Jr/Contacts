package ui.dendi.contacts.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import ui.dendi.contacts.presentation.screen.contact_details.ContactDetailsScreen
import ui.dendi.contacts.presentation.screen.contacts.ContactsScreen
import ui.dendi.contacts.presentation.screen.create_contact.CreateContactScreen
import ui.dendi.contacts.presentation.screen.edit_contact.EditContactScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigator(snackbarHostState: SnackbarHostState) {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = Route.CONTACTS,
    ) {
        composable(
            route = Route.CONTACTS,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            ContactsScreen(
                onCreateContactClick = {
                    navController.navigate(Route.CREATE_CONTACT)
                },
                onNavigateToDetails = { id ->
                    navController.navigate(Route.DETAILS + "/$id")
                },
            )
        }
        composable(
            route = Route.CREATE_CONTACT,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            CreateContactScreen(
                snackbarHostState = snackbarHostState,
                onNavigateToDetails = { id ->
                    navController.navigate(Route.DETAILS + "/$id")
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
            }),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            ContactDetailsScreen(
                id = it.arguments?.getString("id") ?: return@composable,
                onNavigateToContacts = {
                    navController.navigate(Route.CONTACTS) {
                        popUpTo(0)
                    }
                },
                onEditClicked = { id ->
                    navController.navigate(Route.EDIT_CONTACT + "/$id")
                }
            )
        }
        composable(
            route = Route.EDIT_CONTACT + "/{id}",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            }),
        ) {
            EditContactScreen(
                snackbarHostState = snackbarHostState,
                onNavigateToDetails = { id ->
                    navController.navigate(Route.DETAILS + "/$id")
                },
                onCancelClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}