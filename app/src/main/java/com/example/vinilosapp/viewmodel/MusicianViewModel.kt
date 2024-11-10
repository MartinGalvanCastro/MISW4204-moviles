package com.example.vinilosapp.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.models.MusicianDetailDTO
import com.example.models.MusicianSimpleDTO
import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.repository.MusicianRepository
import com.example.vinilosapp.repository.PrizeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicianViewModel @Inject constructor(
    musicianRepository: MusicianRepository,
    private val prizeRepository: PrizeRepository,
) : BaseViewModel<MusicianSimpleDTO, MusicianDetailDTO>(musicianRepository) {

    constructor(
        musicianRepository: MusicianRepository,
        prizeRepository: PrizeRepository,
        ioDispatcher: CoroutineDispatcher,
        defaultDispatcher: CoroutineDispatcher,
    ) : this(musicianRepository, prizeRepository) {
        this.ioDispatcher = ioDispatcher
        this.defaultDispatcher = defaultDispatcher
    }

    private val _prizes = MutableStateFlow<List<PrizeDetailDTO>>(emptyList())
    val prizes: StateFlow<List<PrizeDetailDTO>> = _prizes

    fun filterMusicians(query: String) {
        filterItems(query) { it.name }
    }

    fun fetchPrizes(prizeIds: List<String>) {
        viewModelScope.launch(ioDispatcher) {
            _prizes.value = prizeRepository.fetchPrizes(prizeIds)
        }
    }
}
