package com.example.vinilosapp.repository

import com.example.vinilosapp.di.Cache
import com.example.vinilosapp.utils.NetworkChecker

abstract class BaseRepository<SimpleDTO : Any, DetailDTO : Any>(
    private val lruCache: Cache,
    protected val networkChecker: NetworkChecker,
    private val simpleClass: Class<SimpleDTO>,
    private val detailClass: Class<DetailDTO>,
) {

    private val listKey: String by lazy { "list-${simpleClass.simpleName}" }
    private val detailKeyPrefix: String by lazy { "detail-${detailClass.simpleName}" }

    suspend fun fetchAll(): Result<List<SimpleDTO>> {
        val cachedList = lruCache.getList<SimpleDTO>(listKey)
        if (cachedList != null) {
            return Result.success(cachedList)
        }

        return if (networkChecker.isConnected()) {
            fetchAllItems().onSuccess { list ->
                lruCache.putList(listKey, list)
            }
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }

    suspend fun fetchById(id: String): Result<DetailDTO> {
        val detailKey = "$detailKeyPrefix-$id"

        val cachedDetail = lruCache.getDetail<DetailDTO>(detailKey)
        if (cachedDetail != null) {
            return Result.success(cachedDetail)
        }

        return if (networkChecker.isConnected()) {
            fetchItemById(id).onSuccess { item ->
                lruCache.putDetail(detailKey, item)
            }
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }

    fun clearCache() {
        lruCache.clear()
    }

    // Abstract methods for fetching from the network
    protected abstract suspend fun fetchAllItems(): Result<List<SimpleDTO>>
    protected abstract suspend fun fetchItemById(id: String): Result<DetailDTO>
}
