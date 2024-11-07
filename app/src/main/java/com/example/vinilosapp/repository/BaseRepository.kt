package com.example.vinilosapp.repository

import com.example.vinilosapp.utils.NetworkChecker

abstract class BaseRepository<SimpleDTO, DetailDTO>(
    protected val networkChecker: NetworkChecker,
) {

    protected abstract suspend fun fetchAllItems(): Result<List<SimpleDTO>>
    protected abstract suspend fun fetchItemById(id: String): Result<DetailDTO>

    suspend fun fetchAll(): Result<List<SimpleDTO>> {
        return if (networkChecker.isConnected()) {
            fetchAllItems()
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }

    suspend fun fetchById(id: String): Result<DetailDTO> {
        return if (networkChecker.isConnected()) {
            fetchItemById(id)
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }
}
