package com.example.vinilosapp.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollTo
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
    private val errorMessageTag = "errorMessage"
    private val loadingMessageTag = "loadingMessage"
    private val collectorEmailTag = "collectorEmail"
    private val collectorPhoneTag = "collectorPhone"

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

    fun assertTopBarTitleIsDisplayed(): Boolean =
        composeRule.onNodeWithTag(topBarTitleTag, useUnmergedTree = true)
            .assertIsDisplayed()
            .let { true }

    fun assertInfoSectionIsDisplayed(): Boolean = scrollToAndAssert(infoSectionTag)

    fun assertAlbumSectionIsDisplayed(): Boolean = scrollToAndAssert(albumSectionTag)

    fun assertArtistasSectionIsDisplayed(): Boolean = scrollToAndAssert(artistasSectionTag)

    fun assertCommentSectionIsDisplayed(): Boolean = scrollToAndAssert(commentSectionTag)

    fun assertCollectorEmailIsDisplayed(): Boolean =
        composeRule.onNodeWithTag(collectorEmailTag, useUnmergedTree = true)
            .assertIsDisplayed()
            .let { true }

    fun assertCollectorPhoneIsDisplayed(): Boolean =
        composeRule.onNodeWithTag(collectorPhoneTag, useUnmergedTree = true)
            .assertIsDisplayed()
            .let { true }

    fun assertErrorMessageIsDisplayed(): Boolean =
        composeRule.onNodeWithTag(errorMessageTag, useUnmergedTree = true)
            .assertIsDisplayed()
            .let { true }

    fun assertLoadingMessageIsDisplayed(): Boolean =
        composeRule.onNodeWithTag(loadingMessageTag, useUnmergedTree = true)
            .assertIsDisplayed()
            .let { true }
}
