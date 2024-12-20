package com.example.vinilosapp.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.vinilosapp.steps.ComposeRuleHolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginScreenPage@Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
) {

    private val composeRule = composeRuleHolder.composeRule

    private val appLogoTag = "AppLogo"

    private val invitadoButtonTag = "InvitadoButton"
    private val coleccionistaButtonTag = "coleccionistaButton"

    /**
     * Asserts that the app logo is displayed.
     */
    fun assertAppLogoIsDisplayed() {
        composeRule.onNodeWithTag(appLogoTag).assertIsDisplayed()
    }

    /**
     * Clicks the "Invitado" button to login as a guest.
     */
    fun clickInvitadoButton() {
        composeRule.onNodeWithTag(invitadoButtonTag).performClick()
    }

    fun clickColeccionistaButton() {
        composeRule.onNodeWithTag(coleccionistaButtonTag).performClick()
    }
}
