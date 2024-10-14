package com.example.vinilosapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.vinilosapp.ui.components.ScreenSkeleton

@Composable
fun ArtistasDetalleScreen() {
    ScreenSkeleton(screenName = "Artistas Detalle")
}

@Preview(showBackground = true)
@Composable
fun PreviewArtistasDetalleScreen() {
    ArtistasDetalleScreen()
}
