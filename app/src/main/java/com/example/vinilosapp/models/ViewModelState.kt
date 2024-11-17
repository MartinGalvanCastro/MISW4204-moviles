package com.example.vinilosapp.models

data class ViewModelState<SimpleDTO, DetailDTO>(
    val items: List<SimpleDTO> = emptyList(),
    val filteredItems: List<SimpleDTO> = emptyList(),
    val detail: DetailDTO? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
