package com.example.vinilosapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vinilosapp.ui.components.ScreenWrapper
import com.example.vinilosapp.ui.screens.AlbumesScreen
import com.example.vinilosapp.ui.screens.ArtistasScreen
import com.example.vinilosapp.ui.screens.BandasScreen
import com.example.vinilosapp.ui.screens.ColeccionistasScreen
import com.example.vinilosapp.ui.screens.LoginScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN.route,
    ) {
        composable(Routes.LOGIN.route) {
            LoginScreen(navController = navController)
        }

        composable(Routes.ALBUMES.route) {
            ScreenWrapper(navController = navController) {
                AlbumesScreen()
            }
        }
        composable(Routes.ARTISTAS.route) {
            ScreenWrapper(navController = navController) {
                ArtistasScreen()
            }
        }
        composable(Routes.BANDAS.route) {
            ScreenWrapper(navController = navController) {
                BandasScreen()
            }
        }
        composable(Routes.COLECCIONISTAS.route) {
            ScreenWrapper(navController = navController) {
                ColeccionistasScreen()
            }
        }
    }
}
