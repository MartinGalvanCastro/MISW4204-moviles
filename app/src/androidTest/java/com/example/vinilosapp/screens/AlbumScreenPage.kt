package com.example.vinilosapp.screens

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.vinilosapp.steps.ComposeRuleHolder
import javax.inject.Inject

class AlbumScreenPage @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
) {

    private val composeRule = composeRuleHolder.composeRule

    // Elements
    private val filterTextField = composeRule.onNodeWithTag("filterTextField")
    private val loadingMessage = composeRule.onNodeWithTag("loadingMessage")
    private val errorMessage = composeRule.onNodeWithTag("errorMessage")
    private val albumGrid = composeRule.onNodeWithTag("albumGrid")

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

    fun assertAlbumGridIsDisplayed(): Boolean {
        return try {
            albumGrid.assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            false
        }
    }

    fun assertEachAlbumHasImageAndText(): Boolean {
        return try {
            val albumNodes = composeRule.onAllNodesWithTag("albumItem")

            val albumNodeList = albumNodes.fetchSemanticsNodes()
            assert(albumNodeList.isNotEmpty()) { "No album items found in the grid" }

            albumNodeList.forEach { node ->
                val albumTestTag = node.config[SemanticsProperties.TestTag]

                val albumItem = composeRule.onNodeWithTag(albumTestTag ?: "")

                val image = albumItem.onChildren().filter(hasTestTag("ImageBox"))
                assert(image.fetchSemanticsNodes().isNotEmpty()) { "Album image is missing" }
                val text = albumItem.onChildren().filter(hasTestTag("ImageText"))
                assert(text.fetchSemanticsNodes().isNotEmpty()) { "Album text is missing" }
            }
            true
        } catch (e: AssertionError) {
            false
        }
    }

    fun assertTextIsDisplayed(text: String) {
        composeRule.onNodeWithText(text).assertExists("Text '$text' not found on the screen")
    }
}
