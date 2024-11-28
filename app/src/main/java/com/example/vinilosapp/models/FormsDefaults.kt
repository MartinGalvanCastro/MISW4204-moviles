package com.example.vinilosapp.models

import com.example.models.TrackSimpleDTO

object FormsDefaults {
    val DEFAULT_ALBUM = AlbumSimple(
        id = null,
        name = "",
        cover = "https://via.placeholder.com/150",
        releaseDate = "",
        description = "",
        genre = "",
        recordLabel = "",
    )
    val DEFAULT_SONG = TrackSimpleDTO(name = "", duration = "")
}
