package com.example.vinilosapp.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.example.vinilosapp.utils.TipoUsuario
import java.math.BigDecimal

data class AppState(
    val navController: NavController,
    var tipoUsuario: MutableState<TipoUsuario?> = mutableStateOf(null),
    var idAlbum: MutableState<BigDecimal?> = mutableStateOf(null),
)
