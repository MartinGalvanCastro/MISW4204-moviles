package com.example.vinilosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.TrackSimpleDTO
import com.example.vinilosapp.models.AddSongFormAttribute
import com.example.vinilosapp.models.AddSongFormState
import com.example.vinilosapp.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddSongViewModel @Inject constructor(
    private val albumRepository: AlbumRepository,
) : ViewModel() {

    private var ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var mainDispatcher: CoroutineDispatcher = Dispatchers.Main

    constructor(
        albumRepository: AlbumRepository,
        ioDispatcher: CoroutineDispatcher,
        mainDispatcher: CoroutineDispatcher,
    ) : this(albumRepository) {
        this.ioDispatcher = ioDispatcher
        this.mainDispatcher = mainDispatcher
    }

    private val defaultSong = TrackSimpleDTO(name = "", duration = "")

    private val _formState = MutableStateFlow(AddSongFormState(defaultSong))
    val formState: StateFlow<AddSongFormState> = _formState

    // Handles changes to form attributes
    fun handleChange(attribute: AddSongFormAttribute) {
        _formState.update { currentState ->
            val updatedState = when (attribute) {
                is AddSongFormAttribute.Name ->
                    currentState.copy(item = currentState.item.copy(name = attribute.value))
                is AddSongFormAttribute.Duration ->
                    currentState.copy(item = currentState.item.copy(duration = attribute.value))
                is AddSongFormAttribute.SuccessMessage ->
                    currentState.copy(successMessage = attribute.value)
                is AddSongFormAttribute.ErrorMessage ->
                    currentState.copy(errorMessage = attribute.value)
            }

            // Determine if the form is valid
            val isFormValid = with(updatedState.item) {
                name.isNotBlank() && duration.isNotBlank() && validateDurationFormat(duration)
            }

            // Update the state with the new validity status
            updatedState.copy(isValid = isFormValid)
        }
    }

    private fun validateDurationFormat(duration: String): Boolean {
        val durationPattern = Regex("^([0-5]?\\d):([0-5]\\d)$")
        return durationPattern.matches(duration)
    }

    // Resets the form to its default state
    fun cleanForm() {
        _formState.update { currentState ->
            currentState.copy(item = defaultSong)
        }
    }

    // Submits the form to create a new song
    fun createSong(albumId: String) {
        viewModelScope.launch(ioDispatcher) {
            _formState.update { it.copy(isLoadingSubmit = true) }

            try {
                val song = _formState.value.item

                albumRepository.createSong(albumId, song).onSuccess {
                    withContext(mainDispatcher) {
                        _formState.update {
                            it.copy(
                                successMessage = "Canción creada exitosamente",
                                item = defaultSong,
                            )
                        }
                    }
                }.onFailure { err ->
                    throw Exception(err.message)
                }
            } catch (e: Exception) {
                withContext(mainDispatcher) {
                    _formState.update {
                        it.copy(
                            errorMessage = "Error al crear la canción: ${e.message}",
                        )
                    }
                }
            } finally {
                _formState.update { it.copy(isLoadingSubmit = false) }
            }
        }
    }
}
