package com.example.vinilosapp.services.adapters

import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import com.example.vinilosapp.services.api.BandAPI
import retrofit2.Retrofit
import javax.inject.Inject

class BandServiceRetrofit @Inject constructor(
    retrofit: Retrofit,
) : BandServiceAdapter {

    private val bandAPI by lazy { retrofit.create(BandAPI::class.java) }

    override suspend fun getBands(): Result<List<BandSimpleDTO>> {
        return try {
            val response = bandAPI.getBands()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBandById(id: String): Result<BandDetailDTO> {
        return try {
            val response = bandAPI.getBandById(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
