package com.example.vinilosapp.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.vinilosapp.steps.ComposeRuleHolder
import javax.inject.Inject

class AlbumDetalleScreenPage @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
) {

    private val composeRule = composeRuleHolder.composeRule
    private val albumDetailTag = "albumDetail"
    private val albumCoverTag = "Detail_Cover_Image"
    private val albumDescriptionTag = "albumDescription"
    private val albumReleaseDateTag = "albumReleaseDate"
    private val albumLabelTag = "albumLabel"
    private val albumGenreTag = "albumGenre"
    private val tracksSectionTag = "tracksSection"
    private val performersSectionTag = "performersSection"
    private val commentsSectionTag = "commentsSection"

    fun assertAlbumDetailIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(albumDetailTag).assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            false
        }
    }

    fun assertAlbumCoverIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(albumCoverTag).assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            false
        }
    }

    fun assertAlbumDescriptionIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(albumDescriptionTag).assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            false
        }
    }

    fun assertAlbumReleaseDateIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(albumReleaseDateTag).assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            false
        }
    }

    fun assertAlbumLabelIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(albumLabelTag).assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            false
        }
    }

    fun assertAlbumGenreIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(albumGenreTag).assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            false
        }
    }

    fun assertTracksSectionIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(tracksSectionTag).assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            false
        }
    }

    fun assertPerformersSectionIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(performersSectionTag).assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            false
        }
    }

    fun assertCommentsSectionIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithTag(commentsSectionTag).assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            false
        }
    }
}
