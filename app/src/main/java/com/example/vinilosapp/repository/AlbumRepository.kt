package com.example.vinilosapp.repository

import com.example.models.AlbumSimpleDTO
import com.example.models.CollectorAlbumSimpleDTO
import com.example.vinilosapp.di.Cache
import com.example.vinilosapp.models.AlbumDetail
import com.example.vinilosapp.models.AlbumSimple
import com.example.vinilosapp.services.adapters.AlbumServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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

    suspend fun fetchCollectorAlbums(collectoAlbums: List<CollectorAlbumSimpleDTO>): List<AlbumSimpleDTO> = coroutineScope {
        val results = collectoAlbums.map { album ->
            async {
                album.id to fetchById("${album.id}")
            }
        }.awaitAll()

        val succesfulAlbums = results.mapNotNull { (_, result) ->
            val detail = result.getOrNull()
            detail?.let {
                AlbumSimpleDTO(
                    id = detail.id,
                    name = detail.name,
                    cover = it.cover,
                    description = detail.description,
                    releaseDate = detail.releaseDate,
                )
            }
        }

        succesfulAlbums
    }
}
