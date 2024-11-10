package com.example.vinilosapp.viewmodel

import com.example.models.CollectorDetailDTO
import com.example.models.CollectorSimpleDTO
import com.example.vinilosapp.repository.ColeccionistaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class ColeccionistaViewModel @Inject constructor(
    colecionistaRepository: ColeccionistaRepository,

) : BaseViewModel<CollectorSimpleDTO, CollectorDetailDTO>(colecionistaRepository) {

    constructor(
        colecionistaRepository: ColeccionistaRepository,
        ioDispatcher: CoroutineDispatcher,
        defaultDispatcher: CoroutineDispatcher,
    ) : this(colecionistaRepository) {
        this.ioDispatcher = ioDispatcher
        this.defaultDispatcher = defaultDispatcher
    }

    fun filterCollectors(query: String) {
        filterItems(query) { it.name }
    }
}
