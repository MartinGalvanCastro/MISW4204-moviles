package com.example.vinilosapp.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.vinilosapp.MainActivity

class LoginScreenPage(val composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {

    // Test tags
    private val appLogoTag = "AppLogo"
    private val loginPromptTag = "LoginPrompt"
    private val invitadoButtonTag = "InvitadoButton"

    /**
     * Asserts that the login prompt text is displayed.
     */
    fun assertLoginPromptIsDisplayed() {
        composeRule.onNodeWithTag(loginPromptTag).assertIsDisplayed()
    }

    /**
     * Asserts that the app logo is displayed.
     */
    fun assertAppLogoIsDisplayed() {
        composeRule.onNodeWithTag(appLogoTag).assertIsDisplayed()
    }

    fun assertInvitadoButtonIsDisplayed() {
        composeRule.onNodeWithTag(appLogoTag).assertIsDisplayed()
    }

    /**
     * Clicks the "Invitado" button to login as a guest.
     */
    fun clickInvitadoButton() {
        composeRule.onNodeWithTag(invitadoButtonTag).performClick()
    }
}
