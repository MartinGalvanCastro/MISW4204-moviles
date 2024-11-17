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
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.example.vinilosapp.steps.ComposeRuleHolder
import javax.inject.Inject

class ColeccionistaScreenPage @Inject constructor(
    composeRuleHolder: ComposeRuleHolder,
) {

    private val composeTestRule = composeRuleHolder.composeRule

    private val filterTextFieldTag = "filterTextField"
    private val collectorListTag = "collectorList"
    private val collectorItemTag = "collectorItem"
    private val itemCardTitleTag = "itemCardTitle"
    private val itemCardDescriptionTag = "itemCardDescription"
    private val itemCardFooterTag = "itemCardFooter"

    fun assertCollectorListIsDisplayed(): Boolean {
        return try {
            composeTestRule.onNodeWithTag(collectorListTag)
                .assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertAllCollectorsHaveDetails(): Boolean {
        composeTestRule.onRoot().printToLog("TAG")
        return try {
            val collectorNodes = composeTestRule.onAllNodesWithTag(collectorItemTag)

            val collectorNodeList = collectorNodes.fetchSemanticsNodes()
            assert(collectorNodeList.isNotEmpty()) { "No collector items found in the list" }

            collectorNodeList.forEachIndexed { index, _ ->
                val collectorItem = collectorNodes[index]

                collectorItem.onChildren().filter(hasTestTag(itemCardTitleTag))
                    .assertCountEquals(1)

                collectorItem.onChildren().filter(hasTestTag(itemCardDescriptionTag))
                    .assertCountEquals(1)

                collectorItem.onChildren().filter(hasTestTag(itemCardFooterTag))
                    .assertCountEquals(1)
            }
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertCollectorsListIsFiltered(filterText: String): Boolean {
        return try {
            composeTestRule.onNode(
                hasText(filterText, substring = true, ignoreCase = true) and !hasSetTextAction(),
            ).assertExists("Text '$filterText' not found on the screen")
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }
}
