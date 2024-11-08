package com.example.vinilosapp.services.adapters

import com.example.models.CollectorDetailDTO
import com.example.models.CollectorSimpleDTO
import com.example.vinilosapp.services.api.ColeccionistaAPI
import retrofit2.Retrofit
import javax.inject.Inject

class ColecionistaServiceRetrofit @Inject constructor(
    retrofit: Retrofit,
) : ColecionistaServiceAdapter {

    private val colecionistaAPI by lazy { retrofit.create(ColeccionistaAPI::class.java) }

    override suspend fun getColecionistas(): Result<List<CollectorSimpleDTO>> {
        return try {
            val response = colecionistaAPI.getCollectors()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getColecionistaById(id: String): Result<CollectorDetailDTO> {
        return try {
            val response = colecionistaAPI.getCollectors(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
