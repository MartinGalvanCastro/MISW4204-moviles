package com.example.vinilosapp.services.api

import com.example.vinilosapp.models.AlbumDetail
import com.example.vinilosapp.models.AlbumSimple
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlbumAPI {

    @GET("albums")
    suspend fun getAllAlbums(): List<AlbumSimple>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id: String): AlbumDetail

    @POST("albums")
    suspend fun createAlbum(@Body newAlbum: AlbumSimple): AlbumSimple
}
