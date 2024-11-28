package com.example.vinilosapp.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.models.ViewModelState
import com.example.vinilosapp.repository.BandRepository
import com.example.vinilosapp.repository.PrizeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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

    fun filterBands(query: String) {
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
