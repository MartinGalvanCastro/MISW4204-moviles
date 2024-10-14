package com.example.vinilosapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.vinilosapp.ui.components.ScreenSkeleton

@Composable
fun ColeccionistasScreen() {
    ScreenSkeleton(screenName = "Coleccionistas")
}

@Preview(showBackground = true)
@Composable
fun PreviewColeccionistasScreen() {
    ColeccionistasScreen()
}
