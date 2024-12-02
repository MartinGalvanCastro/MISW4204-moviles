package com.example.vinilosapp.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.models.AlbumSimpleDTO
import com.example.models.CollectorAlbumSimpleDTO
import com.example.models.CollectorDetailDTO
import com.example.models.CollectorSimpleDTO
import com.example.vinilosapp.models.ViewModelState
import com.example.vinilosapp.repository.AlbumRepository
import com.example.vinilosapp.repository.ColeccionistaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ColeccionistaViewModel @Inject constructor(
    colecionistaRepository: ColeccionistaRepository,
    private val albumRepository: AlbumRepository,
) : BaseViewModel<CollectorSimpleDTO, CollectorDetailDTO>(colecionistaRepository) {

    constructor(
        colecionistaRepository: ColeccionistaRepository,
        albumRepository: AlbumRepository,
        ioDispatcher: CoroutineDispatcher,
        defaultDispatcher: CoroutineDispatcher,
    ) : this(colecionistaRepository, albumRepository) {
        this.ioDispatcher = ioDispatcher
        this.defaultDispatcher = defaultDispatcher
    }

    private val _albumsState: MutableStateFlow<ViewModelState<AlbumSimpleDTO, AlbumSimpleDTO>> by lazy {
        MutableStateFlow(ViewModelState(isLoading = true))
    }
    val albumsState: StateFlow<ViewModelState<AlbumSimpleDTO, AlbumSimpleDTO>> = _albumsState

    private fun updateAlbumsState(
        isLoading: Boolean? = null,
        items: List<AlbumSimpleDTO>? = null,
        errorMessage: String? = null,
    ) {
        _albumsState.update { currentState ->
            currentState.copy(
                isLoading = isLoading ?: currentState.isLoading,
                items = items ?: currentState.items,
                errorMessage = errorMessage ?: currentState.errorMessage,
            )
        }
    }

    fun filterCollectors(query: String) {
        viewModelScope.launch(defaultDispatcher) {
            filterItems(query) { it.name }
        }
    }

    fun fetchAlbumsOfCollector(albums: List<CollectorAlbumSimpleDTO>) {
        viewModelScope.launch(ioDispatcher) {
            updateAlbumsState(isLoading = true)
            val fetchedAlbums = try {
                albumRepository.fetchCollectorAlbums(albums)
            } catch (e: Exception) {
                updateAlbumsState(errorMessage = e.message, isLoading = false)
                return@launch
            }
            updateAlbumsState(items = fetchedAlbums, isLoading = false)
        }
    }
}
