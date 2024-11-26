package com.example.vinilosapp.models

import java.math.BigDecimal

data class AlbumSimple(
    val id: BigDecimal? = null,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
)
