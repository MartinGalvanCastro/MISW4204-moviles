package com.example.vinilosapp.repository

import com.example.models.CollectorDetailDTO
import com.example.models.CollectorSimpleDTO
import com.example.vinilosapp.services.adapters.ColecionistaServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import javax.inject.Inject

class ColeccionistaRepository @Inject constructor(
    private val coleccionistaServiceAdapter: ColecionistaServiceAdapter,
    networkChecker: NetworkChecker,
) : BaseRepository<CollectorSimpleDTO, CollectorDetailDTO>(networkChecker) {
    override suspend fun fetchAllItems(): Result<List<CollectorSimpleDTO>> {
        return coleccionistaServiceAdapter.getColecionistas()
    }

    override suspend fun fetchItemById(id: String): Result<CollectorDetailDTO> {
        return coleccionistaServiceAdapter.getColecionistaById(id)
    }
}
