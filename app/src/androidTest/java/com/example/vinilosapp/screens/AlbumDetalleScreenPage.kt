package com.example.vinilosapp.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import com.example.vinilosapp.steps.ComposeRuleHolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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

    fun clickAddSong(): Boolean {
        return try {
            composeRule.onNodeWithTag("addSongButton").performClick()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertAlbumCoverIsDisplayed(): Boolean = scrollToAndAssert(albumCoverTag)

    fun assertAlbumDescriptionIsDisplayed(): Boolean = scrollToAndAssert(albumDescriptionTag)

    fun assertAlbumReleaseDateIsDisplayed(): Boolean = scrollToAndAssert(albumReleaseDateTag)

    fun assertAlbumLabelIsDisplayed(): Boolean = scrollToAndAssert(albumLabelTag)

    fun assertAlbumGenreIsDisplayed(): Boolean = scrollToAndAssert(albumGenreTag)

    fun assertTracksSectionIsDisplayed(): Boolean = scrollToAndAssert(tracksSectionTag)

    fun assertPerformersSectionIsDisplayed(): Boolean = scrollToAndAssert(performersSectionTag)

    fun assertCommentsSectionIsDisplayed(): Boolean {
        return try {
            composeRule.onNode(hasScrollToIndexAction())
                .performScrollToNode(hasTestTag(commentsSectionTag))
            composeRule.onNodeWithTag(commentsSectionTag, useUnmergedTree = true)
                .assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertSongIsInList(text: String): Boolean {
        return try {
            composeRule.onNodeWithText(text, substring = true).assertExists()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }
}
