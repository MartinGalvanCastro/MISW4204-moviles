package com.example.vinilosapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
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
import com.example.vinilosapp.ui.components.ArtistSection
import com.example.vinilosapp.ui.components.CancionesSection
import com.example.vinilosapp.ui.components.CommentSection
import com.example.vinilosapp.ui.components.DetailDTO
import com.example.vinilosapp.ui.components.DetailedTopBar
import com.example.vinilosapp.ui.components.InfoSection
import com.example.vinilosapp.ui.components.ScreenSkeleton
import com.example.vinilosapp.utils.TipoUsuario
import com.example.vinilosapp.viewmodel.AlbumViewModel

@Composable
fun AlbumDetalleScreen(
    albumId: String?,
    albumViewModel: AlbumViewModel = hiltViewModel(),
) {
    val state by albumViewModel.state.collectAsState()

    LaunchedEffect(albumId) {
        albumId?.let { albumViewModel.fetchDetailById(it) }
    }

    Scaffold(
        topBar = {
            DetailedTopBar(
                text = state.detail?.name.orEmpty(),
                modifier = Modifier.testTag("topBarTitle"),
            )
        },
    ) { innerPadding ->
        when {
            state.errorMessage != null -> {
                ScreenSkeleton(
                    screenName = state.errorMessage!!,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .testTag("errorMessage"),
                )
            }
            state.isLoading -> {
                ScreenSkeleton(
                    screenName = "Cargando...",
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .testTag("loadingMessage"),
                )
            }
            state.detail != null -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 32.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    item(key = "infoSection") {
                        InfoSection(
                            item = DetailDTO.AlbumDetail(state.detail!!),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("infoSection"),
                        )
                    }

                    if (state.detail!!.tracks.isNotEmpty()) {
                        item(key = "tracksSection") {
                            CancionesSection(
                                state.detail!!.tracks,
                                TipoUsuario.INVITADO,
                                Modifier
                                    .fillMaxWidth()
                                    .testTag("tracksSection"),
                            )
                        }
                    }

                    if (state.detail!!.performers.isNotEmpty()) {
                        item(key = "performersSection") {
                            ArtistSection(
                                state.detail!!.performers,
                                fromBandas = false,
                                fromColeccionista = false,
                                Modifier.testTag("performersSection"),
                            )
                        }
                    }

                    if (state.detail!!.comments.isNotEmpty()) {
                        item(key = "commentsSection") {
                            CommentSection(
                                state.detail!!.comments,
                                Modifier
                                    .fillMaxWidth()
                                    .testTag("commentsSection"),
                            )
                        }
                    }
                }
            }
        }
    }
}
