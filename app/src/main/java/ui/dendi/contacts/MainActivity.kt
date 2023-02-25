package ui.dendi.contacts

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.contacts.navigation.Navigator
import ui.dendi.contacts.ui.theme.ContactsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gradientStatusBar(this)

        setContent {
            ContactsTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                ) {
                    Navigator(snackbarHostState)
                }
            }
        }
    }

    private fun gradientStatusBar(activity: Activity) {
        val window: Window = activity.window
        val background = ContextCompat.getDrawable(activity, R.drawable.gradient_horizontal)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        window.statusBarColor = ContextCompat.getColor(activity, android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(activity, android.R.color.transparent)
        window.setBackgroundDrawable(background)
    }
}