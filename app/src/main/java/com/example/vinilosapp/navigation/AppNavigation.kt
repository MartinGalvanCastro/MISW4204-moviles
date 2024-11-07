package com.example.vinilosapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.vinilosapp.ui.components.ScreenWrapper
import com.example.vinilosapp.ui.screens.AlbumDetalleScreen
import com.example.vinilosapp.ui.screens.AlbumesScreen
import com.example.vinilosapp.ui.screens.ArtistasDetalleScreen
import com.example.vinilosapp.ui.screens.ArtistasScreen
import com.example.vinilosapp.ui.screens.BandasDetalleScreen
import com.example.vinilosapp.ui.screens.BandasScreen
import com.example.vinilosapp.ui.screens.ColeccionistasScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.ALBUMS_SCREEN,
    ) {
        composable(Routes.ALBUMS_SCREEN) {
            ScreenWrapper {
                AlbumesScreen()
            }
        }
        composable(Routes.ARTISTAS_SCREEN) {
            ScreenWrapper {
                ArtistasScreen()
            }
        }
        composable(Routes.BANDAS_SCREEN) {
            ScreenWrapper {
                BandasScreen()
            }
        }
        composable(Routes.COLECCIONISTAS_SCREEN) {
            ScreenWrapper {
                ColeccionistasScreen()
            }
        }
        composable(
            route = Routes.ALBUM_DETALLE_SCREEN,
            arguments = listOf(navArgument("albumId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId")
            AlbumDetalleScreen(albumId)
        }
        composable(
            route = Routes.ARTISTA_DETALLE_SCREEN,
            arguments = listOf(navArgument("artistaId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val artistaId = backStackEntry.arguments?.getString("artistaId")
            ArtistasDetalleScreen(artistaId)
        }
        composable(
            route = Routes.BAND_DETALLE_SCREEN,
            arguments = listOf(navArgument("bandaId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val bandaId = backStackEntry.arguments?.getString("bandaId")
            BandasDetalleScreen(bandaId)
        }
    }
}
