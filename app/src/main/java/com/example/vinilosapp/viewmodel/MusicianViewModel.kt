package com.example.vinilosapp.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.models.MusicianDetailDTO
import com.example.models.MusicianSimpleDTO
import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.repository.MusicianRepository
import com.example.vinilosapp.repository.PrizeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicianViewModel @Inject constructor(
    musicianRepository: MusicianRepository,
    val prizeRepository: PrizeRepository,
) : BaseViewModel<MusicianSimpleDTO, MusicianDetailDTO>(musicianRepository) {

    private val _prizes = MutableStateFlow<List<PrizeDetailDTO>>(emptyList())
    val prizes: StateFlow<List<PrizeDetailDTO>> = _prizes

    fun filterMusicians(query: String) {
        filterItems(query) { it.name }
    }

    fun fetchPrizes(prizeIds: List<String>) {
        viewModelScope.launch {
            _prizes.value = prizeRepository.fetchPrizes(prizeIds)
        }
    }
}
