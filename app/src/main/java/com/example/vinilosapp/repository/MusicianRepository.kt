package com.example.vinilosapp.repository

import com.example.models.MusicianDetailDTO
import com.example.models.MusicianSimpleDTO
import com.example.vinilosapp.services.adapters.MusicianServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import javax.inject.Inject

class MusicianRepository @Inject constructor(
    private val musicianServiceAdapter: MusicianServiceAdapter,
    private val networkChecker: NetworkChecker,
) {

    suspend fun fetchMusicians(): Result<List<MusicianSimpleDTO>> {
        return if (networkChecker.isConnected()) {
            musicianServiceAdapter.getMusicians()
        } else {
            // TODO: Add cases for Cache and Local Storage
            Result.failure(Exception("No internet connection"))
        }
    }

    suspend fun fetchMusicianById(id: String): Result<MusicianDetailDTO> {
        return if (networkChecker.isConnected()) {
            musicianServiceAdapter.getMusicianById(id)
        } else {
            // TODO: Add cases for Cache and Local Storage
            Result.failure(Exception("No internet connection"))
        }
    }
}
