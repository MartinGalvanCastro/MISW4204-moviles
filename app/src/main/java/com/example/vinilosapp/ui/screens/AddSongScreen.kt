package com.example.vinilosapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vinilosapp.models.AddSongFormAttribute
import com.example.vinilosapp.ui.components.DetailedTopBar
import com.example.vinilosapp.viewmodel.AddSongViewModel
import kotlinx.coroutines.launch

object SongScreenTestTags {
    const val SONG_NAME_TEXT_FIELD = "SongNameTextField"
    const val DURATION_TEXT_FIELD = "DurationTextField"
    const val CANCEL_BUTTON = "CancelButton"
    const val SUBMIT_BUTTON = "SubmitButton"
}

@Composable
fun AddSongScreen(
    albumId: String,
    addSongViewModel: AddSongViewModel = hiltViewModel(),
) {
    val formState by addSongViewModel.formState.collectAsState()
    val song = formState.item

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var isNameTouched by remember { mutableStateOf(false) }
    var isNameError by remember { mutableStateOf(false) }
    var isDurationTouched by remember { mutableStateOf(false) }
    var isDurationError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { DetailedTopBar("Agregar Canción") },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.testTag("SnackbarHost"),
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            // Song Name
            TextField(
                value = song.name,
                onValueChange = {
                    addSongViewModel.handleChange(AddSongFormAttribute.Name(it))
                    if (isNameTouched) {
                        isNameError = it.isBlank()
                    }
                },
                label = { Text("Nombre de la canción") },
                isError = isNameError,
                supportingText = {
                    if (isNameError) {
                        Text("El campo no puede estar vacío")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            isNameTouched = true
                        } else if (isNameTouched) {
                            isNameError = song.name.isBlank()
                        }
                    }
                    .testTag(SongScreenTestTags.SONG_NAME_TEXT_FIELD),
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Duration
            TextField(
                value = song.duration,
                onValueChange = {
                    addSongViewModel.handleChange(AddSongFormAttribute.Duration(it))
                    if (isDurationTouched) {
                        isDurationError = it.isBlank()
                    }
                },
                label = { Text("Duración (mm:ss)") },
                isError = isDurationError,
                supportingText = {
                    if (isDurationError) {
                        Text("El campo no puede estar vacío")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            isDurationTouched = true
                        } else if (isDurationTouched) {
                            isDurationError = song.duration.isBlank()
                        }
                    }
                    .testTag(SongScreenTestTags.DURATION_TEXT_FIELD),
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Buttons: Submit and Cancel
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Cancel Button
                OutlinedButton(
                    onClick = { addSongViewModel.cleanForm() },
                    modifier = Modifier
                        .weight(1f)
                        .testTag(SongScreenTestTags.CANCEL_BUTTON),
                ) {
                    Text("Cancelar")
                }

                // Submit Button
                Button(
                    onClick = { addSongViewModel.createSong(albumId) },
                    enabled = !formState.isLoadingSubmit && formState.isValid,
                    modifier = Modifier
                        .weight(1f)
                        .testTag(SongScreenTestTags.SUBMIT_BUTTON),
                ) {
                    if (formState.isLoadingSubmit) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                        )
                    } else {
                        Text("Guardar", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Show Snackbar for success or error messages
        LaunchedEffect(formState.successMessage, formState.errorMessage) {
            formState.successMessage?.let {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(it)
                    addSongViewModel.handleChange(AddSongFormAttribute.SuccessMessage(null)) // Clear success message after showing
                }
            }

            formState.errorMessage?.let {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(it)
                    addSongViewModel.handleChange(AddSongFormAttribute.ErrorMessage(null)) // Clear error message after showing
                }
            }
        }
    }
}
