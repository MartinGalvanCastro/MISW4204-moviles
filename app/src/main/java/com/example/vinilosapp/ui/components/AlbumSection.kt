package com.example.vinilosapp.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.models.AlbumSimpleDTO

@Composable
fun AlbumSection(albumes: List<AlbumSimpleDTO>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        SectionTitle("Albumes")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            for (index in albumes.indices) {
                ImageAndText(
                    imageShape = ImageShape.CIRCULO,
                    imageUrl = albumes[index].cover,
                    imageText = albumes[index].name,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
