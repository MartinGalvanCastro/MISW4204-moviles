package com.example.vinilosapp.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.printToLog
import com.example.vinilosapp.steps.ComposeRuleHolder
import javax.inject.Inject

class AlbumDetalleScreenPage @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
) {

    private val composeRule = composeRuleHolder.composeRule
    private val albumCoverTag = "Detail_Cover_Image"
    private val albumDescriptionTag = "Description"
    private val albumReleaseDateTag = "albumReleaseDate"
    private val albumLabelTag = "albumLabel"
    private val albumGenreTag = "albumGenre"
    private val tracksSectionTag = "tracksSection"
    private val performersSectionTag = "performersSection"
    private val commentsSectionTag = "commentsSection"

    fun assertAlbumCoverIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(albumCoverTag, useUnmergedTree = true).performScrollTo().assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertAlbumDescriptionIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(albumDescriptionTag, useUnmergedTree = true).performScrollTo().assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertAlbumReleaseDateIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(albumReleaseDateTag, useUnmergedTree = true).performScrollTo().assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertAlbumLabelIsDisplayed(): Boolean {
        return try {
            composeRule.onRoot().printToLog("TAG")
            composeRule.onNodeWithTag(albumLabelTag, useUnmergedTree = true).performScrollTo().assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertAlbumGenreIsDisplayed(): Boolean {
        return try {
            composeRule.onRoot().printToLog("TAG")
            composeRule.onNodeWithTag(albumGenreTag, useUnmergedTree = true).performScrollTo().assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertTracksSectionIsDisplayed(): Boolean {
        return try {
            composeRule.onRoot().printToLog("TAG")
            composeRule.onNodeWithTag(tracksSectionTag, useUnmergedTree = true).performScrollTo().assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertPerformersSectionIsDisplayed(): Boolean {
        return try {
            composeRule.onRoot().printToLog("TAG")
            composeRule.onNodeWithTag(performersSectionTag, useUnmergedTree = true).performScrollTo().assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertCommentsSectionIsDisplayed(): Boolean {
        return try {
            composeRule.onRoot().printToLog("TAG")
            composeRule.onNodeWithTag(commentsSectionTag, useUnmergedTree = true).performScrollTo().assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }
}
