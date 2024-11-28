package com.example.vinilosapp.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.models.MusicianDetailDTO
import com.example.models.MusicianSimpleDTO
import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.models.ViewModelState
import com.example.vinilosapp.repository.MusicianRepository
import com.example.vinilosapp.repository.PrizeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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

    private val _prizesState: MutableStateFlow<ViewModelState<PrizeDetailDTO, PrizeDetailDTO>> by lazy {
        MutableStateFlow(ViewModelState(isLoading = true))
    }

    val prizesState: StateFlow<ViewModelState<PrizeDetailDTO, PrizeDetailDTO>> = _prizesState

    private fun updatePrizesState(
        isLoading: Boolean? = null,
        items: List<PrizeDetailDTO>? = null,
        errorMessage: String? = null,
    ) {
        _prizesState.update { currentState ->
            currentState.copy(
                isLoading = isLoading ?: currentState.isLoading,
                items = items ?: currentState.items,
                errorMessage = errorMessage ?: currentState.errorMessage,
            )
        }
    }

    fun filterMusicians(query: String) {
        viewModelScope.launch(defaultDispatcher) {
            filterItems(query) { it.name }
        }
    }

    fun fetchPrizesForPerformer(prizeIds: List<String>) {
        viewModelScope.launch(ioDispatcher) {
            updatePrizesState(isLoading = true)
            val prizes = try {
                prizeRepository.fetchPrizes(prizeIds)
            } catch (e: Exception) {
                updatePrizesState(errorMessage = e.message, isLoading = false)
                return@launch
            }
            updatePrizesState(items = prizes, isLoading = false)
        }
    }
}
