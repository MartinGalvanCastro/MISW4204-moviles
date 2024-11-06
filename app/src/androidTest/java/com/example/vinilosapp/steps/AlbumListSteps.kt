package com.example.vinilosapp.steps

import com.example.vinilosapp.screens.AlbumScreenPage
import io.cucumber.java.en.Then
import javax.inject.Inject

class AlbumListSteps @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
    private val albumScreenPage: AlbumScreenPage,
) {

    @Then("Puede ver el listado de albumes")
    fun puedeVerListadoAlbumes() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            albumScreenPage.assertAlbumGridIsDisplayed()
        }
    }

    @Then("Cada album tiene su nombre, su foto")
    fun cadaAlbumTieneNombreFotoYArtista() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
            albumScreenPage.assertEachAlbumHasImageAndText()
        }
    }

    @Then("Solo puede ver los albumes que contengan la palabra {string}")
    fun soloPuedeVerElAlbum(albumName: String) {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            albumScreenPage.assertTextIsDisplayed(albumName)
        }
    }
}
