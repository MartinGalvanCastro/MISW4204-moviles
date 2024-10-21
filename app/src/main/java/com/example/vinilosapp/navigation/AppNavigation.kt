package com.example.vinilosapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vinilosapp.ui.screens.AlbumDetalleScreen
import com.example.vinilosapp.ui.screens.AlbumesScreen
import com.example.vinilosapp.ui.screens.ArtistasScreen
import com.example.vinilosapp.ui.screens.BandasScreen
import com.example.vinilosapp.ui.screens.ColeccionistasScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.ALBUMS_SCREEN,
    ) {
        composable(Routes.ALBUMS_SCREEN) {
            AlbumesScreen()
        }
        composable(Routes.ARTISTAS_SCREEN) {
            ArtistasScreen()
        }
        composable(Routes.BANDAS_SCREEN) {
            BandasScreen()
        }
        composable(Routes.COLECCIONISTAS_SCREEN) {
            ColeccionistasScreen()
        }
        composable(Routes.ALBUM_DETALLE) {
            AlbumDetalleScreen()
        }
    }
}
