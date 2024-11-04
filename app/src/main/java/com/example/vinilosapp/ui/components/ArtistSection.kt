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
import com.example.models.PerformerSimpleDTO
import com.example.vinilosapp.LocalAppState

@Composable
fun ArtistSection(artistas: List<PerformerSimpleDTO>, modifier: Modifier = Modifier) {
    val navController = LocalAppState.current.navController

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        SectionTitle("Artistas")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            artistas.forEach { artista ->

                ImageAndText(
                    imageShape = ImageShape.CIRCULO,
                    imageUrl = artista.image,
                    imageText = artista.name,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
