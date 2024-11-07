package com.example.vinilosapp.ui.screens

import android.util.Log
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
import com.example.models.MusicianDetailDTO
import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.ui.components.AlbumSection
import com.example.vinilosapp.ui.components.DetailDTO
import com.example.vinilosapp.ui.components.DetailedTopBar
import com.example.vinilosapp.ui.components.InfoSection
import com.example.vinilosapp.ui.components.PremiosSection
import com.example.vinilosapp.ui.components.ScreenSkeleton
import com.example.vinilosapp.viewmodel.MusicianViewModel

@Composable
fun ArtistaDetalleInternalScreen(
    artista: MusicianDetailDTO,
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
            item = DetailDTO.MusicianDetail(artista),
            modifier = Modifier.testTag("infoSection"),
        )

        if (!artista.performerPrizes.isNullOrEmpty() && premios.isNotEmpty()) {
            Spacer(Modifier.height(5.dp))
            Log.d("PREMIOS>>>", premios.toString())
            PremiosSection(premios, Modifier.testTag("premiosSection"))
        }

        if (!artista.albums.isNullOrEmpty()) {
            Spacer(Modifier.height(5.dp))
            AlbumSection(artista.albums, Modifier.testTag("albumsSection"))
        }
    }
}

@Composable
fun ArtistasDetalleScreen(
    artistaId: String?,
    artistaViewModel: MusicianViewModel = hiltViewModel(),
) {
    val artista by artistaViewModel.detail.collectAsState()
    val loadingArtista by artistaViewModel.loading.collectAsState()
    val errorArtista by artistaViewModel.errorMessage.collectAsState()
    val premios by artistaViewModel.prizes.collectAsState()

    LaunchedEffect(artistaId) {
        if (artistaId != null) {
            artistaViewModel.fetchDetailById(artistaId)
        }
    }

    LaunchedEffect(artista) {
        artista?.performerPrizes?.let { prizes ->
            artistaViewModel.fetchPrizes(prizes.map { prize -> prize.id.toString() })
        }
    }

    Scaffold(
        topBar = {
            DetailedTopBar(
                text = artista?.name.orEmpty(),
                modifier = Modifier.testTag("topBarTitle"),
            )
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
                    errorArtista != null || (!loadingArtista && artista == null) -> {
                        errorArtista?.let { ScreenSkeleton(it, Modifier.testTag("errorMessage")) }
                    }
                    loadingArtista -> {
                        ScreenSkeleton("Cargando...", Modifier.testTag("loadingMessage"))
                    }
                    else -> {
                        artista?.let { ArtistaDetalleInternalScreen(it, premios) }
                    }
                }
            }
        }
    }
}
