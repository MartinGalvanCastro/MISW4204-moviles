package com.example.vinilosapp.repository

import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.services.adapters.PremioServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import javax.inject.Inject

class PrizeRepository @Inject constructor(
    private val premioServiceAdapter: PremioServiceAdapter,
    networkChecker: NetworkChecker,
) : BaseRepository<PrizeDetailDTO, PrizeDetailDTO>(networkChecker) {

    override suspend fun fetchAllItems(): Result<List<PrizeDetailDTO>> {
        return Result.success(emptyList())
    }

    override suspend fun fetchItemById(id: String): Result<PrizeDetailDTO> {
        return premioServiceAdapter.getPremioById(id)
    }

    suspend fun fetchPrizes(prizeIds: List<String>): List<PrizeDetailDTO> {
        return if (networkChecker.isConnected()) {
            prizeIds.fold(mutableListOf()) { accumulator, prizeId ->
                val result = fetchItemById(prizeId)
                result.onSuccess { prize ->
                    accumulator.add(prize)
                }
                accumulator
            }
        } else {
            return emptyList()
        }
    }
}
