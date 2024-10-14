package com.example.vinilosapp.models

import com.airbnb.mvrx.MavericksState
import com.example.vinilosapp.utils.TipoUsuario

data class AppState(
    val tipoUsuario: TipoUsuario? = null,
) : MavericksState
