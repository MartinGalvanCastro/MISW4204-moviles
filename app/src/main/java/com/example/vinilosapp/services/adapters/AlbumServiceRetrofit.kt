package com.example.vinilosapp.services.adapters

import com.example.models.TrackSimpleDTO
import com.example.vinilosapp.models.AlbumDetail
import com.example.vinilosapp.models.AlbumSimple
import com.example.vinilosapp.services.api.AlbumAPI
import retrofit2.Retrofit
import javax.inject.Inject

class AlbumServiceRetrofit @Inject constructor(
    private val retrofit: Retrofit,
) : AlbumServiceAdapter {

    private val albumAPI by lazy { retrofit.create(AlbumAPI::class.java) }

    override suspend fun getAllAlbums(): Result<List<AlbumSimple>> {
        return try {
            val response = albumAPI.getAllAlbums()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAlbumById(id: String): Result<AlbumDetail> {
        return try {
            val response = albumAPI.getAlbumById(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createAlbum(newAlbum: AlbumSimple): Result<AlbumSimple> {
        return try {
            val response = albumAPI.createAlbum(newAlbum)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun linkAlbumTo(albumId: String, performerId: String, isBand: Boolean): Result<String> {
        return try {
            if (isBand) {
                albumAPI.linkAlbumToBand(albumId, performerId)
            } else {
                albumAPI.linkAlbumToMusician(albumId, performerId)
            }

            return Result.success("Se creo el album exitosamente")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun linkTrackToAlbum(
        albumId: String,
        trackSimpleDTO: TrackSimpleDTO,
    ): Result<String> {
        return try {
            albumAPI.linkTrackToAlbum(albumId, trackSimpleDTO)
            Result.success("Se creo la cancion exitosamente")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
