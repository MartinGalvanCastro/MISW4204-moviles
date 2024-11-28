package com.example.vinilosapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vinilosapp.LocalAppState
import com.example.vinilosapp.models.GridItemProps
import com.example.vinilosapp.navigation.DetailRoutePrefix
import com.example.vinilosapp.navigation.Routes.ADD_ALBUM_SCREEN
import com.example.vinilosapp.ui.components.GridLayout
import com.example.vinilosapp.ui.components.ScreenSkeleton
import com.example.vinilosapp.utils.TipoUsuario
import com.example.vinilosapp.viewmodel.AlbumViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class, ExperimentalFoundationApi::class)
@Composable
fun AlbumesScreen(albumViewModel: AlbumViewModel = hiltViewModel()) {
    val navController = LocalAppState.current.navController
    val userType = LocalAppState.current.tipoUsuario.value
    val state by albumViewModel.state.collectAsState()

    var filterText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        albumViewModel.fetchAllItems()
    }

    LaunchedEffect(filterText) {
        snapshotFlow { filterText }
            .debounce(300)
            .collect { albumViewModel.filterAlbums(it) }
    }

    Scaffold(
        floatingActionButton = {
            if (userType === TipoUsuario.COLECCIONISTA) {
                ExtendedFloatingActionButton(
                    onClick = { navController.navigate(ADD_ALBUM_SCREEN) },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text(text = "Agregar Album") },
                    modifier = Modifier
                        .testTag("addAlbumTag")
                        .semantics { contentDescription = "Agregar álbum" }
                        .combinedClickable(
                            onClick = { navController.navigate(ADD_ALBUM_SCREEN) },
                        ),
                )
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            TextField(
                value = filterText,
                onValueChange = { filterText = it },
                label = { Text("Filtro") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("filterTextField"),
            )

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                when {
                    state.isLoading -> {
                        ScreenSkeleton("Cargando...", modifier = Modifier.testTag("loadingMessage"))
                    }
                    state.errorMessage != null -> {
                        ScreenSkeleton(state.errorMessage!!, modifier = Modifier.testTag("errorMessage"))
                    }
                    state.filteredItems.isEmpty() && !state.isLoading -> {
                        ScreenSkeleton("No se encontraron resultados", modifier = Modifier.testTag("emptyMessage"))
                    }
                    else -> {
                        GridLayout(
                            items = state.filteredItems,
                            itemTestTag = "albumItem",
                            modifier = Modifier.testTag("albumGrid"),
                        ) { album ->
                            GridItemProps(
                                name = album.name,
                                imageUrl = album.cover,
                                onSelect = {
                                    navController.navigate("${DetailRoutePrefix.ALBUM_DETALLE_SCREEN}/${album.id}")
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
