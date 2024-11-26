package com.example.vinilosapp.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollTo
import com.example.vinilosapp.steps.ComposeRuleHolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BandaDetalleScreenPage @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
) {

    private val composeRule = composeRuleHolder.composeRule
    private val infoSectionTag = "infoSection"
    private val premiosSectionTag = "premiosSection"
    private val albumsSectionTag = "albumsSection"

    private fun scrollToAndAssert(tag: String): Boolean {
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
