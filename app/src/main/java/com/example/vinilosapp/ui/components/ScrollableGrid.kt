package com.example.vinilosapp.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridLayout(items: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 items per row
        modifier = Modifier.fillMaxSize(),
    ) {
        items(items) { item ->
            GridItem(item = item) // Custom item composable
        }
    }
}

@Composable
fun GridItem(item: String) {
    Text(text = item) // Replace with your custom layout
}
