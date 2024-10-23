package com.example.vinilosapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vinilosapp.navigation.Routes
import com.example.vinilosapp.ui.theme.VinilosAppTheme

data class NavbarItem(
    val label: String,
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector,
    val route: String,
    val testTag: String,
)

@Composable
fun Navbar(
    navController: NavController,
) {
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

@Preview(showBackground = true)
@Composable
fun NavbarPreview() {
    val navController = rememberNavController()
    navController.navigate("albumes")

    VinilosAppTheme {
        Scaffold(
            bottomBar = {
                Navbar(navController)
            },
        ) {
            Box(modifier = Modifier.padding(it)) {
                Text("Navbar Preview")
            }
        }
    }
}
