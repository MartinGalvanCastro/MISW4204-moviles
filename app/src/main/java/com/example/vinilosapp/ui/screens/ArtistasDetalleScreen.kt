package com.example.vinilosapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vinilosapp.ui.components.DetailedTopBar
import com.example.vinilosapp.ui.components.ScreenSkeleton

@Composable
fun ArtistasDetalleScreen(artistaId: String?) {
    Scaffold(
        topBar = {
            DetailedTopBar("Album $artistaId")
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (artistaId != null) {
                ScreenSkeleton(screenName = "Artistas Detalle")
            } else {
                Text("Loading...")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewArtistasDetalleScreen() {
    ArtistasDetalleScreen(null)
}
