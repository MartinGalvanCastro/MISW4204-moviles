package com.example.vinilosapp.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.repository.BandRepository
import com.example.vinilosapp.repository.PrizeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BandViewModel @Inject constructor(
    bandRepository: BandRepository,
    val prizeRepository: PrizeRepository,
) : BaseViewModel<BandSimpleDTO, BandDetailDTO>(bandRepository) {

    private val _prizes = MutableStateFlow<List<PrizeDetailDTO>>(emptyList())
    val prizes: StateFlow<List<PrizeDetailDTO>> = _prizes

    fun filterBands(query: String) {
        filterItems(query) { it.name }
    }

    fun fetchPrizes(prizeIds: List<String>) {
        viewModelScope.launch {
            _prizes.value = prizeRepository.fetchPrizes(prizeIds)
        }
    }
}
