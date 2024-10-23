package com.example.vinilosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.example.vinilosapp.navigation.AppNavigation
import com.example.vinilosapp.ui.components.ScreenWrapper
import com.example.vinilosapp.ui.screens.LoginScreen
import com.example.vinilosapp.ui.theme.VinilosAppTheme
import com.example.vinilosapp.utils.TipoUsuario
import com.example.vinilosapp.viewmodel.AppViewModel
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

@Composable
fun MainContent() {
    val appViewModel: AppViewModel = mavericksViewModel()
    val state by appViewModel.collectAsState()
    val currentTipoUsuario: TipoUsuario? = state.tipoUsuario
    VinilosAppTheme {
        val navController = rememberNavController()
        if (currentTipoUsuario != null) {
            ScreenWrapper(navController, { appViewModel.logout() }) {
                AppNavigation(navController)
            }
        } else {
            LoginScreen()
        }
    }
}
