package com.example.vinilosapp.pageobject

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag

class AlbumDetalleScreenPage(private val composeTestRule: ComposeTestRule) {

    // Elements
    private val topBarTitle = composeTestRule.onNodeWithTag("topBarTitle")
    private val loadingMessage = composeTestRule.onNodeWithTag("loadingMessage")
    private val errorMessage = composeTestRule.onNodeWithTag("errorMessage")
    private val infoSection = composeTestRule.onNodeWithTag("infoSection")
    private val tracksSection = composeTestRule.onNodeWithTag("tracksSection")
    private val performersSection = composeTestRule.onNodeWithTag("performersSection")
    private val commentsSection = composeTestRule.onNodeWithTag("commentsSection")

    // Verifications
    fun assertTopBarTitleIsDisplayed(expectedTitle: String) {
        topBarTitle.assertIsDisplayed()
        topBarTitle.assertTextEquals(expectedTitle)
    }

    fun assertLoadingIsDisplayed() {
        loadingMessage.assertIsDisplayed()
    }

    fun assertErrorMessageIsDisplayed(expectedMessage: String) {
        errorMessage.assertIsDisplayed()
        errorMessage.assertTextEquals(expectedMessage)
    }

    fun assertInfoSectionIsDisplayed() {
        infoSection.assertIsDisplayed()
    }

    fun assertTracksSectionIsDisplayed() {
        tracksSection.assertIsDisplayed()
    }

    fun assertPerformersSectionIsDisplayed() {
        performersSection.assertIsDisplayed()
    }

    fun assertCommentsSectionIsDisplayed() {
        commentsSection.assertIsDisplayed()
    }
}
