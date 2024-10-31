package com.example.vinilosapp.services.adapters

import com.example.models.AlbumDetailDTO
import com.example.models.AlbumSimpleDTO

interface AlbumServiceAdapter {
    suspend fun getAllAlbums(): Result<List<AlbumSimpleDTO>>
    suspend fun getAlbumById(id: String): Result<AlbumDetailDTO>
    suspend fun createAlbum(newAlbum: AlbumSimpleDTO): Result<AlbumSimpleDTO>
}
