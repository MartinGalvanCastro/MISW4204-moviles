package com.example.vinilosapp.viewmodel

import com.example.models.CollectorDetailDTO
import com.example.models.CollectorSimpleDTO
import com.example.vinilosapp.repository.ColeccionistaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ColeccionistaViewModel @Inject constructor(
    colecionistaRepository: ColeccionistaRepository,
) : BaseViewModel<CollectorSimpleDTO, CollectorDetailDTO>(colecionistaRepository) {

    // Filter collectors based on a query string
    fun filterCollectors(query: String) {
        filterItems(query) { it.name }
    }

    // Refresh the list of collectors after changes
    private suspend fun refreshCollectors() {
        val result = repository.fetchAll()
        result.onSuccess { itemList ->
            _state.value = _state.value.copy(
                items = itemList,
                filteredItems = itemList,
                isLoading = false,
            )
        }.onFailure {
            _state.value = _state.value.copy(
                errorMessage = "Error refreshing collectors: ${it.message}",
                isLoading = false,
            )
        }
    }
}
