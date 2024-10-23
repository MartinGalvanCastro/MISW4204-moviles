package com.example.vinilosapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.vinilosapp.navigation.Routes

@Composable
fun ScreenWrapper(
    navController: NavController,
    goBackFunction: () -> Unit,
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                onBackClick = {
                    navController.navigate(Routes.ALBUMS_SCREEN)
                    goBackFunction()
                },
            )
        },
        bottomBar = {
            Navbar(navController)
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}
