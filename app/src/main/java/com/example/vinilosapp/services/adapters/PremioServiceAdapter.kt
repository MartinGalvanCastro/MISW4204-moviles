package com.example.vinilosapp.services.adapters

import com.example.models.PrizeDetailDTO

interface PremioServiceAdapter {

    suspend fun getPremioById(id: String): Result<PrizeDetailDTO>
}
