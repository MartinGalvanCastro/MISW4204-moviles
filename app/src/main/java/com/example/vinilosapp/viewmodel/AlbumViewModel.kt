package com.example.vinilosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.AlbumDetailDTO
import com.example.models.AlbumSimpleDTO
import com.example.vinilosapp.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val albumRepository: AlbumRepository,
) : ViewModel() {

    private val _albums = MutableStateFlow<List<AlbumSimpleDTO>>(emptyList())
    val albums: StateFlow<List<AlbumSimpleDTO>> = _albums

    private val _album = MutableStateFlow<AlbumDetailDTO?>(null)
    val album: StateFlow<AlbumDetailDTO?> = _album

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _filteredAlbums = MutableStateFlow<List<AlbumSimpleDTO>>(emptyList())
    val filteredAlbums: StateFlow<List<AlbumSimpleDTO>> = _filteredAlbums

    fun fetchAlbums() {
        viewModelScope.launch {
            _loading.value = true
            val result = albumRepository.fetchAlbums()
            result.onSuccess { albumList ->
                _albums.value = albumList
                _filteredAlbums.value = albumList
            }.onFailure {
                _errorMessage.value = "Error fetching albums"
            }
            _loading.value = false
        }
    }

    fun fetchAlbumById(id: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = albumRepository.fetchAlbumById(id)
            result.onSuccess { albumDetail ->
                _album.value = albumDetail
            }.onFailure {
                _errorMessage.value = "Error fetching album details"
            }
            _loading.value = false
        }
    }

    fun createAlbum(newAlbum: AlbumSimpleDTO) {
        viewModelScope.launch {
            _loading.value = true
            val result = albumRepository.createAlbum(newAlbum)
            result.onSuccess { createdAlbum ->
                _successMessage.value = "Album '${createdAlbum.name}' created successfully!"
            }.onFailure {
                _errorMessage.value = "Error creating album"
            }
            _loading.value = false
        }
    }

    fun filterAlbums(query: String) {
        _filteredAlbums.value = if (query.isBlank()) {
            _albums.value
        } else {
            _albums.value.filter { album ->
                album.name.contains(query, ignoreCase = true)
            }
        }
    }
}
