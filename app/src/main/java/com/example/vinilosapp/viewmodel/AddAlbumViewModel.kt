package com.example.vinilosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilosapp.models.AddAlbumFormAttribute
import com.example.vinilosapp.models.AddAlbumFormState
import com.example.vinilosapp.models.FormsDefaults
import com.example.vinilosapp.repository.AlbumRepository
import com.example.vinilosapp.repository.BandRepository
import com.example.vinilosapp.repository.MusicianRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddAlbumViewModel @Inject constructor(
    private val albumRepository: AlbumRepository,
    private val musicianRepository: MusicianRepository,
    private val bandRepository: BandRepository,
) : ViewModel() {

    private var ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var mainDispatcher: CoroutineDispatcher = Dispatchers.Main

    constructor(
        albumRepository: AlbumRepository,
        musicianRepository: MusicianRepository,
        bandRepository: BandRepository,
        ioDispatcher: CoroutineDispatcher,
        mainDispatcher: CoroutineDispatcher,
    ) : this(albumRepository, musicianRepository, bandRepository) {
        this.ioDispatcher = ioDispatcher
        this.mainDispatcher = mainDispatcher
    }

    private val _formState = MutableStateFlow(AddAlbumFormState(FormsDefaults.DEFAULT_ALBUM))
    val formState: StateFlow<AddAlbumFormState> = _formState

    init {
        fetchArtistAndBandLists()
    }

    fun handleChange(attribute: AddAlbumFormAttribute) {
        _formState.update { currentState ->
            val updatedState = when (attribute) {
                is AddAlbumFormAttribute.Name ->
                    currentState.copy(item = currentState.item.copy(name = attribute.value))
                is AddAlbumFormAttribute.Cover ->
                    currentState.copy(item = currentState.item.copy(cover = attribute.value))
                is AddAlbumFormAttribute.ReleaseDate ->
                    currentState.copy(item = currentState.item.copy(releaseDate = attribute.value))
                is AddAlbumFormAttribute.Description ->
                    currentState.copy(item = currentState.item.copy(description = attribute.value))
                is AddAlbumFormAttribute.Genre ->
                    currentState.copy(item = currentState.item.copy(genre = attribute.value))
                is AddAlbumFormAttribute.RecordLabel ->
                    currentState.copy(item = currentState.item.copy(recordLabel = attribute.value))
                is AddAlbumFormAttribute.SelectedArtist ->
                    currentState.copy(selectedArtist = attribute.value)
                is AddAlbumFormAttribute.SuccessMessage ->
                    currentState.copy(successMessage = attribute.value)
                is AddAlbumFormAttribute.ErrorMessage ->
                    currentState.copy(errorMessage = attribute.value)
            }

            val isFormValid = with(updatedState) {
                item.name.isNotBlank() &&
                    item.cover.isNotBlank() &&
                    item.releaseDate.isNotBlank() &&
                    item.description.isNotBlank() &&
                    item.genre.isNotBlank() &&
                    item.recordLabel.isNotBlank() &&
                    selectedArtist != null
            }

            updatedState.copy(isValid = isFormValid)
        }
    }

    fun cleanForm() {
        _formState.update {
            it.copy(
                item = FormsDefaults.DEFAULT_ALBUM,
                successMessage = null,
                errorMessage = null,
                selectedArtist = null,
                isValid = false,
            )
        }
    }

    private fun handleErrorFetchingData() {
        _formState.update {
            it.copy(
                isLoadingData = false,
                errorMessage = "Operación no disponible en el momento, intente más tarde",
            )
        }
    }

    private fun fetchArtistAndBandLists() {
        viewModelScope.launch(ioDispatcher) {
            _formState.update { it.copy(isLoadingData = true) }

            try {
                val (artists, bands) = coroutineScope {
                    val artistsDeferred = async { musicianRepository.fetchAll() }
                    val bandsDeferred = async { bandRepository.fetchAll() }

                    Pair(
                        artistsDeferred.await().getOrElse { emptyList() },
                        bandsDeferred.await().getOrElse { emptyList() },
                    )
                }

                val artistAndBandPairs = (
                    artists.map { Triple("${it.id}", it.name, false) } +
                        bands.map { Triple("${it.id}", it.name, true) }
                    ).sortedBy { it.second }

                if (artistAndBandPairs.isEmpty()) {
                    handleErrorFetchingData()
                } else {
                    _formState.update {
                        it.copy(
                            performerList = artistAndBandPairs,
                            isLoadingData = false,
                            errorMessage = null,
                        )
                    }
                }
            } catch (e: Exception) {
                handleErrorFetchingData()
            } finally {
                _formState.update { it.copy(isLoadingData = false) }
            }
        }
    }

    fun createAlbum() {
        viewModelScope.launch(ioDispatcher) {
            _formState.update { it.copy(isLoadingSubmit = true) }

            try {
                val album = _formState.value.item

                val selectedArtist = _formState.value.selectedArtist ?: run {
                    _formState.update { it.copy(errorMessage = "No se seleccionó un artista") }
                    return@launch
                }

                albumRepository.createAlbum(album).onSuccess { newAlbum ->
                    albumRepository.linkAlbumTo("${newAlbum.id}", selectedArtist.first, selectedArtist.third)
                }.onFailure { err ->
                    throw Exception(err.message)
                }

                withContext(mainDispatcher) {
                    _formState.update {
                        it.copy(
                            successMessage = "Álbum creado exitosamente",
                            item = FormsDefaults.DEFAULT_ALBUM,
                            selectedArtist = null,
                            isValid = false,
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(mainDispatcher) {
                    _formState.update {
                        it.copy(
                            errorMessage = "Error al crear el álbum: ${e.message}",
                        )
                    }
                }
            } finally {
                _formState.update { it.copy(isLoadingSubmit = false) }
            }
        }
    }
}
