package com.example.vinilosapp.services.adapters

import com.example.models.MusicianDetailDTO
import com.example.models.MusicianSimpleDTO

interface MusicianServiceAdapter {

    suspend fun getMusicians(): Result<List<MusicianSimpleDTO>>
    suspend fun getMusicianById(id: String): Result<MusicianDetailDTO>
}
