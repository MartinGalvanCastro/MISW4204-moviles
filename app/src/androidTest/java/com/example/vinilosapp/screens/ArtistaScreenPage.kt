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
import com.example.vinilosapp.steps.ComposeRuleHolder
import javax.inject.Inject

class ArtistaScreenPage @Inject constructor(
    composeRuleHolder: ComposeRuleHolder,
) {

    private val composeRule = composeRuleHolder.composeRule

    private val musicianGrid = composeRule.onNodeWithTag("musicianGrid")
    private val musicianItemTag = "musicianItem"

    fun clickOnMusician() {
        val musicians = composeRule.onAllNodesWithTag(musicianItemTag)

        if (musicians.fetchSemanticsNodes().isNotEmpty()) {
            musicians[0].performClick()
        } else {
            throw AssertionError("No musicians found to click on")
        }
    }

    fun assertMusicianGridIsDisplayed(): Boolean {
        return try {
            musicianGrid.assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertEachMusicianHasImageAndText(): Boolean {
        return try {
            val musicianNodes = composeRule.onAllNodesWithTag(musicianItemTag)

            val musicianNodeList = musicianNodes.fetchSemanticsNodes()
            assert(musicianNodeList.isNotEmpty()) { "No musician items found in the grid" }

            musicianNodeList.forEachIndexed { index, _ ->

                val musicianItem = musicianNodes[index]

                musicianItem.onChildren().filter(hasTestTag("ImageBox"))
                    .assertCountEquals(1)

                musicianItem.onChildren().filter(hasTestTag("ImageText"))
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
                hasText(text, substring = true, ignoreCase = true) and !hasSetTextAction(),
            ).assertExists("Text '$text' not found on the screen")
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }
}
