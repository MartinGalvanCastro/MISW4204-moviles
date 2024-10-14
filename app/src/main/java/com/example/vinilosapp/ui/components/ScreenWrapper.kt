package com.example.vinilosapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.example.vinilosapp.navigation.Routes
import com.example.vinilosapp.viewmodel.AppViewModel

@Composable
fun ScreenWrapper(content: @Composable () -> Unit) {
    val appViewModel: AppViewModel = mavericksViewModel()
    val state by appViewModel.collectAsState()
    val navController = state.navController

    Scaffold(
        topBar = {
            TopBar(
                onBackClick = {
                    navController?.navigate(Routes.ALBUMS_SCREEN)
                    appViewModel.logout()
                },
            )
        },
        bottomBar = {
            if (navController != null) {
                Navbar()
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}
