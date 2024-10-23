package com.example.vinilosapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vinilosapp.LocalAppState
import com.example.vinilosapp.models.GridItemProps
import com.example.vinilosapp.navigation.DetailRoutePrefix
import com.example.vinilosapp.ui.components.GridLayout
import com.example.vinilosapp.viewmodel.AlbumViewModel

@Composable
fun AlbumesScreen(albumViewModel: AlbumViewModel = hiltViewModel()) {
    val navController = LocalAppState.current.navController

    val albums by albumViewModel.filteredAlbums.collectAsState()
    var filterText by remember { mutableStateOf("") }

    val filteredAlbums = albums.filter {
        it.name.contains(filterText, ignoreCase = true)
    }

    val gridItems = filteredAlbums.map { album ->
        GridItemProps(name = album.name, imageUrl = album.cover, onSelect = {
            navController.navigate("${DetailRoutePrefix.ALBUM_DETALLE_SCREEN}/${album.id}")
        })
    }

    LaunchedEffect(Unit) {
        albumViewModel.fetchAlbums()
    }

    LaunchedEffect(filterText) {
        albumViewModel.filterAlbums(filterText)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        TextField(
            value = filterText,
            onValueChange = { filterText = it },
            label = { Text("Filtro") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            GridLayout(items = gridItems) // Pass the transformed items
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAlbumesScreen() {
    AlbumesScreen()
}
