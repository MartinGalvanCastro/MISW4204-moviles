package com.example.vinilosapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vinilosapp.models.AlbumSimple
import com.example.vinilosapp.models.Genre
import com.example.vinilosapp.models.RecordLabel
import com.example.vinilosapp.ui.components.DetailedTopBar
import com.example.vinilosapp.ui.components.Dropdown
import com.example.vinilosapp.ui.components.TextField
import com.example.vinilosapp.viewmodel.AlbumViewModel
import java.util.logging.Level
import java.util.logging.Logger

@Composable
fun AddAlbumScreen(albumViewModel: AlbumViewModel = hiltViewModel()) {
    // Form fields
    var albumName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var releaseDate by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var recordLabel by remember { mutableStateOf("") }

    // Hardcoded image URL
    val hardcodedCoverUrl = "https://via.placeholder.com/150"

    // Error states
    var albumNameError by remember { mutableStateOf<String?>(null) }
    var descriptionError by remember { mutableStateOf<String?>(null) }
    var releaseDateError by remember { mutableStateOf<String?>(null) }
    var genreError by remember { mutableStateOf<String?>(null) }
    var recordLabelError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = { DetailedTopBar("Agregar Álbum") },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            // Album Name
            TextField(
                value = albumName,
                onValueChange = {
                    albumName = it
                    albumNameError = if (it.isBlank()) "El nombre del álbum es obligatorio" else null
                },
                label = "Nombre del álbum",
                isError = albumNameError != null,
                errorMessage = albumNameError.orEmpty(),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            TextField(
                value = description,
                onValueChange = {
                    description = it
                    descriptionError = if (it.isBlank()) "La descripción es obligatoria" else null
                },
                label = "Descripción",
                isError = descriptionError != null,
                errorMessage = descriptionError.orEmpty(),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Release Date
            TextField(
                value = releaseDate,
                onValueChange = {
                    releaseDate = it
                    releaseDateError = if (it.isBlank()) {
                        "La fecha es obligatoria"
                    } else if (!it.matches(Regex("^\\d{4}-\\d{2}-\\d{2}\$"))) {
                        "La fecha debe estar en formato YYYY-MM-DD"
                    } else {
                        null
                    }
                },
                label = "Fecha de Lanzamiento",
                placeholder = "YYYY-MM-DD",
                isError = releaseDateError != null,
                errorMessage = releaseDateError.orEmpty(),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Genre Dropdown
            Dropdown(
                selectedOption = genre,
                onOptionSelected = {
                    genre = it
                    genreError = if (it.isBlank()) "El género es obligatorio" else null
                },
                options = Genre.entries.map { it.genre },
                label = "Género",
                isError = genreError != null,
                errorMessage = genreError.orEmpty(),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Record Label Dropdown
            Dropdown(
                selectedOption = recordLabel,
                onOptionSelected = {
                    recordLabel = it
                    recordLabelError = if (it.isBlank()) "La disquera es obligatoria" else null
                },
                options = RecordLabel.entries.map { it.label },
                label = "Disquera",
                isError = recordLabelError != null,
                errorMessage = recordLabelError.orEmpty(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                // Cancel Button
                OutlinedButton(
                    onClick = {
                        albumName = ""
                        description = ""
                        releaseDate = ""
                        genre = ""
                        recordLabel = ""
                        albumNameError = null
                        descriptionError = null
                        releaseDateError = null
                        genreError = null
                        recordLabelError = null
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Cancelar")
                }

                // Save Button
                Button(
                    onClick = {
                        Logger.getLogger("Add Album Screen").log(Level.INFO, "Click on on save")
                        val newAlbum = AlbumSimple(
                            name = albumName,
                            cover = hardcodedCoverUrl,
                            releaseDate = releaseDate,
                            description = description,
                            genre = genre,
                            recordLabel = recordLabel,
                        )
                        albumViewModel.createAlbum(newAlbum)
                    },
                    enabled = albumName.isNotBlank() &&
                        description.isNotBlank() &&
                        releaseDate.isNotBlank() &&
                        genre.isNotBlank() &&
                        recordLabel.isNotBlank() &&
                        releaseDate.matches(Regex("^\\d{4}-\\d{2}-\\d{2}\$")),
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}
