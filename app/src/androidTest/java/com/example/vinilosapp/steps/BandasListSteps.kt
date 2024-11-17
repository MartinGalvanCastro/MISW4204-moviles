package com.example.vinilosapp.steps

import com.example.vinilosapp.screens.BandScreenPage
import com.example.vinilosapp.screens.HelperPage
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import javax.inject.Inject

class BandasListSteps @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
    private val bandScreenPage: BandScreenPage,
    private val helperPage: HelperPage,
) {

    @When("Navega al menu de bandas")
    fun navegaAlMenuDeBandas() {
        composeRuleHolder.composeRule.waitForIdle()
        helperPage.navigateToBandas()
    }

    @Then("Puede ver el listado de bandas")
    fun puedeVerListadoDeBandas() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            bandScreenPage.assertBandGridIsDisplayed()
        }
    }

    @Then("Cada banda tiene su nombre, su foto")
    fun cadaBandaTieneNombreYFoto() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
            bandScreenPage.assertEachBandHasImageAndText()
        }
    }

    @Then("Solo puede ver las bandas que contengan la palabra {string}")
    fun soloPuedeVerLasBandasConNombre(bandName: String) {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
            bandScreenPage.assertTextIsDisplayed(bandName)
        }
    }
}
