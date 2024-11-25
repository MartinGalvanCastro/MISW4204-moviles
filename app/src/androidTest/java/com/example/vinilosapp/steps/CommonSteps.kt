package com.example.vinilosapp.steps

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.vinilosapp.screens.HelperPage
import com.example.vinilosapp.screens.LoginScreenPage
import io.cucumber.java.Before
import io.cucumber.java.en.And
import io.cucumber.java.en.When
import javax.inject.Inject

class CommonSteps @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
    private val loginScreenPage: LoginScreenPage,
    private val helperPage: HelperPage,
) {

    @Before(order = 1)
    fun setupComposeTestRule() {
        composeRuleHolder.composeRule.waitForIdle()
    }

    @When("Un usuario {string} ingresa a Vinilos App")
    fun usuarioIngresaAVinilosApp(tipo: String) {
        loginScreenPage.assertAppLogoIsDisplayed()

        when (tipo) {
            "Invitado" -> loginScreenPage.clickInvitadoButton()
            "Coleccionista" -> loginScreenPage.clickColeccionistaButton()
            else -> throw IllegalArgumentException("Unsupported user type: $tipo")
        }
    }

    @When("Navega al menu de {string}")
    fun navegaAlMenu(menu: String) {
        composeRuleHolder.composeRule.waitForIdle()

        when (menu) {
            "artistas" -> helperPage.navigateToArtistas()
            "bandas" -> helperPage.navigateToBandas()
            "coleccionistas" -> helperPage.navigateToColeccionistas()
            "albumes" -> {}
            else -> throw IllegalArgumentException("Unsupported menu: $menu")
        }

        composeRuleHolder.composeRule.waitForIdle()
    }

    @When("Ingresa la palabra {string}")
    fun ingresaLaPalabraEnFiltro(palabra: String) {
        composeRuleHolder.composeRule.waitForIdle()
        helperPage.enterFilterText(palabra)
        composeRuleHolder.composeRule.waitForIdle()
    }

    @When("Un usuario <type> ingresa a Vinilos App")
    fun unUsuarioTypeIngresaAVinilosApp(tipo: String) {
        loginScreenPage.assertAppLogoIsDisplayed()

        when (tipo) {
            "Invitado" -> loginScreenPage.clickInvitadoButton()
            "Coleccionista" -> loginScreenPage.clickColeccionistaButton()
            else -> throw IllegalArgumentException("Unsupported user type: $tipo")
        }
    }

    @And("Navega a la vista anterior")
    fun navegaALaVistaAnterior() {
        composeRuleHolder.composeRule.onNodeWithTag("backButtonTag").performClick()
    }
}
