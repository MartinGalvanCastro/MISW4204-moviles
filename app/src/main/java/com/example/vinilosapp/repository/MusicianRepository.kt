package com.example.vinilosapp.repository

import com.example.models.MusicianDetailDTO
import com.example.models.MusicianSimpleDTO
import com.example.vinilosapp.services.adapters.MusicianServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import javax.inject.Inject

class MusicianRepository @Inject constructor(
    private val musicianServiceAdapter: MusicianServiceAdapter,
    networkChecker: NetworkChecker,
) : BaseRepository<MusicianSimpleDTO, MusicianDetailDTO>(networkChecker) {

    override suspend fun fetchAllItems(): Result<List<MusicianSimpleDTO>> {
        return musicianServiceAdapter.getMusicians()
    }

    override suspend fun fetchItemById(id: String): Result<MusicianDetailDTO> {
        return musicianServiceAdapter.getMusicianById(id)
    }
}
