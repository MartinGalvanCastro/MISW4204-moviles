package com.example.vinilosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import com.example.vinilosapp.repository.BandRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BandViewModel @Inject constructor(
    private val bandRepository: BandRepository,
) : ViewModel() {

    private val _bands = MutableStateFlow<List<BandSimpleDTO>>(emptyList())

    private val _filteredBands = MutableStateFlow<List<BandSimpleDTO>>(emptyList())
    val filteredBands: StateFlow<List<BandSimpleDTO>> = _filteredBands

    private val _band = MutableStateFlow<BandDetailDTO?>(null)
    val band: StateFlow<BandDetailDTO?> = _band

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchBands() {
        viewModelScope.launch {
            _loading.value = true
            val result = bandRepository.fetchBands()
            result.onSuccess { bandList ->
                _bands.value = bandList
                _filteredBands.value = bandList
            }.onFailure {
                _errorMessage.value = "Error fetching bands"
            }
            _loading.value = false
        }
    }

    fun fetchBandById(id: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = bandRepository.fetchBandById(id)
            result.onSuccess { bandDetail ->
                _band.value = bandDetail
            }.onFailure {
                _errorMessage.value = "Error fetching band details"
            }
            _loading.value = false
        }
    }

    fun filterBands(query: String) {
        _filteredBands.value = if (query.isBlank()) {
            _bands.value
        } else {
            _bands.value.filter { band ->
                band.name.contains(query, ignoreCase = true)
            }
        }
    }
}
