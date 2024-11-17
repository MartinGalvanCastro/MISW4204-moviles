package com.example.vinilosapp.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()

    val imageRequest = ImageRequest.Builder(context)
        .data("file:///android_asset/logo.svg") // Your SVG file path
        .build()

    AsyncImage(
        model = imageRequest,
        contentDescription = null,
        imageLoader = imageLoader,
        modifier = modifier
            .wrapContentSize()
            .fillMaxSize(),
        contentScale = ContentScale.Fit,
    )
}
