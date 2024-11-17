package com.example.vinilosapp.repository

import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.di.Cache
import com.example.vinilosapp.services.adapters.PremioServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class PrizeRepository @Inject constructor(
    private val premioServiceAdapter: PremioServiceAdapter,
    networkChecker: NetworkChecker,
    cache: Cache,
) : BaseRepository<PrizeDetailDTO, PrizeDetailDTO>(
    cache,
    networkChecker,
    simpleClass = PrizeDetailDTO::class.java,
    detailClass = PrizeDetailDTO::class.java,
) {

    override suspend fun fetchAllItems(): Result<List<PrizeDetailDTO>> {
        return Result.success(emptyList())
    }

    override suspend fun fetchItemById(id: String): Result<PrizeDetailDTO> {
        return premioServiceAdapter.getPremioById(id)
    }

    suspend fun fetchPrizes(prizeIds: List<String>): List<PrizeDetailDTO> = coroutineScope {
        val results = prizeIds.map { id ->
            async {
                id to fetchById(id)
            }
        }.awaitAll()

        val successfulPrizes = results.mapNotNull { (_, result) ->
            result.getOrNull()
        }

        successfulPrizes
    }
}
