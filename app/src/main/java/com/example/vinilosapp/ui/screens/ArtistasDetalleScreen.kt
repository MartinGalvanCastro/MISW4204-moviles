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
import com.example.vinilosapp.ui.components.AlbumSection
import com.example.vinilosapp.ui.components.DetailDTO
import com.example.vinilosapp.ui.components.DetailedTopBar
import com.example.vinilosapp.ui.components.InfoSection
import com.example.vinilosapp.ui.components.PremiosSection
import com.example.vinilosapp.ui.components.ScreenSkeleton
import com.example.vinilosapp.viewmodel.MusicianViewModel

@Composable
fun ArtistasDetalleScreen(
    artistaId: String?,
    artistaViewModel: MusicianViewModel = hiltViewModel(),
) {
    val performerState by artistaViewModel.state.collectAsState()
    val prizesState by artistaViewModel.prizesState.collectAsState()

    LaunchedEffect(artistaId) {
        artistaId?.let { artistaViewModel.fetchDetailById(it) }
    }

    LaunchedEffect(performerState.detail) {
        performerState.detail?.performerPrizes?.map { it.id.toString() }?.let { prizeIds ->
            artistaViewModel.fetchPrizesForPerformer(prizeIds)
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
                                item = DetailDTO.MusicianDetail(performerState.detail!!),
                                modifier = Modifier.testTag("infoSection"),
                            )
                        }

                        if (performerState.detail!!.performerPrizes?.isNotEmpty() == true &&
                            prizesState.items.isNotEmpty()
                        ) {
                            item(key = "premiosSection") {
                                PremiosSection(
                                    premios = prizesState.items,
                                    modifier = Modifier.testTag("premiosSection"),
                                )
                            }
                        }

                        if (performerState.detail!!.albums?.isNotEmpty() == true) {
                            item(key = "albumSection") {
                                AlbumSection(
                                    modifier = Modifier.testTag("albumsSection"),
                                    albumes = performerState.detail!!.albums!!,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
