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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.models.TrackSimpleDTO
import com.example.vinilosapp.utils.TipoUsuario

@Composable
fun CancionesSection(canciones: List<TrackSimpleDTO>, tipoUsuario: TipoUsuario, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxWidth(),
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
                    onClick = { println("Button Pressed") },
                    modifier = Modifier.padding(vertical = 0.dp),
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
            val listaCanciones = canciones.map {
                    cancion ->
                ListItemValueItem(
                    item = cancion.name,
                    value = cancion.duration,
                    modifier = Modifier,
                )
            }
            ListItemValue(listaCanciones, extraSpacing = true)
        }
    }
}
