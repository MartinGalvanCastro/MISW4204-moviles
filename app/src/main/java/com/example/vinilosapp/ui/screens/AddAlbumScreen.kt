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
import com.example.vinilosapp.models.AddAlbumFormAttribute
import com.example.vinilosapp.models.Genre
import com.example.vinilosapp.models.RecordLabel
import com.example.vinilosapp.ui.components.DatePicker
import com.example.vinilosapp.ui.components.DetailedTopBar
import com.example.vinilosapp.ui.components.Dropdown
import com.example.vinilosapp.viewmodel.AddAlbumViewModel
import kotlinx.coroutines.launch

object ScreenTestTags {
    const val ALBUM_NAME_TEXT_FIELD = "AlbumNameTextField"
    const val DESCRIPTION_TEXT_FIELD = "DescriptionTextField"
    const val RELEASE_DATE_PICKER = "ReleaseDatePicker"
    const val GENRE_DROPDOWN = "GenreDropdown"
    const val RECORD_LABEL_DROPDOWN = "RecordLabelDropdown"
    const val PERFORMER_DROPDOWN = "PerformerDropdown"
    const val CANCEL_BUTTON = "CancelButton"
    const val SUBMIT_BUTTON = "SubmitButton"
}

@Composable
fun AddAlbumScreen(addAlbumViewModel: AddAlbumViewModel = hiltViewModel()) {
    val formState by addAlbumViewModel.formState.collectAsState()
    val album = formState.item

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var isNameTouched by remember { mutableStateOf(false) }
    var isNameError by remember { mutableStateOf(false) }
    var isDescriptionTouched by remember { mutableStateOf(false) }
    var isDescriptionError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { DetailedTopBar("Agregar Álbum") },
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
            // Album Name
            TextField(
                value = album.name,
                onValueChange = {
                    addAlbumViewModel.handleChange(AddAlbumFormAttribute.Name(it))
                    if (isNameTouched) {
                        isNameError = it.isBlank()
                    }
                },
                label = { Text("Nombre del álbum") },
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
                            isNameError = album.name.isBlank()
                        }
                    }
                    .testTag(ScreenTestTags.ALBUM_NAME_TEXT_FIELD),
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Description
            TextField(
                value = album.description,
                onValueChange = {
                    addAlbumViewModel.handleChange(AddAlbumFormAttribute.Description(it))
                    if (isDescriptionTouched) {
                        isDescriptionError = it.isBlank()
                    }
                },
                label = { Text("Descripción") },
                isError = isDescriptionError,
                supportingText = {
                    if (isDescriptionError) {
                        Text("El campo no puede estar vacío")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            isDescriptionTouched = true
                        } else if (isDescriptionTouched) {
                            isDescriptionError = album.description.isBlank()
                        }
                    }
                    .testTag(ScreenTestTags.DESCRIPTION_TEXT_FIELD),
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Date Picker
            DatePicker(
                label = "Fecha de Lanzamiento",
                initialDate = album.releaseDate,
                onDateSelected = { selectedDate ->
                    addAlbumViewModel.handleChange(AddAlbumFormAttribute.ReleaseDate(selectedDate))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(ScreenTestTags.RELEASE_DATE_PICKER),
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Genre Dropdown
            Dropdown(
                selectedOption = album.genre,
                onOptionSelected = { addAlbumViewModel.handleChange(AddAlbumFormAttribute.Genre(it)) },
                options = Genre.entries.map { it.genre },
                label = "Género",
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(ScreenTestTags.GENRE_DROPDOWN),
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Record Label Dropdown
            Dropdown(
                selectedOption = album.recordLabel,
                onOptionSelected = { addAlbumViewModel.handleChange(AddAlbumFormAttribute.RecordLabel(it)) },
                options = RecordLabel.entries.map { it.label },
                label = "Disquera",
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(ScreenTestTags.RECORD_LABEL_DROPDOWN),
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Performer Dropdown
            Dropdown(
                selectedOption = formState.selectedArtist?.second.orEmpty(),
                onOptionSelected = { selected ->
                    val artist = formState.performerList.find { it.second == selected }
                    artist?.let { addAlbumViewModel.handleChange(AddAlbumFormAttribute.SelectedArtist(it)) }
                },
                options = formState.performerList.map { it.second },
                label = "Artista o Banda",
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(ScreenTestTags.PERFORMER_DROPDOWN),
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Buttons: Submit and Cancel
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Cancel Button
                OutlinedButton(
                    onClick = { addAlbumViewModel.cleanForm() },
                    modifier = Modifier
                        .weight(1f)
                        .testTag(ScreenTestTags.CANCEL_BUTTON),
                ) {
                    Text("Cancelar")
                }

                // Submit Button
                Button(
                    onClick = { addAlbumViewModel.createAlbum() },
                    enabled = !formState.isLoadingSubmit && formState.isValid,
                    modifier = Modifier
                        .weight(1f)
                        .testTag(ScreenTestTags.SUBMIT_BUTTON),
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
                    addAlbumViewModel.handleChange(AddAlbumFormAttribute.SuccessMessage(null)) // Clear success message after showing
                }
            }

            formState.errorMessage?.let {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(it)
                    addAlbumViewModel.handleChange(AddAlbumFormAttribute.ErrorMessage(null)) // Clear error message after showing
                }
            }
        }
    }
}
