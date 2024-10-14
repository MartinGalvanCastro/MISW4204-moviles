package com.example.vinilosapp.viewmodel

import com.airbnb.mvrx.MavericksViewModel
import com.example.vinilosapp.models.AppState
import com.example.vinilosapp.utils.TipoUsuario

class AppViewModel(initialState: AppState) : MavericksViewModel<AppState>(initialState) {

    fun loginInvitado() {
        setState { copy(tipoUsuario = TipoUsuario.INVITADO) }
    }

    fun logout() {
        setState { copy(tipoUsuario = null) }
    }
}
