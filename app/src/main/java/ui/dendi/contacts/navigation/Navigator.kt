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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigator(snackbarHostState: SnackbarHostState) {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = Route.CONTACTS,
    ) {
        composable(
            Route.CONTACTS,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            ContactsScreen(
                onNextClick = {
                    navController.navigate(Route.CREATE_CONTACT)
                },
                navHostController = navController,
            )
        }
        composable(
            Route.CREATE_CONTACT,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
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
            }),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            ContactDetailsScreen(
                id = it.arguments?.getString("id") ?: return@composable,
                onBackClicked = {
                    navController.popBackStack()
                },
                onNextClick = {
                    navController.navigate(Route.CONTACTS) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}