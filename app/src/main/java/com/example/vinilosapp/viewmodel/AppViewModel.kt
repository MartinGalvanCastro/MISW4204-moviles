package com.example.vinilosapp.viewmodel

import androidx.navigation.NavController
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

    // Private property to hold the NavController
    private var _navController: NavController? = null

    // Public getter and setter for external access
    var navController: NavController?
        get() = _navController
        set(value) {
            _navController = value
            setState { copy(navController = value) } // Also update the state
        }
}
