package com.example.vinilosapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.vinilosapp.ui.theme.VinilosAppTheme

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
) {
    var isPressed by remember { mutableStateOf(false) }

    val shapeModifier = when (imageShape) {
        ImageShape.CIRCULO ->
            Modifier
                .size(100.dp)
                .shadow(32.dp, CircleShape)
                .clip(CircleShape)
        ImageShape.CUADRADO ->
            Modifier
                .size(165.dp, 150.dp)
                .shadow(32.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp),
    ) {
        Box(
            modifier = Modifier
                .then(shapeModifier)
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
                }
                .testTag("ImageBox"),
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
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
                .padding(top = 4.dp),
            textAlign = textAlign,
        )
    }
}

@Composable
fun MockImageAndText(
    imageText: String,
) {
    ImageAndText(
        imageShape = ImageShape.CUADRADO,
        imageUrl = "https://picsum.photos/100",
        imageText = imageText,
        textAlign = TextAlign.Center,
        onSelect = { println("Circle Image Clicked") },
    )
}

@Preview(showBackground = true)
@Composable
fun ImageAndTextPreview() {
    VinilosAppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ImageAndText(
                imageShape = ImageShape.CIRCULO,
                imageUrl = "https://picsum.photos/100",
                imageText = "Banda (Circle)",
                textAlign = TextAlign.Center,
                onSelect = { println("Circle Image Clicked") },
            )

            ImageAndText(
                imageShape = ImageShape.CUADRADO,
                imageUrl = "https://picsum.photos/165/150",
                imageText = "Banda (Square)",
                textAlign = TextAlign.Center,
            )
        }
    }
}
