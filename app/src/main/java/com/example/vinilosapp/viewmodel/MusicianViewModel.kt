package com.example.vinilosapp.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.models.MusicianDetailDTO
import com.example.models.MusicianSimpleDTO
import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.models.ViewModelState
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
    private val prizeRepository: PrizeRepository,
) : BaseViewModel<MusicianSimpleDTO, MusicianDetailDTO>(musicianRepository) {

    private val _prizesState = MutableStateFlow(ViewModelState<PrizeDetailDTO, PrizeDetailDTO>(isLoading = true))
    val prizesState: StateFlow<ViewModelState<PrizeDetailDTO, PrizeDetailDTO>> = _prizesState

    fun filterMusicians(query: String) {
        filterItems(query) { it.name }
    }

    fun fetchPrizesForPerformer(prizeIds: List<String>) {
        viewModelScope.launch(ioDispatcher) {
            try {
                _prizesState.value = _prizesState.value.copy(isLoading = true)
                val prizes = prizeRepository.fetchPrizes(prizeIds) // Directly fetches the list of prizes
                _prizesState.value = _prizesState.value.copy(items = prizes, isLoading = false)
            } catch (e: Exception) {
                _prizesState.value = _prizesState.value.copy(errorMessage = e.message, isLoading = false)
            }
        }
    }
}
