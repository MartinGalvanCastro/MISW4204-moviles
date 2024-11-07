package com.example.vinilosapp.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollTo
import com.example.vinilosapp.steps.ComposeRuleHolder
import javax.inject.Inject

class ArtistaDetalleScreenPage @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
) {

    private val composeRule = composeRuleHolder.composeRule
    private val infoSectionTag = "infoSection"
    private val premiosSectionTag = "premiosSection"
    private val albumsSectionTag = "albumsSection"
    private val errorMessageTag = "errorMessage"
    private val loadingMessageTag = "loadingMessage"

    fun assertInfoSectionIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(infoSectionTag, useUnmergedTree = true)
                .performScrollTo()
                .assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertPremiosSectionIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(premiosSectionTag, useUnmergedTree = true)
                .performScrollTo()
                .assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertAlbumsSectionIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(albumsSectionTag, useUnmergedTree = true)
                .performScrollTo()
                .assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertLoadingMessageIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(loadingMessageTag, useUnmergedTree = true)
                .performScrollTo()
                .assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertErrorMessageIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(errorMessageTag, useUnmergedTree = true)
                .performScrollTo()
                .assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }
}
