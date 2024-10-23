package com.example.vinilosapp.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class ScrollableGridKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun gridLayout_displaysItemsCorrectly() {
        val testItems = listOf("Item 1", "Item 2", "Item 3", "Item 4")

        composeTestRule.setContent {
            GridLayout(items = testItems)
        }

        for (item in testItems) {
            composeTestRule.onNodeWithText(item).assertIsDisplayed()
        }

        val displayedItems = composeTestRule.onAllNodesWithText("Item", substring = true).fetchSemanticsNodes()
        assert(displayedItems.size == testItems.size)
    }
}
