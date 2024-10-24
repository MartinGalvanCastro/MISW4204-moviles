package com.example.vinilosapp.services

import com.example.models.AlbumDetailDTO
import com.example.models.AlbumSimpleDTO
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlbumService {

    @GET("albums")
    suspend fun getAllAlbums(): List<AlbumSimpleDTO>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id: String): AlbumDetailDTO

    @POST("albums")
    suspend fun createAlbum(newAlbum: AlbumSimpleDTO): AlbumSimpleDTO
}
