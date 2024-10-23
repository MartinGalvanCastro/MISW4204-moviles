package com.example.vinilosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.AlbumDetailDTO
import com.example.models.AlbumSimpleDTO
import com.example.vinilosapp.services.AlbumService
import com.example.vinilosapp.utils.NetworkChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val albumService: AlbumService,
    private val networkChecker: NetworkChecker,
) : ViewModel() {

    private val _albums = MutableStateFlow<List<AlbumSimpleDTO>>(emptyList())

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
            if (networkChecker.isConnected()) {
                _loading.value = true
                try {
                    val albumList = albumService.getAllAlbums()
                    _albums.value = albumList
                    _filteredAlbums.value = albumList
                } catch (e: Exception) {
                    e.printStackTrace()
                    _errorMessage.value = "Error fetching albums"
                } finally {
                    _loading.value = false
                }
            } else {
                _errorMessage.value = "No internet connection."
            }
        }
    }

    fun fetchAlbumById(id: Int) {
        viewModelScope.launch {
            if (networkChecker.isConnected()) {
                _loading.value = true
                try {
                    val album = albumService.getAlbumById(id)
                    _album.value = album
                } catch (e: Exception) {
                    _errorMessage.value = "Error fetching album details"
                } finally {
                    _loading.value = false
                }
            } else {
                _errorMessage.value = "No internet connection."
            }
        }
    }

    fun createAlbum(newAlbum: AlbumSimpleDTO) {
        viewModelScope.launch {
            if (networkChecker.isConnected()) {
                _loading.value = true
                try {
                    val createdAlbum = albumService.createAlbum(newAlbum)
                    _successMessage.value = "Album '${createdAlbum.name}' created successfully!"
                } catch (e: Exception) {
                    _errorMessage.value = "Error creating album"
                } finally {
                    _loading.value = false
                }
            } else {
                _errorMessage.value = "No internet connection."
            }
        }
    }

    fun filterAlbums(query: String) {
        _filteredAlbums.value = if (query.isBlank()) {
            _albums.value // Show all albums if query is blank
        } else {
            _albums.value.filter { album ->
                album.name.contains(query, ignoreCase = true) // Assuming AlbumSimpleDTO has a name property
            }
        }
    }
}
