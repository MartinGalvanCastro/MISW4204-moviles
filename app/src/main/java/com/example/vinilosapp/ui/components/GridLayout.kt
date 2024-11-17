package com.example.vinilosapp.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.vinilosapp.models.GridItemProps

@Composable
fun <T> GridLayout(
    items: List<T>,
    itemTestTag: String,
    modifier: Modifier = Modifier,
    transform: (T) -> GridItemProps,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
    ) {
        items(items) { item ->
            val gridItem = transform(item)
            ImageAndText(
                imageUrl = gridItem.imageUrl,
                imageText = gridItem.name,
                onSelect = gridItem.onSelect,
                modifier = Modifier.testTag(itemTestTag),
            )
        }
    }
}
