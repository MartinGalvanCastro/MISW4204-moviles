package com.example.vinilosapp.steps

import com.example.vinilosapp.screens.ColeccionistaScreenPage
import com.example.vinilosapp.screens.ColeccionistasDetalleScreenPage
import io.cucumber.java.en.And
import io.cucumber.java.en.Then
import javax.inject.Inject

class ColeccionistaDetalleSteps @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
    private val coleccionistaScreenPage: ColeccionistaScreenPage,
    private val coleccionistasDetalleScreenPage: ColeccionistasDetalleScreenPage,
) {

    @Then("Puede ingresar a ver el detalle de un coleccionista")
    fun puedeIngresarAVerElDetalleDeUnaBanda() {
        composeRuleHolder.composeRule.waitForIdle()
        coleccionistaScreenPage.clickOnCollector()
        composeRuleHolder.composeRule.waitForIdle()
    }

    @And("El coleccionista tiene un correo")
    fun elColeccionistaTieneUnCorreo() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            coleccionistasDetalleScreenPage.assertCollectorEmailIsDisplayed()
        }
    }

    @And("El coleccionista tiene un telefono")
    fun elColeccionistaTieneUnTelefono() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            coleccionistasDetalleScreenPage.assertCollectorPhoneIsDisplayed()
        }
    }

    @And("El coleccionista tiene albumes")
    fun elColeccionistaTieneAlbumes() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
            coleccionistasDetalleScreenPage.assertAlbumSectionIsDisplayed()
        }
    }

    @And("El coleccionista tiene artistas favoritos")
    fun elColeccionistaTieneArtistasFavoritos() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            coleccionistasDetalleScreenPage.assertArtistasSectionIsDisplayed()
        }
    }
}
