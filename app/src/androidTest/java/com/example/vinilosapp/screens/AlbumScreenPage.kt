package com.example.vinilosapp.screens

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.vinilosapp.steps.ComposeRuleHolder
import javax.inject.Inject

class AlbumScreenPage @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
) {

    private val composeRule = composeRuleHolder.composeRule

    private val filterTextField = composeRule.onNodeWithTag("filterTextField")
    private val albumGrid = composeRule.onNodeWithTag("albumGrid")
    private val albumItemTag = "albumItem"

    fun enterFilterText(text: String) {
        filterTextField.performTextInput(text)
    }

    fun clickOnAlbum() {
        val albums = composeRule.onAllNodesWithTag(albumItemTag)

        if (albums.fetchSemanticsNodes().isNotEmpty()) {
            albums[0].performClick()
        } else {
            throw AssertionError("No albums found to click on")
        }
    }

    fun assertAlbumGridIsDisplayed(): Boolean {
        return try {
            albumGrid.assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertEachAlbumHasImageAndText(): Boolean {
        return try {
            val albumNodes = composeRule.onAllNodesWithTag("albumItem")

            val albumNodeList = albumNodes.fetchSemanticsNodes()
            assert(albumNodeList.isNotEmpty()) { "No album items found in the grid" }

            albumNodeList.forEachIndexed { index, _ ->

                val albumItem = albumNodes[index]

                albumItem.onChildren().filter(hasTestTag("ImageBox"))
                    .assertCountEquals(1)

                albumItem.onChildren().filter(hasTestTag("ImageText"))
                    .assertCountEquals(1)
            }
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertTextIsDisplayed(text: String): Boolean {
        return try {
            composeRule.onNode(
                hasText("Buscando", substring = true, ignoreCase = true) and !hasSetTextAction(),
            ).assertExists("Text 'Buscando' not found on the screen")
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            return false
        }
    }
}
