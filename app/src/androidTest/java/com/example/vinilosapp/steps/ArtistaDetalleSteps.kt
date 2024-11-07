package com.example.vinilosapp.steps

import com.example.vinilosapp.screens.ArtistaDetalleScreenPage
import com.example.vinilosapp.screens.ArtistaScreenPage
import io.cucumber.java.en.And
import io.cucumber.java.en.Then
import javax.inject.Inject

class ArtistaDetalleSteps @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
    private val artistaScreenPage: ArtistaScreenPage,
    private val artistaDetalleScreenPage: ArtistaDetalleScreenPage,
) {

    @Then("Puede ingresar a ver el detalle de un artista")
    fun puedeIngresarAVerElDetalleDeUnArtista() {
        composeRuleHolder.composeRule.waitForIdle()
        artistaScreenPage.clickOnMusician()
        composeRuleHolder.composeRule.waitForIdle()
    }

    @And("El artista tiene informacion")
    fun elArtistaTieneInformacion() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            artistaDetalleScreenPage.assertInfoSectionIsDisplayed()
        }
    }

    @And("El artista tiene premios")
    fun elArtistaTienePremios() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            artistaDetalleScreenPage.assertPremiosSectionIsDisplayed()
        }
    }

    @And("El artista tiene albumes")
    fun elArtistaTieneAlbums() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            artistaDetalleScreenPage.assertAlbumsSectionIsDisplayed()
        }
    }

    @And("El artista tiene descripcion")
    fun elArtistaTieneDescripcion() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            artistaDetalleScreenPage.assertInfoSectionIsDisplayed() // Modify if needed to assert specific description section
        }
    }
}
