package com.example.vinilosapp.services.api

import com.example.models.MusicianDetailDTO
import com.example.models.MusicianSimpleDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicianAPI {

    @GET("musicians")
    suspend fun getMusicians(): List<MusicianSimpleDTO>

    @GET("musicians/{id}")
    suspend fun getMusicianById(@Path("id") id: String): MusicianDetailDTO
}
