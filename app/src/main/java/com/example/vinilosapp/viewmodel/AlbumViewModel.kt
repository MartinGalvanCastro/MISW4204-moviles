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
import java.util.logging.Level
import java.util.logging.Logger
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

    fun createAlbum(newAlbum: AlbumSimple) {
        viewModelScope.launch(ioDispatcher) {
            _state.value = _state.value.copy(isLoading = true)

            Logger.getLogger("Add Album Screen").log(Level.INFO, "Repo Call")
            val result = albumRepository.createAlbum(newAlbum)
            result.onSuccess { createdAlbum ->
                Logger.getLogger("Add Album Screen").log(Level.INFO, "Success")
                _successMessage.value = "Album '${createdAlbum.name}' created successfully!"
            }.onFailure {
                Logger.getLogger("Add Album Screen").log(Level.INFO, "Fail")
                Logger.getLogger("Add Album Screen").log(Level.INFO, it.message)
                _state.value = _state.value.copy(
                    errorMessage = "Error creating album: ${it.message}",
                    isLoading = false,
                )
            }

            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun filterAlbums(query: String) {
        filterItems(query) { it.name }
    }
}
