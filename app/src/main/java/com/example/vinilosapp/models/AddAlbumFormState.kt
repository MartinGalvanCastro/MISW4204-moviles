package com.example.vinilosapp.models

data class AddAlbumFormState(
    val item: AlbumSimple,
    val performerList: List<Triple<String, String, Boolean>> = emptyList(),
    val selectedArtist: Triple<String, String, Boolean>? = null,
    val isLoadingData: Boolean = true,
    val isLoadingSubmit: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isValid: Boolean = false,
)
