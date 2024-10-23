package com.example.vinilosapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vinilosapp.LocalAppState
import com.example.vinilosapp.navigation.Routes

@Composable
fun ScreenWrapper(
    content: @Composable () -> Unit,
) {
    val appState = LocalAppState.current
    Scaffold(
        topBar = {
            TopBar(
                onBackClick = {
                    appState.navController.navigate(Routes.ALBUMS_SCREEN)
                    appState.tipoUsuario.value = null
                },
            )
        },
        bottomBar = {
            Navbar()
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}
