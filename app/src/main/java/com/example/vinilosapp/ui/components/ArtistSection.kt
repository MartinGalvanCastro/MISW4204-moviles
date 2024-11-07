package com.example.vinilosapp.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.models.PerformerSimpleDTO

@Composable
fun ArtistSection(
    artistas: List<PerformerSimpleDTO>,
    fromBandas: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag(if (fromBandas) "bandMembersSection" else "artistsSection"),
    ) {
        SectionTitle(
            title = if (fromBandas) "Integrantes" else "Artistas",
            modifier = Modifier.testTag("sectionTitle"),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            artistas.forEachIndexed { index, artista ->
                ImageAndText(
                    imageShape = ImageShape.CIRCULO,
                    imageUrl = artista.image,
                    imageText = artista.name,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.testTag("artistItem-$index"),
                )
            }
        }
    }
}
