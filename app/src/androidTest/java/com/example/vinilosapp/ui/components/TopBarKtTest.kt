package com.example.vinilosapp.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class TopBarKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun topBar_displaysTitleCorrectly() {
        composeTestRule.setContent {
            TopBar()
        }

        composeTestRule
            .onNodeWithTag("title")
            .assertIsDisplayed()
            .assertTextEquals("Vinilos App")
    }

    @Test
    fun topBar_callsOnBackClick_whenBackButtonIsClicked() {
        val mockOnBackClick = mock<() -> Unit>()

        composeTestRule.setContent {
            TopBar(onBackClick = mockOnBackClick)
        }

        composeTestRule
            .onNodeWithTag("backButton", useUnmergedTree = true)
            .performClick()

        verify(mockOnBackClick).invoke()
    }
}
