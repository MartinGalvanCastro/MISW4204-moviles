package com.example.vinilosapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.models.AlbumDetailDTO
import com.example.models.BandDetailDTO
import com.example.models.CollectorDetailDTO
import com.example.models.MusicianDetailDTO
import com.example.vinilosapp.R
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

sealed class DetailDTO {
    data class AlbumDetail(val album: AlbumDetailDTO) : DetailDTO()
    data class BandDetail(val bandDetail: BandDetailDTO) : DetailDTO()
    data class MusicianDetail(val musicianDetail: MusicianDetailDTO) : DetailDTO()
    data class ColeccionistaDetail(val coleccionistaDetail: CollectorDetailDTO) : DetailDTO()
}

@Composable
fun InfoSection(
    item: DetailDTO,
) {
    var listaInfo: List<ListItemValueItem>? = null
    var descriptionText: String? = null
    val defaultImage = rememberAsyncImagePainter(model = R.drawable.default_album_image_background)
    var imageUrl: String? = null

    when (item) {
        is DetailDTO.AlbumDetail -> {
            descriptionText = item.album.description
            imageUrl = item.album.cover
            val instant = Instant.parse(item.album.releaseDate)
            val localDate = instant.toLocalDateTime(TimeZone.UTC).date
            val formattedDate = "%02d/%02d/%d".format(localDate.dayOfMonth, localDate.monthNumber, localDate.year)

            listaInfo = listOf(
                ListItemValueItem("Fecha de Publicacion: ", formattedDate),
                ListItemValueItem("Disquera: ", "SONY"), // Example data
                ListItemValueItem("Genero: ", "Rock"), // Example data
            )
        }
        is DetailDTO.BandDetail -> {
            descriptionText = item.bandDetail.description
            imageUrl = item.bandDetail.image
            val instant = Instant.parse(item.bandDetail.creationDate)
            val localDate = instant.toLocalDateTime(TimeZone.UTC).date
            val formattedDate = "%02d/%02d/%d".format(localDate.dayOfMonth, localDate.monthNumber, localDate.year)

            listaInfo = listOf(
                ListItemValueItem("Formada en: ", formattedDate),
            )
        }
        is DetailDTO.ColeccionistaDetail -> {
            listaInfo = listOf(
                ListItemValueItem("Correo: ", item.coleccionistaDetail.email),
                ListItemValueItem("Telefono: ", item.coleccionistaDetail.telephone),
            )
        }
        is DetailDTO.MusicianDetail -> {
            imageUrl = item.musicianDetail.image
            descriptionText = item.musicianDetail.description
            val instant = Instant.parse(item.musicianDetail.birthDate)
            val localDate = instant.toLocalDateTime(TimeZone.UTC).date
            val formattedDate = "%02d/%02d/%d".format(localDate.dayOfMonth, localDate.monthNumber, localDate.year)

            listaInfo = listOf(
                ListItemValueItem("Fecha de Nacimiento: ", formattedDate),
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        // Conditionally render the image if imageUrl is not null
        imageUrl?.let {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    model = it,
                    contentDescription = "Detail Cover Image",
                    error = defaultImage,
                    placeholder = defaultImage,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(210.dp)
                        .shadow(12.dp, RoundedCornerShape(10.dp))
                        .background(Color.Transparent)
                        .clip(RoundedCornerShape(10.dp))
                        .testTag("Detail_Cover_Image"),
                )
            }
        }

        descriptionText?.let {
            Text(
                "\"$it\"",
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }

        ListItemValue(listaInfo)
    }
}
