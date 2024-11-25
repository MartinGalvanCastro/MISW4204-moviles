package com.example.vinilosapp.screens

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.vinilosapp.steps.ComposeRuleHolder
import java.util.logging.Level
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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

    private val generatedNames = mutableMapOf<String, String>()

    fun saveGeneratedName(entity: String, name: String) {
        generatedNames[entity] = name
    }

    fun getGeneratedName(entity: String): String {
        java.util.logging.Logger.getAnonymousLogger().log(Level.INFO, "${generatedNames.keys}")
        return generatedNames[entity] ?: throw IllegalStateException("$entity name was not set")
    }
}
