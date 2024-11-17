package com.example.vinilosapp.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.printToLog
import com.example.vinilosapp.steps.ComposeRuleHolder
import javax.inject.Inject

class BandaDetalleScreenPage @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
) {

    private val composeRule = composeRuleHolder.composeRule
    private val infoSectionTag = "infoSection"
    private val premiosSectionTag = "premiosSection"
    private val albumsSectionTag = "albumsSection"

    // Debugging Utility
    private fun printDebugLogs() {
        composeRule.onRoot().printToLog("TAG")
    }

    // General scroll and assert utility for all sections
    private fun scrollToAndAssert(tag: String): Boolean {
        printDebugLogs() // Log node hierarchy for debugging
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

    fun assertInfoSectionIsDisplayed(): Boolean = scrollToAndAssert(infoSectionTag)

    fun assertPremiosSectionIsDisplayed(): Boolean = scrollToAndAssert(premiosSectionTag)

    fun assertAlbumsSectionIsDisplayed(): Boolean = scrollToAndAssert(albumsSectionTag)
}
