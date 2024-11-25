package com.example.vinilosapp.models

import com.example.models.AlbumDetailDTO
import com.example.models.CommentSimpleDTO
import com.example.models.PerformerSimpleDTO
import com.example.models.TrackSimpleDTO
import java.math.BigDecimal

data class AlbumDetail(
    val id: BigDecimal?,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val performers: List<PerformerSimpleDTO>,
    val tracks: List<TrackSimpleDTO>,
    val comments: List<CommentSimpleDTO>,
    val genre: String,
    val recordLabel: String,
) {
    companion object {
        fun fromDto(dto: AlbumDetailDTO, genre: String, recordLabel: String): AlbumDetail {
            return AlbumDetail(
                id = dto.id,
                name = dto.name,
                cover = dto.cover,
                releaseDate = dto.releaseDate,
                description = dto.description,
                performers = dto.performers,
                tracks = dto.tracks,
                comments = dto.comments,
                genre = genre,
                recordLabel = recordLabel,
            )
        }
    }
}
