package com.example.vinilosapp.repository

import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import com.example.vinilosapp.services.adapters.BandServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import javax.inject.Inject

class BandRepository @Inject constructor(
    private val bandServiceAdapter: BandServiceAdapter,
    networkChecker: NetworkChecker,
) : BaseRepository<BandSimpleDTO, BandDetailDTO>(networkChecker) {

    override suspend fun fetchAllItems(): Result<List<BandSimpleDTO>> {
        return bandServiceAdapter.getBands()
    }

    override suspend fun fetchItemById(id: String): Result<BandDetailDTO> {
        return bandServiceAdapter.getBandById(id)
    }
}
