package ui.dendi.contacts

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.contacts.navigation.Navigator
import ui.dendi.contacts.ui.theme.ContactsTheme
import ui.dendi.contacts.ui.theme.Mandy
import ui.dendi.contacts.ui.theme.MulledWine

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            ContactsTheme {
                val callPermissionState =
                    rememberPermissionState(permission = Manifest.permission.CALL_PHONE)
                val systemUiController = rememberSystemUiController()
                CheckCallPermission(callPermissionState)

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = MulledWine,
                        darkIcons = false,
                    )
                    systemUiController.setNavigationBarColor(
                        color = Mandy,
                        darkIcons = false,
                    )
                }
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                ) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Navigator(
                            snackbarHostState = snackbarHostState,
                            permissionState = callPermissionState
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun CheckCallPermission(permissionState: PermissionState) {
        if (permissionState.hasPermission) {
            Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show()
        } else {
            LaunchedEffect(key1 = true) {
                permissionState.launchPermissionRequest()
            }
        }
    }
}