package com.example.vinilosapp.steps

import com.example.vinilosapp.screens.AlbumScreenPage
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
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

    @Then("Cada album tiene su nombre, su foto y el nombre del artista")
    fun cadaAlbumTieneNombreFotoYArtista() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
            albumScreenPage.assertEachAlbumHasImageAndText()
        }
    }

    @When("Ingresa la palabra {string}")
    fun ingresaLaPalabraEnFiltro(palabra: String) {
        composeRuleHolder.composeRule.waitForIdle()
        albumScreenPage.enterFilterText(palabra)
    }

    @Then("Solo puede ver el album {string}")
    fun soloPuedeVerElAlbum(albumName: String) {
        composeRuleHolder.composeRule.waitForIdle()
        albumScreenPage.assertTextIsDisplayed(albumName)
    }
}
