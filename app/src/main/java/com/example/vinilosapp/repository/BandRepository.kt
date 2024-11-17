package com.example.vinilosapp.repository

import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import com.example.vinilosapp.di.Cache
import com.example.vinilosapp.services.adapters.BandServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import javax.inject.Inject

class BandRepository @Inject constructor(
    private val bandServiceAdapter: BandServiceAdapter,
    networkChecker: NetworkChecker,
    cache: Cache,
) : BaseRepository<BandSimpleDTO, BandDetailDTO>(
    cache,
    networkChecker,
    BandSimpleDTO::class.java,
    BandDetailDTO::class.java,
) {

    override suspend fun fetchAllItems(): Result<List<BandSimpleDTO>> {
        return bandServiceAdapter.getBands()
    }

    override suspend fun fetchItemById(id: String): Result<BandDetailDTO> {
        return bandServiceAdapter.getBandById(id)
    }
}
