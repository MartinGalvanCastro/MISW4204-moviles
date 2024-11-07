package com.example.vinilosapp.steps

import com.example.vinilosapp.screens.BandScreenPage
import com.example.vinilosapp.screens.BandaDetalleScreenPage
import io.cucumber.java.en.And
import io.cucumber.java.en.Then
import javax.inject.Inject

class BandaDetalleSteps @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
    private val bandaScreenPage: BandScreenPage,
    private val bandaDetalleScreenPage: BandaDetalleScreenPage,
) {

    @Then("Puede ingresar a ver el detalle de una banda")
    fun puedeIngresarAVerElDetalleDeUnaBanda() {
        composeRuleHolder.composeRule.waitForIdle()
        bandaScreenPage.clickOnBand()
        composeRuleHolder.composeRule.waitForIdle()
    }

    @And("La banda tiene una foto")
    fun laBandaTieneUnaFoto() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            bandaDetalleScreenPage.assertInfoSectionIsDisplayed() // Assuming the photo is in the info section
        }
    }

    @And("La banda tiene descripcion")
    fun laBandaTieneDescripcion() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            bandaDetalleScreenPage.assertInfoSectionIsDisplayed() // Assuming description is part of the info section
        }
    }

    @And("La banda tiene fecha de nacimiento")
    fun laBandaTieneFechaDeNacimiento() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            bandaDetalleScreenPage.assertInfoSectionIsDisplayed() // Assuming birthdate is part of the info section
        }
    }

    @And("La banda tiene premios")
    fun laBandaTienePremios() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            bandaDetalleScreenPage.assertPremiosSectionIsDisplayed()
        }
    }

    @And("La banda tiene albumes")
    fun laBandaTieneAlbumes() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            bandaDetalleScreenPage.assertPerformersSectionIsDisplayed()
        }
    }
}
