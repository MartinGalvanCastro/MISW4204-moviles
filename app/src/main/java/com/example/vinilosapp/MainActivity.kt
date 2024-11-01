package com.example.vinilosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.Mavericks
import com.example.vinilosapp.models.AppState
import com.example.vinilosapp.navigation.AppNavigation
import com.example.vinilosapp.ui.screens.LoginScreen
import com.example.vinilosapp.ui.theme.VinilosAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mavericks.initialize(this)
        enableEdgeToEdge()
        setContent {
            MainContent()
        }
    }
}

val LocalAppState = staticCompositionLocalOf<AppState> {
    error("No AppState provided")
}

@Composable
fun MainContent() {
    VinilosAppTheme {
        val navController = rememberNavController()
        val appState = AppState(navController)

        CompositionLocalProvider(LocalAppState provides appState) {
            if (appState.tipoUsuario.value != null) {
                AppNavigation(navController)
            } else {
                LoginScreen()
            }
        }
    }
}
