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

    fun filterCollectors(query: String) {
        filterItems(query) { it.name }
    }
}
