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
fun GridLayout(
    items: List<GridItemProps>,
    itemTestTag: String,
    modifier: Modifier = Modifier,
) {
    if (items.isEmpty()) {
        ScreenSkeleton("No se encontro la informacion requerida")
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.fillMaxSize(),
        ) {
            items(items) { item ->
                ImageAndText(
                    imageUrl = item.imageUrl,
                    imageText = item.name,
                    onSelect = item.onSelect,
                    modifier = Modifier.testTag(itemTestTag),
                )
            }
        }
    }
}
