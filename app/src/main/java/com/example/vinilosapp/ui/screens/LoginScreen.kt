package com.example.vinilosapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vinilosapp.LocalAppState
import com.example.vinilosapp.ui.components.AppLogo
import com.example.vinilosapp.utils.TipoUsuario

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val appState = LocalAppState.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        AppLogo(modifier = Modifier.size(250.dp).testTag("AppLogo"))

        Text(
            text = "¿Como quieres ingresar?",
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().testTag("LoginPrompt"),
        )

        Button(
            onClick = {
                appState.tipoUsuario.value = TipoUsuario.COLECCIONISTA
            },
            modifier = Modifier.testTag("coleccionistaButton"),
        ) {
            Text("Coleccionista")
        }

        OutlinedButton(
            onClick = {
                appState.tipoUsuario.value = TipoUsuario.INVITADO
            },
            modifier = Modifier.testTag("InvitadoButton"),
        ) {
            Text("Invitado")
        }
    }
}
