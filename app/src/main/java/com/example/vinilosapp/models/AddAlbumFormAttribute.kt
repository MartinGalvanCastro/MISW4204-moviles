package com.example.vinilosapp.models

sealed class AddAlbumFormAttribute {
    data class Name(val value: String) : AddAlbumFormAttribute()
    data class Cover(val value: String) : AddAlbumFormAttribute()
    data class ReleaseDate(val value: String) : AddAlbumFormAttribute()
    data class Description(val value: String) : AddAlbumFormAttribute()
    data class Genre(val value: String) : AddAlbumFormAttribute()
    data class RecordLabel(val value: String) : AddAlbumFormAttribute()
    data class SelectedArtist(val value: Triple<String, String, Boolean>) : AddAlbumFormAttribute()
    data class SuccessMessage(val value: String?) : AddAlbumFormAttribute()
    data class ErrorMessage(val value: String?) : AddAlbumFormAttribute()
}
