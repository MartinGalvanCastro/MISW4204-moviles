package com.example.vinilosapp.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.vinilosapp.R

enum class ImageShape {
    CIRCULO,
    CUADRADO,
}

@Composable
fun ImageAndText(
    imageShape: ImageShape = ImageShape.CUADRADO,
    imageUrl: String,
    imageText: String,
    textAlign: TextAlign = TextAlign.Start,
    onSelect: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val shapeModifier = when (imageShape) {
        ImageShape.CIRCULO ->
            Modifier
                .size(100.dp)
                .shadow(12.dp, shape = CircleShape)
                .background(Color.Transparent)
                .clip(CircleShape)
        ImageShape.CUADRADO ->
            Modifier
                .size(165.dp, 150.dp)
                .shadow(12.dp, RoundedCornerShape(10.dp))
                .background(Color.Transparent)
                .clip(RoundedCornerShape(10.dp))
    }

    var isPressed by remember { mutableStateOf(false) }
    val defaultImage = rememberAsyncImagePainter(model = R.drawable.default_album_image_background)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(8.dp),
    ) {
        Box(
            modifier = Modifier
                .clip(
                    if (imageShape == ImageShape.CUADRADO) {
                        RoundedCornerShape(10.dp)
                    } else {
                        CircleShape
                    },
                )
                .testTag("ImageBox"),
        ) {
            AsyncImage(
                model = imageUrl,
                placeholder = defaultImage,
                error = defaultImage,
                onError = { state ->
                    state.result.throwable.message?.let { Log.e("ImageAndText", it) }
                    state.result.throwable.printStackTrace()
                },
                contentDescription = null,
                modifier = Modifier.then(shapeModifier)
                    .pointerInput(onSelect) {
                        onSelect?.let {
                            detectTapGestures(
                                onPress = {
                                    isPressed = true
                                    try {
                                        awaitRelease()
                                        isPressed = false
                                        it()
                                    } catch (e: Exception) {
                                        isPressed = false
                                    }
                                },
                            )
                        }
                    },
                contentScale = ContentScale.Crop,
            )

            if (isPressed) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .testTag("ImageOverlay"),
                )
            }
        }

        Text(
            text = imageText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .widthIn(max = 100.dp)
                .testTag("ImageText"),
            textAlign = textAlign,
            minLines = when (imageShape) {
                ImageShape.CIRCULO -> 2
                ImageShape.CUADRADO -> 1
            },
            maxLines = when (imageShape) {
                ImageShape.CIRCULO -> 2
                ImageShape.CUADRADO -> 1
            },
        )
    }
}
