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
import com.example.vinilosapp.ui.components.ArtistSection
import com.example.vinilosapp.ui.components.CommentSection
import com.example.vinilosapp.ui.components.DetailDTO
import com.example.vinilosapp.ui.components.DetailedTopBar
import com.example.vinilosapp.ui.components.InfoSection
import com.example.vinilosapp.ui.components.ScreenSkeleton
import com.example.vinilosapp.viewmodel.ColeccionistaViewModel

@Composable
fun ColeccionistasDetalleScreen(
    coleccionstaId: String?,
    coleccionistaViewModel: ColeccionistaViewModel = hiltViewModel(),
) {
    val collectorState by coleccionistaViewModel.state.collectAsState()
    val albumsState by coleccionistaViewModel.albumsState.collectAsState()

    LaunchedEffect(coleccionstaId) {
        coleccionstaId?.let { coleccionistaViewModel.fetchDetailById(it) }
    }

    LaunchedEffect(collectorState.detail) {
        collectorState.detail?.collectorAlbums?.let {
            coleccionistaViewModel.fetchAlbumsOfCollector(
                it,
            )
        }
    }

    Scaffold(
        topBar = {
            DetailedTopBar(
                text = collectorState.detail?.name.orEmpty(),
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
                collectorState.errorMessage != null -> {
                    ScreenSkeleton(
                        screenName = collectorState.errorMessage!!,
                        modifier = Modifier.testTag("errorMessage"),
                    )
                }
                collectorState.isLoading || albumsState.isLoading -> {
                    ScreenSkeleton(
                        screenName = "Cargando...",
                        modifier = Modifier.testTag("loadingMessage"),
                    )
                }
                collectorState.detail != null -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                    ) {
                        item(key = "infoSection") {
                            InfoSection(
                                item = DetailDTO.ColeccionistaDetail(collectorState.detail!!),
                                modifier = Modifier.testTag("infoSection"),
                            )
                        }

                        if (!collectorState.detail!!.collectorAlbums.isNullOrEmpty() &&
                            albumsState.items.isNotEmpty()
                        ) {
                            item(key = "albumSection") {
                                AlbumSection(
                                    albumes = albumsState.items,
                                    modifier = Modifier.testTag("albumSection"),
                                )
                            }
                        }

                        if (!collectorState.detail!!.favoritePerformers.isNullOrEmpty()) {
                            item(key = "artistas") {
                                val musicians = collectorState.detail!!.favoritePerformers
                                musicians?.let {
                                    ArtistSection(
                                        it,
                                        fromBandas = true,
                                        Modifier.testTag("artistasSection"),
                                    )
                                }
                            }
                        }

                        if (!collectorState.detail!!.comments.isNullOrEmpty()) {
                            item(key = "commentSection") {
                                CommentSection(
                                    comentarios = collectorState.detail!!.comments!!,
                                    modifier = Modifier.testTag("commentSection"),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
