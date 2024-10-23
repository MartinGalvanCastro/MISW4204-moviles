package com.example.vinilosapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vinilosapp.ui.components.DetailedTopBar

@Composable
fun AlbumDetalleScreen(albumId: String?) {
    Scaffold(
        topBar = {
            DetailedTopBar("Album $albumId")
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text("Album Detalle $albumId")
        }
    }
}
