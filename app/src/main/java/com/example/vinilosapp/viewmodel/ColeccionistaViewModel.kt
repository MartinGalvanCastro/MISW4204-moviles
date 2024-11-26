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

    private val _albumsState = MutableStateFlow(ViewModelState<AlbumSimpleDTO, AlbumSimpleDTO>(isLoading = true))
    val albumsState: StateFlow<ViewModelState<AlbumSimpleDTO, AlbumSimpleDTO>> = _albumsState

    fun filterCollectors(query: String) {
        filterItems(query) { it.name }
    }

    fun fetchAlbumsOfCollector(albums: List<CollectorAlbumSimpleDTO>) {
        viewModelScope.launch(ioDispatcher) {
            try {
                _albumsState.value = _albumsState.value.copy(isLoading = true)
                val albums = albumRepository.fetchCollectorAlbums(albums)
                _albumsState.value = _albumsState.value.copy(items = albums, isLoading = false)
            } catch (e: Exception) {
                _albumsState.value = _albumsState.value.copy(errorMessage = e.message, isLoading = false)
            }
        }
    }
}
