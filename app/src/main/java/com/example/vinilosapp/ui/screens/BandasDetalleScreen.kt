package com.example.vinilosapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vinilosapp.ui.components.DetailedTopBar
import com.example.vinilosapp.ui.components.ScreenSkeleton

@Composable
fun BandasDetalleScreen(bandaId: String?) {
    Scaffold(
        topBar = {
            DetailedTopBar("Album $bandaId")
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (bandaId != null) {
                ScreenSkeleton(screenName = "Bandas Detalle $bandaId")
            } else {
                Text("Loading...")
            }
        }
    }
}
