package com.example.vinilosapp.models

data class GridItemProps(
    val name: String,
    val imageUrl: String,
    val onSelect: (() -> Unit)? = null,
)
