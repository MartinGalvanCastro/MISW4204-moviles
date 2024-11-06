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

class BandScreenPage @Inject constructor(
    composeRuleHolder: ComposeRuleHolder,
) {

    private val composeRule = composeRuleHolder.composeRule

    private val bandGrid = composeRule.onNodeWithTag("bandGrid")
    private val bandItemTag = "bandItem"

    fun clickOnBand() {
        val bands = composeRule.onAllNodesWithTag(bandItemTag)

        if (bands.fetchSemanticsNodes().isNotEmpty()) {
            bands[0].performClick()
        } else {
            throw AssertionError("No bands found to click on")
        }
    }

    fun assertBandGridIsDisplayed(): Boolean {
        return try {
            bandGrid.assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertEachBandHasImageAndText(): Boolean {
        return try {
            val bandNodes = composeRule.onAllNodesWithTag(bandItemTag)

            val bandNodeList = bandNodes.fetchSemanticsNodes()
            assert(bandNodeList.isNotEmpty()) { "No band items found in the grid" }

            bandNodeList.forEachIndexed { index, _ ->

                val bandItem = bandNodes[index]

                bandItem.onChildren().filter(hasTestTag("ImageBox"))
                    .assertCountEquals(1)

                bandItem.onChildren().filter(hasTestTag("ImageText"))
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
