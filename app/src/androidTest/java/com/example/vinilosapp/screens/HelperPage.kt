package com.example.vinilosapp.screens

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.vinilosapp.steps.ComposeRuleHolder
import javax.inject.Inject

class HelperPage @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
) {

    private fun clickNavbarItem(testTag: String) {
        composeRuleHolder.composeRule.onNodeWithTag(testTag).performClick()
    }

    fun enterFilterText(text: String, filterTag: String = "filterTextField") {
        composeRuleHolder.composeRule.onNodeWithTag(filterTag).performTextInput(text)
    }

    fun navigateToAlbumes() {
        clickNavbarItem("navbar-item-Albumes")
    }

    fun navigateToArtistas() {
        clickNavbarItem("navbar-item-Artistas")
    }

    fun navigateToBandas() {
        clickNavbarItem("navbar-item-Bandas")
    }

    fun navigateToColeccionistas() {
        clickNavbarItem("navbar-item-Coleccionistas")
    }
}
