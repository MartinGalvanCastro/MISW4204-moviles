package com.example.vinilosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.MusicianDetailDTO
import com.example.models.MusicianSimpleDTO
import com.example.vinilosapp.repository.MusicianRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicianViewModel @Inject constructor(
    private val musicianRepository: MusicianRepository,
) : ViewModel() {

    private val _musicians = MutableStateFlow<List<MusicianSimpleDTO>>(emptyList())

    private val _filteredMusicians = MutableStateFlow<List<MusicianSimpleDTO>>(emptyList())
    val filteredMusicians: StateFlow<List<MusicianSimpleDTO>> = _filteredMusicians

    private val _musician = MutableStateFlow<MusicianDetailDTO?>(null)
    val musician: StateFlow<MusicianDetailDTO?> = _musician

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchMusicians() {
        viewModelScope.launch {
            _loading.value = true
            val result = musicianRepository.fetchMusicians()
            result.onSuccess { musicianList ->
                _musicians.value = musicianList
                _filteredMusicians.value = musicianList
            }.onFailure {
                _errorMessage.value = "Error fetching musicians"
            }
            _loading.value = false
        }
    }

    fun fetchMusicianById(id: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = musicianRepository.fetchMusicianById(id)
            result.onSuccess { musicianDetail ->
                _musician.value = musicianDetail
            }.onFailure {
                _errorMessage.value = "Error fetching musician details"
            }
            _loading.value = false
        }
    }

    fun filterMusicians(query: String) {
        _filteredMusicians.value = if (query.isBlank()) {
            _musicians.value
        } else {
            _musicians.value.filter { musician ->
                musician.name.contains(query, ignoreCase = true)
            }
        }
    }
}
