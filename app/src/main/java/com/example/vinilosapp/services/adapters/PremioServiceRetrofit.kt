package com.example.vinilosapp.services.adapters

import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.services.api.PremioAPI
import retrofit2.Retrofit
import javax.inject.Inject

class PremioServiceRetrofit @Inject constructor(
    private val retrofit: Retrofit,
) : PremioServiceAdapter {

    private val premioAPI by lazy { retrofit.create(PremioAPI::class.java) }

    override suspend fun getPremioById(id: String): Result<PrizeDetailDTO> {
        return try {
            val response = premioAPI.getPremioById(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
