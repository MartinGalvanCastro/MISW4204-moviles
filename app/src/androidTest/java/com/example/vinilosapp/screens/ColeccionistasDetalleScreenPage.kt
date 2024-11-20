package com.example.vinilosapp.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.printToLog
import com.example.vinilosapp.steps.ComposeRuleHolder
import javax.inject.Inject

class ColeccionistasDetalleScreenPage @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
) {
    private val composeRule = composeRuleHolder.composeRule

    private val infoSectionTag = "infoSection"
    private val albumSectionTag = "albumSection"
    private val artistasSectionTag = "artistasSection"
    private val commentSectionTag = "commentSection"
    private val topBarTitleTag = "topBarTitle"
    private val collectorEmailTag = "collectorEmail"
    private val collectorPhoneTag = "collectorPhone"

    private fun scrollToAndAssert(tag: String): Boolean {
        composeRule.onRoot().printToLog("TAG")
        return try {
            composeRule.onNodeWithTag(tag, useUnmergedTree = true)
                .performScrollTo()
                .assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertTopBarTitleIsDisplayed(): Boolean =
        composeRule.onNodeWithTag(topBarTitleTag, useUnmergedTree = true)
            .assertIsDisplayed()
            .let { true }

    fun assertInfoSectionIsDisplayed(): Boolean = scrollToAndAssert(infoSectionTag)

    fun assertAlbumSectionIsDisplayed(): Boolean = scrollToAndAssert(albumSectionTag)

    fun assertArtistasSectionIsDisplayed(): Boolean = scrollToAndAssert(artistasSectionTag)

    fun assertCommentSectionIsDisplayed(): Boolean = scrollToAndAssert(collectorEmailTag)

    fun assertCollectorEmailIsDisplayed(): Boolean = scrollToAndAssert(commentSectionTag)

    fun assertCollectorPhoneIsDisplayed(): Boolean = scrollToAndAssert(collectorPhoneTag)
}
