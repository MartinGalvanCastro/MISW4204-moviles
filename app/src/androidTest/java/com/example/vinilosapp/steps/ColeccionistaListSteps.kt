package com.example.vinilosapp.steps

import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.example.vinilosapp.screens.ColeccionistaScreenPage
import com.example.vinilosapp.screens.HelperPage
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import javax.inject.Inject

class ColeccionistaListSteps @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
    private val coleccionistaScreenPage: ColeccionistaScreenPage,
    private val helperPage: HelperPage,
) {

    @When("Navega al menu de coleccionistas")
    fun navega_al_menu_de_coleccionistas() {
        helperPage.navigateToColeccionistas()
    }

    @Then("Puede ver el listado de coleccionistas")
    fun puedeVerElListadoDeColeccionistas() {
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
            coleccionistaScreenPage.assertCollectorListIsDisplayed()
        }
    }

    @Then("Cada coleccionista tiene su nombre, su correo y su telefono")
    fun cadaColeccionistaTieneSusDatos() {
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
            composeRuleHolder.composeRule.onRoot(useUnmergedTree = true).printToLog("TAG")
            coleccionistaScreenPage.assertAllCollectorsHaveDetails()
        }
    }

    @Then("Solo puede ver los coleccionistas que contengan la palabra {string}")
    fun soloPuedeVerLosColeccionistasConPalabra(word: String) {
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
            coleccionistaScreenPage.assertCollectorsListIsFiltered(word)
        }
    }
}
