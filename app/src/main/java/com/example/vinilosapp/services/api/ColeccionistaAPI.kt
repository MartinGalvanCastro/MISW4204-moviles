package com.example.vinilosapp.services.api

import com.example.models.CollectorDetailDTO
import com.example.models.CollectorSimpleDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ColeccionistaAPI {

    @GET("collectors")
    suspend fun getCollectors(): List<CollectorSimpleDTO>

    @GET("collectors/{id}")
    suspend fun getCollectors(@Path("id") id: String): CollectorDetailDTO
}
