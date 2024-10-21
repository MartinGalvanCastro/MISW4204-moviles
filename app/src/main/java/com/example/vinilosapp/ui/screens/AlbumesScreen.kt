package com.example.vinilosapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vinilosapp.ui.components.GridLayout

@Composable
fun AlbumesScreen() {
    val albums = List(80) { "Album $it" } // Example album list, replace with actual data
    Box(
        modifier = Modifier.fillMaxHeight(), // Allow the height to fill the screen
        contentAlignment = Alignment.Center,
    ) {
        GridLayout(items = albums) // Pass the albums list to GridLayout
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAlbumesScreen() {
    AlbumesScreen()
}
