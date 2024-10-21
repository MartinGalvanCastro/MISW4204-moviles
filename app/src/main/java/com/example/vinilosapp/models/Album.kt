package com.example.vinilosapp.models

import java.util.Date

data class Album(
    val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: Date,
    val description: String,
    val genre: Genre,
    val recordLabel: RecordLabel,
)
