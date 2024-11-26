package com.example.vinilosapp.models

import com.example.models.TrackSimpleDTO

data class AddSongFormState(
    val item: TrackSimpleDTO,
    val isValid: Boolean = false,
    val isLoadingSubmit: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,
)
