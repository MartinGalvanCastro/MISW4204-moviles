package com.example.vinilosapp.repository

import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import com.example.vinilosapp.services.adapters.BandServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import javax.inject.Inject

class BandRepository @Inject constructor(
    private val bandServiceAdapter: BandServiceAdapter,
    private val networkChecker: NetworkChecker,
) {

    suspend fun fetchBands(): Result<List<BandSimpleDTO>> {
        return if (networkChecker.isConnected()) {
            bandServiceAdapter.getBands()
        } else {
            // TODO: Add cases for Cache and Local Storage
            Result.failure(Exception("No internet connection"))
        }
    }

    suspend fun fetchBandById(id: String): Result<BandDetailDTO> {
        return if (networkChecker.isConnected()) {
            bandServiceAdapter.getBandById(id)
        } else {
            // TODO: Add cases for Cache and Local Storage
            Result.failure(Exception("No internet connection"))
        }
    }
}
