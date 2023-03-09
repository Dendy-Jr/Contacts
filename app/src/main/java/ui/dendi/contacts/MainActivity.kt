package ui.dendi.contacts

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.contacts.navigation.Navigator
import ui.dendi.contacts.ui.theme.ContactsTheme
import ui.dendi.contacts.ui.theme.Tundora

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestCallPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::onGotCallPermissionResult
    )

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestCallPermissionLauncher.launch(Manifest.permission.CALL_PHONE)

        //TODO splash screen does not visible on API < 31

        setContent {
            ContactsTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Tundora,
                        darkIcons = false,
                    )
                    systemUiController.setNavigationBarColor(
                        color = Tundora,
                        darkIcons = false,
                    )
                }
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                ) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Navigator(snackbarHostState)
                    }
                }
            }
        }
    }

    private fun onGotCallPermissionResult(granted: Boolean) {
        if (granted.not()) {
            onCallPermissionGranted()
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE).not()) {
                askUserForOpeningAppSettings()
            }
        }
    }

    private fun askUserForOpeningAppSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        if (packageManager.resolveActivity(
                appSettingsIntent,
                PackageManager.MATCH_DEFAULT_ONLY
            ) == null
        ) {
            Toast.makeText(this, getString(R.string.permissions_denied_forever), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun onCallPermissionGranted() {
        Toast.makeText(this, getString(R.string.call_permission_granted), Toast.LENGTH_SHORT).show()
    }
}