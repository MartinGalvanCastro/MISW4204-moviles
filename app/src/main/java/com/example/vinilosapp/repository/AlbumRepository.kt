package com.example.vinilosapp.repository

import com.example.models.AlbumDetailDTO
import com.example.models.AlbumSimpleDTO
import com.example.vinilosapp.services.adapters.AlbumServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val albumServiceAdapter: AlbumServiceAdapter,
    private val networkChecker: NetworkChecker,
) {

    suspend fun fetchAlbums(): Result<List<AlbumSimpleDTO>> {
        return if (networkChecker.isConnected()) {
            albumServiceAdapter.getAllAlbums()
        } else {
            // TODO: Agregar casos de Cache y Local Storage
            Result.failure(Exception("No internet connection"))
        }
    }

    suspend fun fetchAlbumById(id: String): Result<AlbumDetailDTO> {
        return if (networkChecker.isConnected()) {
            albumServiceAdapter.getAlbumById(id)
        } else {
            // TODO: Agregar casos de Cache y Local Storage
            Result.failure(Exception("No internet connection"))
        }
    }

    suspend fun createAlbum(newAlbum: AlbumSimpleDTO): Result<AlbumSimpleDTO> {
        return if (networkChecker.isConnected()) {
            albumServiceAdapter.createAlbum(newAlbum)
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }
}
