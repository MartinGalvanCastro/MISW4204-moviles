package com.example.vinilosapp.services.api

import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface BandAPI {

    @GET("bands")
    suspend fun getBands(): List<BandSimpleDTO>

    @GET("bands/{id}")
    suspend fun getBandById(@Path("id") id: String): BandDetailDTO
}
