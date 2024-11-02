package com.example.vinilosapp.pageobject

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput

class AlbumScreenPage(private val composeTestRule: ComposeTestRule) {

    // Elements
    private val filterTextField = composeTestRule.onNodeWithTag("filterTextField")
    private val loadingMessage = composeTestRule.onNodeWithTag("loadingMessage")
    private val errorMessage = composeTestRule.onNodeWithTag("errorMessage")
    private val albumGrid = composeTestRule.onNodeWithTag("albumGrid")

    // Actions
    fun enterFilterText(text: String) {
        filterTextField.performTextInput(text)
    }

    // Verifications
    fun assertLoadingIsDisplayed() {
        loadingMessage.assertIsDisplayed()
    }

    fun assertErrorIsDisplayed(expectedMessage: String) {
        errorMessage.assertIsDisplayed()
        errorMessage.assertTextEquals(expectedMessage)
    }

    fun assertAlbumGridIsDisplayed() {
        albumGrid.assertIsDisplayed()
    }

    fun assertEachAlbumHasImageAndText() {
        val albumNodes = composeTestRule.onAllNodesWithTag("albumItem_", useUnmergedTree = true)

        val albumNodeList = albumNodes.fetchSemanticsNodes()
        assert(albumNodeList.isNotEmpty()) { "No album items found in the grid" }

        albumNodeList.forEach { node ->
            val albumTestTag = node.config[SemanticsProperties.TestTag]

            val albumItem = composeTestRule.onNodeWithTag(albumTestTag ?: "")

            val image = albumItem.onChildren().filter(hasTestTag("ImageBox"))
            assert(image.fetchSemanticsNodes().isNotEmpty()) { "Album image is missing" }
            val text = albumItem.onChildren().filter(hasTestTag("ImageText"))
            assert(text.fetchSemanticsNodes().isNotEmpty()) { "Album text is missing" }
        }
    }

    fun assertTextIsDisplayed(text: String) {
        composeTestRule.onNodeWithText(text).assertExists("Text '$text' not found on the screen")
    }
}
