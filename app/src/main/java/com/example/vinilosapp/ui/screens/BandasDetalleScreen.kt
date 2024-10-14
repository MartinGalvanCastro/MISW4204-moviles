package com.example.vinilosapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.vinilosapp.ui.components.ScreenSkeleton

@Composable
fun BandasDetalleScreen() {
    ScreenSkeleton(screenName = "Bandas Detalle")
}

@Preview(showBackground = true)
@Composable
fun PreviewBandasDetalleScreen() {
    BandasDetalleScreen()
}
