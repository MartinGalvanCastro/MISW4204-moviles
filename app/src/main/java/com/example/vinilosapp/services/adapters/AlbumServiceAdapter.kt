package com.example.vinilosapp.services.adapters

import com.example.models.TrackSimpleDTO
import com.example.vinilosapp.models.AlbumDetail
import com.example.vinilosapp.models.AlbumSimple

interface AlbumServiceAdapter {
    suspend fun getAllAlbums(): Result<List<AlbumSimple>>
    suspend fun getAlbumById(id: String): Result<AlbumDetail>
    suspend fun createAlbum(newAlbum: AlbumSimple): Result<AlbumSimple>
    suspend fun linkAlbumTo(albumId: String, performerId: String, isBand: Boolean): Result<String>
    suspend fun linkTrackToAlbum(albumId: String, trackSimpleDTO: TrackSimpleDTO): Result<String>
}
