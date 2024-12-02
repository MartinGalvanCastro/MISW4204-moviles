package com.example.vinilosapp.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.vinilosapp.models.AlbumDetail
import com.example.vinilosapp.models.AlbumSimple
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
) : BaseViewModel<AlbumSimple, AlbumDetail>(
    repository = albumRepository,
) {

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

    private fun updateSuccessMessage(message: String?) {
        _successMessage.value = message
    }

    fun createAlbum(newAlbum: AlbumSimple) {
        viewModelScope.launch(ioDispatcher) {
            updateState(isLoading = true) // Use centralized state update

            val result = albumRepository.createAlbum(newAlbum)
            result.onSuccess { createdAlbum ->
                updateSuccessMessage("Album '${createdAlbum.name}' created successfully!")
                updateState(isLoading = false)
            }.onFailure { error ->
                updateState(
                    errorMessage = "Error creating album: ${error.message}",
                    isLoading = false,
                )
            }
        }
    }

    fun filterAlbums(query: String) {
        viewModelScope.launch(defaultDispatcher) {
            filterItems(query) { it.name }
        }
    }
}
