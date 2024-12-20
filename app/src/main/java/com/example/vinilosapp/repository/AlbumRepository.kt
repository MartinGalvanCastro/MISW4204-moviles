package com.example.vinilosapp.repository

import com.example.models.AlbumSimpleDTO
import com.example.models.CollectorAlbumSimpleDTO
import com.example.models.TrackSimpleDTO
import com.example.vinilosapp.di.Cache
import com.example.vinilosapp.models.AlbumDetail
import com.example.vinilosapp.models.AlbumSimple
import com.example.vinilosapp.services.adapters.AlbumServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val albumServiceAdapter: AlbumServiceAdapter,
    networkChecker: NetworkChecker,
    cache: Cache,
) : BaseRepository<AlbumSimple, AlbumDetail>(
    cache,
    networkChecker,
    AlbumSimple::class.java,
    AlbumDetail::class.java,
) {

    override suspend fun fetchAllItems(): Result<List<AlbumSimple>> {
        return albumServiceAdapter.getAllAlbums()
    }

    override suspend fun fetchItemById(id: String): Result<AlbumDetail> {
        return albumServiceAdapter.getAlbumById(id)
    }

    suspend fun createAlbum(newAlbum: AlbumSimple): Result<AlbumSimple> {
        return if (networkChecker.isConnected()) {
            CoroutineScope(Dispatchers.IO).launch {
                clearCache()
            }
            albumServiceAdapter.createAlbum(newAlbum)
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }

    suspend fun linkAlbumTo(albumId: String, performerId: String, isBand: Boolean): Result<String> {
        return if (networkChecker.isConnected()) {
            CoroutineScope(Dispatchers.IO).launch {
                clearCache()
            }
            albumServiceAdapter.linkAlbumTo(albumId, performerId, isBand)
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }

    suspend fun fetchCollectorAlbums(collectorAlbums: List<CollectorAlbumSimpleDTO>): List<AlbumSimpleDTO> = coroutineScope {
        val results = mutableListOf<AlbumSimpleDTO>()

        for (i in collectorAlbums.indices) {
            val album = collectorAlbums[i]
            val deferredResult = async {
                val result = fetchById("${album.id}")
                result.getOrNull()?.let { detail ->
                    AlbumSimpleDTO(
                        id = detail.id,
                        name = detail.name,
                        cover = detail.cover,
                        description = detail.description,
                        releaseDate = detail.releaseDate,
                    )
                }
            }
            // Only add successful results
            deferredResult.await()?.let { results.add(it) }
        }

        results
    }

    suspend fun createSong(albumId: String, simpleDTO: TrackSimpleDTO): Result<String> {
        return if (networkChecker.isConnected()) {
            CoroutineScope(Dispatchers.IO).launch {
                clearCache()
            }
            albumServiceAdapter.linkTrackToAlbum(albumId, simpleDTO)
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }
}
