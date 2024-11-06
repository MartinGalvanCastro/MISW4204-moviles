package com.example.vinilosapp.services.adapters

import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import retrofit2.http.Path

interface BandServiceAdapter {

    suspend fun getBands(): Result<List<BandSimpleDTO>>

    suspend fun getBandById(@Path("id") id: String): Result<BandDetailDTO>
}
