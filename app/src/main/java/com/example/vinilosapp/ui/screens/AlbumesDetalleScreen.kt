package com.example.vinilosapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.vinilosapp.ui.components.ScreenSkeleton

@Composable
fun AlbumDetalleScreen() {
    ScreenSkeleton(screenName = "Álbum Detalle")
}

@Preview(showBackground = true)
@Composable
fun PreviewAlbumDetalleScreen() {
    AlbumDetalleScreen()
}
