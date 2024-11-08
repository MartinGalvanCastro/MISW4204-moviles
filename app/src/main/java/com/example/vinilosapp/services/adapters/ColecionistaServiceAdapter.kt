package com.example.vinilosapp.services.adapters

import com.example.models.CollectorDetailDTO
import com.example.models.CollectorSimpleDTO

interface ColecionistaServiceAdapter {

    suspend fun getColecionistas(): Result<List<CollectorSimpleDTO>>
    suspend fun getColecionistaById(id: String): Result<CollectorDetailDTO>
}
