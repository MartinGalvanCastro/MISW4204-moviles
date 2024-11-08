package com.example.vinilosapp.services.adapters

import com.example.models.MusicianDetailDTO
import com.example.models.MusicianSimpleDTO
import com.example.vinilosapp.services.api.MusicianAPI
import retrofit2.Retrofit
import javax.inject.Inject

class MusicianServiceRetrofit @Inject constructor(
    retrofit: Retrofit,
) : MusicianServiceAdapter {

    private val musicianAPI by lazy { retrofit.create(MusicianAPI::class.java) }

    override suspend fun getMusicians(): Result<List<MusicianSimpleDTO>> {
        return try {
            val response = musicianAPI.getMusicians()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMusicianById(id: String): Result<MusicianDetailDTO> {
        return try {
            val response = musicianAPI.getMusicianById(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
