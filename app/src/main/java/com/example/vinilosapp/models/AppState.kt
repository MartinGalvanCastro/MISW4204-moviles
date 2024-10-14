package com.example.vinilosapp.models

import androidx.navigation.NavController
import com.airbnb.mvrx.MavericksState
import com.example.vinilosapp.utils.TipoUsuario

data class AppState(
    val tipoUsuario: TipoUsuario? = null,
    val navController: NavController? = null,
) : MavericksState
