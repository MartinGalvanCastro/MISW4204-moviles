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
import com.example.models.AlbumDetailDTO
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
fun AlbumDetalleInternalScreen(album: AlbumDetailDTO, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        InfoSection(
            item = DetailDTO.AlbumDetail(album),
            modifier = Modifier.testTag("infoSection"),
        )

        if (album.tracks.isNotEmpty()) {
            Spacer(Modifier.height(5.dp))
            CancionesSection(album.tracks, TipoUsuario.INVITADO, Modifier.testTag("tracksSection"))
        }

        if (album.performers.isNotEmpty()) {
            Spacer(Modifier.height(5.dp))
            ArtistSection(album.performers, Modifier.testTag("performersSection"))
        }

        if (album.comments.isNotEmpty()) {
            Spacer(Modifier.height(5.dp))
            CommentSection(album.comments, Modifier.testTag("commentsSection"))
        }
    }
}

@Composable
fun AlbumDetalleScreen(albumId: String?, albumViewModel: AlbumViewModel = hiltViewModel()) {
    val album by albumViewModel.album.collectAsState()
    val loading by albumViewModel.loading.collectAsState()
    val error by albumViewModel.errorMessage.collectAsState()

    LaunchedEffect(albumId) {
        if (albumId != null) {
            albumViewModel.fetchAlbumById(albumId)
        }
    }

    Scaffold(
        topBar = {
            if (album == null) {
                DetailedTopBar("")
            } else {
                DetailedTopBar(album!!.name, Modifier.testTag("topBarTitle"))
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
                    error != null || (!loading && album == null) -> {
                        error?.let { ScreenSkeleton(it, Modifier.testTag("errorMessage")) }
                    }
                    loading -> {
                        ScreenSkeleton("Cargando...", Modifier.testTag("loadingMessage"))
                    }
                    else -> {
                        album?.let { AlbumDetalleInternalScreen(it) }
                    }
                }
            }
        }
    }
}
