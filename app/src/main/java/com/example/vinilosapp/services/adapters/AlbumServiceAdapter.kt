package com.example.vinilosapp.services.adapters

import com.example.vinilosapp.models.AlbumDetail
import com.example.vinilosapp.models.AlbumSimple

interface AlbumServiceAdapter {
    suspend fun getAllAlbums(): Result<List<AlbumSimple>>
    suspend fun getAlbumById(id: String): Result<AlbumDetail>
    suspend fun createAlbum(newAlbum: AlbumSimple): Result<AlbumSimple>
}
