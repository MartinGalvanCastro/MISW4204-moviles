package com.example.vinilosapp.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.vinilosapp.steps.ComposeRuleHolder
import com.example.vinilosapp.ui.screens.SongScreenTestTags
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddSongScreenPage @Inject constructor(
    composeRuleHolder: ComposeRuleHolder,
) {

    private val composeRule = composeRuleHolder.composeRule

    fun enterSongName(name: String): Boolean {
        return try {
            composeRule.onNodeWithTag(SongScreenTestTags.SONG_NAME_TEXT_FIELD)
                .assertExists()
                .assertIsDisplayed()
                .performTextInput(name)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun enterDuration(duration: String) {
        composeRule.onNodeWithTag(SongScreenTestTags.DURATION_TEXT_FIELD)
            .assertExists()
            .assertIsDisplayed()
            .performTextInput(duration)
    }

    fun clickSubmit() {
        composeRule.onNodeWithTag(SongScreenTestTags.SUBMIT_BUTTON)
            .assertExists()
            .assertIsDisplayed()
            .performClick()
    }

    fun clickCancel() {
        composeRule.onNodeWithTag(SongScreenTestTags.CANCEL_BUTTON)
            .assertExists()
            .assertIsDisplayed()
            .performClick()
    }

    fun assertSnackbarMessageDisplayed(message: String) {
        composeRule.onNodeWithText(message)
            .assertExists()
            .assertIsDisplayed()
    }
}
