package com.example.vinilosapp.ui.screens

import ItemCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.vinilosapp.navigation.DetailRoutePrefix
import com.example.vinilosapp.ui.components.ScreenSkeleton
import com.example.vinilosapp.viewmodel.ColeccionistaViewModel

@Composable
fun ColeccionistaScreen(coleccionistaViewModel: ColeccionistaViewModel = hiltViewModel()) {
    val navController = LocalAppState.current.navController
    val state by coleccionistaViewModel.state.collectAsState()

    var filterText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        coleccionistaViewModel.fetchAllItems()
    }

    LaunchedEffect(filterText) {
        coleccionistaViewModel.filterCollectors(filterText)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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

        Spacer(Modifier.height(10.dp))

        when {
            state.isLoading -> {
                ScreenSkeleton("Cargando...", modifier = Modifier.testTag("loadingMessage"))
            }
            state.errorMessage != null -> {
                ScreenSkeleton(state.errorMessage!!, modifier = Modifier.testTag("errorMessage"))
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("collectorList"),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.filteredItems, key = { it.id!! }) { collector ->
                        ItemCard(
                            title = collector.name,
                            description = collector.email,
                            footer = collector.telephone,
                            onSelect = {
                                navController.navigate("${DetailRoutePrefix.COLECCIONISTA_DETALLE_SCREEN}/${collector.id}")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("collectorItem"),
                        )
                    }
                }
            }
        }
    }
}
