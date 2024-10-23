package com.example.vinilosapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vinilosapp.LocalAppState
import com.example.vinilosapp.navigation.Routes

data class NavbarItem(
    val label: String,
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector,
    val route: String,
    val testTag: String,
)

@Composable
fun Navbar() {
    val navController = LocalAppState.current.navController
    val items = listOf(
        NavbarItem(
            label = "Álbumes",
            outlinedIcon = Icons.Outlined.MusicNote,
            filledIcon = Icons.Filled.MusicNote,
            route = Routes.ALBUMS_SCREEN,
            testTag = "Albumes",
        ),
        NavbarItem(
            label = "Artistas",
            outlinedIcon = Icons.Outlined.Mic,
            filledIcon = Icons.Filled.Mic,
            route = Routes.ARTISTAS_SCREEN,
            testTag = "Artistas",
        ),
        NavbarItem(
            label = "Bandas",
            outlinedIcon = Icons.Outlined.Groups,
            filledIcon = Icons.Filled.Groups,
            route = Routes.BANDAS_SCREEN,
            testTag = "Bandas",
        ),
        NavbarItem(
            label = "Coleccionistas",
            outlinedIcon = Icons.Outlined.Person,
            filledIcon = Icons.Filled.Person,
            route = Routes.COLECCIONISTAS_SCREEN,
            testTag = "Coleccionistas",
        ),
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        modifier = Modifier.testTag("navbar-container"),
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                modifier = Modifier.testTag("navbar-item-${item.testTag}"),
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.filledIcon else item.outlinedIcon,
                        contentDescription = item.label,
                    )
                },
                label = { Text(item.label, modifier = Modifier.testTag(item.testTag)) },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
            )
        }
    }
}
