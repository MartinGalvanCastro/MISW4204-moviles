package com.example.vinilosapp.steps

import com.example.vinilosapp.screens.ArtistaScreenPage
import com.example.vinilosapp.screens.HelperPage
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import javax.inject.Inject

class ArtistasListSteps @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
    private val artistaScreenPage: ArtistaScreenPage,
    private val helperPage: HelperPage,
) {

    @When("Navega al menu de artistas")
    fun navegaAlMenuDeArtistas() {
        composeRuleHolder.composeRule.waitForIdle()
        helperPage.navigateToArtistas()
    }

    @Then("Puede ver el listado de artistas")
    fun puedeVerListadoDeArtistas() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            artistaScreenPage.assertMusicianGridIsDisplayed()
        }
    }

    @Then("Cada artista tiene su nombre y su foto")
    fun cadaArtistaTieneNombreYFoto() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
            artistaScreenPage.assertEachMusicianHasImageAndText()
        }
    }

    @Then("Solo puede ver los artistas que contengan la palabra {string}")
    fun soloPuedeVerLosArtistasConNombre(artistaName: String) {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            artistaScreenPage.assertTextIsDisplayed(artistaName)
        }
    }
}
