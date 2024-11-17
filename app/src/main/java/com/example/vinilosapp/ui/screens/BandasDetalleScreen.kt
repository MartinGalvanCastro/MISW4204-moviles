package com.example.vinilosapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.models.BandDetailDTO
import com.example.models.PerformerSimpleDTO
import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.ui.components.AlbumSection
import com.example.vinilosapp.ui.components.ArtistSection
import com.example.vinilosapp.ui.components.DetailDTO
import com.example.vinilosapp.ui.components.DetailedTopBar
import com.example.vinilosapp.ui.components.InfoSection
import com.example.vinilosapp.ui.components.PremiosSection
import com.example.vinilosapp.ui.components.ScreenSkeleton
import com.example.vinilosapp.viewmodel.BandViewModel

@Composable
fun BandaDetalleInternalScreen(
    banda: BandDetailDTO,
    premios: List<PrizeDetailDTO>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        InfoSection(
            item = DetailDTO.BandDetail(banda),
            modifier = Modifier.testTag("infoSection"),
        )

        if (banda.performerPrizes !== null && banda.performerPrizes.isNotEmpty()) {
            Spacer(Modifier.height(5.dp))
            PremiosSection(premios, Modifier.testTag("premiosSection"))
        }

        if (!banda.musicians.isNullOrEmpty()) {
            Spacer(Modifier.height(5.dp))
            val musicians = banda.musicians as? List<PerformerSimpleDTO>
            musicians?.let {
                ArtistSection(it, fromBandas = true, Modifier.testTag("miembrosSection"))
            }
        }

        if (banda.albums !== null && banda.albums.isNotEmpty()) {
            Spacer(Modifier.height(5.dp))
            AlbumSection(banda.albums, Modifier.testTag("performersSection"))
        }
    }
}

@Composable
fun BandasDetalleScreen(
    bandaId: String?,
    bandaViewModel: BandViewModel = hiltViewModel(),
) {
    val banda by bandaViewModel.detail.collectAsState()
    val loadingBanda by bandaViewModel.loading.collectAsState()
    val errorBanda by bandaViewModel.errorMessage.collectAsState()
    val premios by bandaViewModel.prizes.collectAsState()

    LaunchedEffect(bandaId) {
        if (bandaId != null) {
            bandaViewModel.fetchDetailById(bandaId)
        }
    }

    LaunchedEffect(banda) {
        banda?.let { bandaDefined ->
            bandaDefined.performerPrizes?.let { prizes ->
                val prizesId = prizes.map { prize -> prize.id.toString() }
                bandaViewModel.fetchPrizes(prizesId)
            }
        }
    }

    Scaffold(
        topBar = {
            if (banda == null) {
                DetailedTopBar("")
            } else {
                DetailedTopBar(banda!!.name, Modifier.testTag("topBarTitle"))
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item {
                when {
                    errorBanda != null || (!loadingBanda && banda == null) -> {
                        errorBanda?.let { ScreenSkeleton(it, Modifier.testTag("errorMessage")) }
                    }
                    loadingBanda -> {
                        ScreenSkeleton("Cargando...", Modifier.testTag("loadingMessage"))
                    }
                    else -> {
                        banda?.let { BandaDetalleInternalScreen(it, premios) }
                    }
                }
            }
        }
    }
}
