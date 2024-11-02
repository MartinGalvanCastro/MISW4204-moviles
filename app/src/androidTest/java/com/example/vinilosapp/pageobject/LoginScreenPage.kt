package com.example.vinilosapp.pageobject

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick

class LoginScreenPage(private val composeTestRule: ComposeTestRule) {

    private val logo = composeTestRule.onNodeWithTag("appLogo")
    private val loginInvitadoButton = composeTestRule.onNodeWithTag("loginInvitado")

    // Actions
    fun clickLoginAsGuest() {
        loginInvitadoButton.performClick()
    }

    // Verifications
    fun assertLogoIsDisplayed() {
        logo.assertIsDisplayed()
    }
}
