package com.example.vinilosapp.models

sealed class AddSongFormAttribute {
    data class Name(val value: String) : AddSongFormAttribute()
    data class Duration(val value: String) : AddSongFormAttribute()
    data class SuccessMessage(val value: String?) : AddSongFormAttribute()
    data class ErrorMessage(val value: String?) : AddSongFormAttribute()
}
