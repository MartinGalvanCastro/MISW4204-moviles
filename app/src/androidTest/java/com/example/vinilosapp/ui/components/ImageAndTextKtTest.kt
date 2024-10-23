package com.example.vinilosapp.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

@RunWith(AndroidJUnit4::class)
class ImageAndTextKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun imageIsDisplayedWithCircleShapeTest() {
        composeTestRule.setContent {
            ImageAndText(
                imageShape = ImageShape.CIRCULO,
                imageUrl = "https://example.com/image.png",
                imageText = "Circle Image",
            )
        }

        composeTestRule.onNodeWithTag("ImageBox").assertExists()
    }

    @Test
    fun imageIsDisplayedWithSquareShapeTest() {
        composeTestRule.setContent {
            ImageAndText(
                imageShape = ImageShape.CUADRADO,
                imageUrl = "https://example.com/image.png",
                imageText = "Square Image",
            )
        }
        composeTestRule.onNodeWithTag("ImageBox").assertExists()
    }

    @Test
    fun onSelectIsInvokedWhenImageIsPressed() {
        val onSelectMock: () -> Unit = mock()

        composeTestRule.setContent {
            ImageAndText(
                imageShape = ImageShape.CUADRADO,
                imageUrl = "https://example.com/image.png",
                imageText = "Clickable Image",
                onSelect = onSelectMock,
            )
        }

        composeTestRule.onNodeWithTag("ImageBox").performClick()
        verify(onSelectMock).invoke()
    }

    @Test
    fun overlayAppearsWhenImageIsPressed() {
        val onSelectMock: () -> Unit = mock()

        composeTestRule.setContent {
            ImageAndText(
                imageShape = ImageShape.CUADRADO,
                imageUrl = "https://example.com/image.png",
                imageText = "Press Image",
                onSelect = onSelectMock,
            )
        }

        composeTestRule.onNodeWithTag("ImageBox").performTouchInput { down(center) }

        composeTestRule.onNodeWithTag("ImageOverlay")
            .assertExists()

        composeTestRule.onNodeWithTag("ImageBox").performTouchInput { up() }

        composeTestRule.onNodeWithTag("ImageOverlay")
            .assertDoesNotExist()
    }
}
