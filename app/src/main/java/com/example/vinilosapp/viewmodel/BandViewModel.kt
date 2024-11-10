package com.example.vinilosapp.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.repository.BandRepository
import com.example.vinilosapp.repository.PrizeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BandViewModel @Inject constructor(
    bandRepository: BandRepository,
    private val prizeRepository: PrizeRepository,
) : BaseViewModel<BandSimpleDTO, BandDetailDTO>(bandRepository) {

    constructor(
        bandRepository: BandRepository,
        prizeRepository: PrizeRepository,
        ioDispatcher: CoroutineDispatcher,
        defaultDispatcher: CoroutineDispatcher,
    ) : this(bandRepository, prizeRepository) {
        this.ioDispatcher = ioDispatcher
        this.defaultDispatcher = defaultDispatcher
    }

    private val _prizes = MutableStateFlow<List<PrizeDetailDTO>>(emptyList())
    val prizes: StateFlow<List<PrizeDetailDTO>> = _prizes

    fun filterBands(query: String) {
        filterItems(query) { it.name }
    }

    fun fetchPrizes(prizeIds: List<String>) {
        viewModelScope.launch(ioDispatcher) {
            _prizes.value = prizeRepository.fetchPrizes(prizeIds)
        }
    }
}
