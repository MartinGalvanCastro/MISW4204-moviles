package com.example.vinilosapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.models.TrackSimpleDTO
import com.example.vinilosapp.LocalAppState
import com.example.vinilosapp.navigation.DetailRoutePrefix.ALBUM_DETALLE_SCREEN
import com.example.vinilosapp.utils.TipoUsuario

@Composable
fun CancionesSection(albumId: String, canciones: List<TrackSimpleDTO>, modifier: Modifier = Modifier) {
    val tipoUsuario = LocalAppState.current.tipoUsuario.value
    val navController = LocalAppState.current.navController

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (tipoUsuario == TipoUsuario.COLECCIONISTA) Arrangement.SpaceBetween else Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SectionTitle("Canciones")

            if (tipoUsuario == TipoUsuario.COLECCIONISTA) {
                OutlinedButton(
                    onClick = { navController.navigate("$ALBUM_DETALLE_SCREEN/$albumId/addSong") },
                    modifier = Modifier.testTag("addSongButton").padding(vertical = 0.dp),
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add song")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Agregar Cancion")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            if (canciones.isEmpty()) {
                Text(
                    text = "No se han registrado canciones para este álbum",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp),
                )
            } else {
                val listaCanciones = mutableListOf<ListItemValueItem>()
                for (i in canciones.indices) {
                    val cancion = canciones[i]
                    listaCanciones.add(
                        ListItemValueItem(
                            item = cancion.name,
                            value = cancion.duration,
                            testTag = "songs_list_item",
                        ),
                    )
                }
                ListItemValue(listaCanciones, extraSpacing = true)
            }
        }
    }
}
