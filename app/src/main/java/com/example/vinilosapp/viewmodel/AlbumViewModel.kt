package com.example.vinilosapp.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.models.AlbumDetailDTO
import com.example.models.AlbumSimpleDTO
import com.example.vinilosapp.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val albumRepository: AlbumRepository,
) : BaseViewModel<AlbumSimpleDTO, AlbumDetailDTO>(albumRepository) {

    constructor(
        albumRepository: AlbumRepository,
        ioDispatcher: CoroutineDispatcher,
        defaultDispatcher: CoroutineDispatcher,
    ) : this(albumRepository) {
        this.ioDispatcher = ioDispatcher
        this.defaultDispatcher = defaultDispatcher
    }

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    fun createAlbum(newAlbum: AlbumSimpleDTO) {
        viewModelScope.launch {
            setLoading(true)
            val result = albumRepository.createAlbum(newAlbum)
            result.onSuccess { createdAlbum ->
                _successMessage.value = "Album '${createdAlbum.name}' created successfully!"
            }.onFailure {
                setErrorMessage("Error creating album")
            }
            setLoading(false)
        }
    }

    fun filterAlbums(query: String) {
        filterItems(query) { it.name }
    }
}
