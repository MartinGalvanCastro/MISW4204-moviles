package com.example.vinilosapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vinilosapp.LocalAppState
import com.example.vinilosapp.models.GridItemProps
import com.example.vinilosapp.navigation.DetailRoutePrefix
import com.example.vinilosapp.ui.components.GridLayout
import com.example.vinilosapp.ui.components.ScreenSkeleton
import com.example.vinilosapp.viewmodel.BandViewModel

@Composable
fun BandasScreen(bandViewModel: BandViewModel = hiltViewModel()) {
    val navController = LocalAppState.current.navController

    val bands by bandViewModel.items.collectAsState()
    val loading by bandViewModel.loading.collectAsState()
    val error by bandViewModel.errorMessage.collectAsState()

    var filterText by remember { mutableStateOf("") }

    val filteredBands = bands.filter {
        it.name.contains(filterText, ignoreCase = true)
    }

    val gridItems = filteredBands.map { band ->
        GridItemProps(name = band.name, imageUrl = band.image, onSelect = {
            navController.navigate("${DetailRoutePrefix.BAND_DETALLE_SCREEN}/${band.id}")
        })
    }

    LaunchedEffect(Unit) {
        bandViewModel.fetchAllItems()
    }

    LaunchedEffect(filterText) {
        bandViewModel.filterBands(filterText)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        TextField(
            value = filterText,
            onValueChange = { filterText = it },
            label = { Text("Filtro") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("filterTextField"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            when {
                loading -> {
                    ScreenSkeleton("Cargando...", modifier = Modifier.testTag("loadingMessage"))
                }
                error != null -> {
                    ScreenSkeleton(error!!, modifier = Modifier.testTag("errorMessage"))
                }
                else -> {
                    GridLayout(gridItems, "bandItem", modifier = Modifier.testTag("bandGrid"))
                }
            }
        }
    }
}
