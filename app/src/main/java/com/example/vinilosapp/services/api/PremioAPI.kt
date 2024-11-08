package com.example.vinilosapp.services.api

import com.example.models.PrizeDetailDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface PremioAPI {

    @GET("prizes/{id}")
    suspend fun getPremioById(@Path("id") id: String): PrizeDetailDTO
}
