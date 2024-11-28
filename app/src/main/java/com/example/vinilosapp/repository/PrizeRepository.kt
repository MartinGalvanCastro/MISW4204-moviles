package com.example.vinilosapp.repository

import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.di.Cache
import com.example.vinilosapp.services.adapters.PremioServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import kotlinx.coroutines.async
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
        val successfulPrizes = mutableListOf<PrizeDetailDTO>()

        for (i in prizeIds.indices) {
            val id = prizeIds[i]
            val deferredResult = async {
                fetchById(id).getOrNull()
            }
            // Add to the list only if the result is successful
            deferredResult.await()?.let { successfulPrizes.add(it) }
        }

        successfulPrizes
    }
}
