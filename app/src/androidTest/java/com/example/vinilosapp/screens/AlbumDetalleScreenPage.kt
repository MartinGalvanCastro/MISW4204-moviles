package com.example.vinilosapp.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import com.example.vinilosapp.steps.ComposeRuleHolder
import javax.inject.Inject

class AlbumDetalleScreenPage @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
) {

    private val composeRule = composeRuleHolder.composeRule
    private val albumCoverTag = "Detail_Cover_Image"
    private val albumDescriptionTag = "albumDescription"
    private val albumReleaseDateLabel = "Fecha de Publicacion"
    private val albumLabelTag = "Disquera"
    private val albumGenreTag = "Genero"
    private val tracksSectionTag = "Canciones"
    private val performersSectionTag = "Artistas"
    private val commentsSectionTag = "Comentarios"

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
            composeRule.onNodeWithText(albumReleaseDateLabel, useUnmergedTree = true, substring = true, ignoreCase = true).performScrollTo().assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertAlbumLabelIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithText(albumLabelTag, useUnmergedTree = true, substring = true, ignoreCase = true).performScrollTo().assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertAlbumGenreIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithText(albumGenreTag, useUnmergedTree = true, substring = true, ignoreCase = true).performScrollTo().assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertTracksSectionIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithText(tracksSectionTag, useUnmergedTree = true).performScrollTo().assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertPerformersSectionIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithText(performersSectionTag, useUnmergedTree = true).performScrollTo().assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }

    fun assertCommentsSectionIsDisplayed(): Boolean {
        return try {
            composeRule.onNodeWithText(commentsSectionTag, useUnmergedTree = true).performScrollTo().assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            e.printStackTrace()
            false
        }
    }
}
