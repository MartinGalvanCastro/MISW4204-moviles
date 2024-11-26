package com.example.vinilosapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.models.PerformerSimpleDTO
import com.example.vinilosapp.ui.components.AlbumSection
import com.example.vinilosapp.ui.components.ArtistSection
import com.example.vinilosapp.ui.components.DetailDTO
import com.example.vinilosapp.ui.components.DetailedTopBar
import com.example.vinilosapp.ui.components.InfoSection
import com.example.vinilosapp.ui.components.PremiosSection
import com.example.vinilosapp.ui.components.ScreenSkeleton
import com.example.vinilosapp.viewmodel.BandViewModel

@Composable
fun BandasDetalleScreen(
    bandaId: String?,
    bandaViewModel: BandViewModel = hiltViewModel(),
) {
    val performerState by bandaViewModel.state.collectAsState()
    val prizesState by bandaViewModel.prizesState.collectAsState()

    LaunchedEffect(bandaId) {
        bandaId?.let { bandaViewModel.fetchDetailById(it) }
    }

    LaunchedEffect(performerState.detail) {
        performerState.detail?.performerPrizes?.map { it.id.toString() }?.let { prizeIds ->
            bandaViewModel.fetchPrizesForPerformer(prizeIds)
        }
    }

    Scaffold(
        topBar = {
            DetailedTopBar(
                text = performerState.detail?.name.orEmpty(),
                modifier = Modifier.testTag("topBarTitle"),
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.TopStart,
        ) {
            when {
                performerState.errorMessage != null -> {
                    ScreenSkeleton(
                        screenName = performerState.errorMessage!!,
                        modifier = Modifier.testTag("errorMessage"),
                    )
                }
                performerState.isLoading || prizesState.isLoading -> {
                    ScreenSkeleton(
                        screenName = "Cargando...",
                        modifier = Modifier.testTag("loadingMessage"),
                    )
                }
                performerState.detail != null -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                    ) {
                        item(key = "infoSection") {
                            InfoSection(
                                item = DetailDTO.BandDetail(performerState.detail!!),
                                modifier = Modifier.testTag("infoSection"),
                            )
                        }

                        if (!performerState.detail!!.performerPrizes.isNullOrEmpty() &&
                            prizesState.items.isNotEmpty()
                        ) {
                            item(key = "premiosSection") {
                                PremiosSection(
                                    premios = prizesState.items,
                                    modifier = Modifier.testTag("premiosSection"),
                                )
                            }
                        }

                        if (!performerState.detail!!.musicians.isNullOrEmpty()) {
                            item(key = "miembrosSection") {
                                val musicians = performerState.detail!!.musicians as? List<PerformerSimpleDTO>
                                musicians?.let {
                                    ArtistSection(
                                        it,
                                        fromBandas = true,
                                        fromColeccionista = false,
                                        Modifier.testTag("miembrosSection"),
                                    )
                                }
                            }
                        }

                        if (!performerState.detail!!.albums.isNullOrEmpty()) {
                            item(key = "albumSection") {
                                AlbumSection(
                                    albumes = performerState.detail!!.albums!!,
                                    modifier = Modifier.testTag("albumsSection"),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
