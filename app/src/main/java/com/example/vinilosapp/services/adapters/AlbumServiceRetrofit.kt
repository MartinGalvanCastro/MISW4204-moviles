package com.example.vinilosapp.services.adapters

import com.example.models.AlbumDetailDTO
import com.example.models.AlbumSimpleDTO
import com.example.vinilosapp.services.api.AlbumAPI
import retrofit2.Retrofit
import javax.inject.Inject

class AlbumServiceRetrofit @Inject constructor(
    private val retrofit: Retrofit,
) : AlbumServiceAdapter {

    private val albumAPI by lazy { retrofit.create(AlbumAPI::class.java) }

    override suspend fun getAllAlbums(): Result<List<AlbumSimpleDTO>> {
        return try {
            val response = albumAPI.getAllAlbums()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAlbumById(id: String): Result<AlbumDetailDTO> {
        return try {
            val response = albumAPI.getAlbumById(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createAlbum(newAlbum: AlbumSimpleDTO): Result<AlbumSimpleDTO> {
        return try {
            val response = albumAPI.createAlbum(newAlbum)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
