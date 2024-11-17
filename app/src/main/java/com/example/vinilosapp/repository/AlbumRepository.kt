package com.example.vinilosapp.repository

import com.example.models.AlbumDetailDTO
import com.example.models.AlbumSimpleDTO
import com.example.vinilosapp.di.Cache
import com.example.vinilosapp.services.adapters.AlbumServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val albumServiceAdapter: AlbumServiceAdapter,
    networkChecker: NetworkChecker,
    cache: Cache,
) : BaseRepository<AlbumSimpleDTO, AlbumDetailDTO>(
    cache,
    networkChecker,
    AlbumSimpleDTO::class.java,
    AlbumDetailDTO::class.java,
) {

    override suspend fun fetchAllItems(): Result<List<AlbumSimpleDTO>> {
        return albumServiceAdapter.getAllAlbums()
    }

    override suspend fun fetchItemById(id: String): Result<AlbumDetailDTO> {
        return albumServiceAdapter.getAlbumById(id)
    }

    suspend fun createAlbum(newAlbum: AlbumSimpleDTO): Result<AlbumSimpleDTO> {
        return if (networkChecker.isConnected()) {
            CoroutineScope(Dispatchers.IO).launch {
                clearCache()
            }
            albumServiceAdapter.createAlbum(newAlbum)
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }
}
