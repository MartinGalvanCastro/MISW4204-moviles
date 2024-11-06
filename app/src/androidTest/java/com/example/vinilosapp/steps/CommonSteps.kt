package com.example.vinilosapp.steps

import com.example.vinilosapp.screens.HelperPage
import com.example.vinilosapp.screens.LoginScreenPage
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.Before
import io.cucumber.java.en.When
import javax.inject.Inject

@HiltAndroidTest
class CommonSteps @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
    private val loginScreenPage: LoginScreenPage,
    private val helperPage: HelperPage,
) {

    @Before(order = 1)
    fun setupComposeTestRule() {
        composeRuleHolder.composeRule.waitForIdle()
    }

    @When("Un usuario invitado ingresa a Vinilos App")
    fun invitadoIngresaAVinilosApp() {
        loginScreenPage.assertAppLogoIsDisplayed()
        loginScreenPage.clickInvitadoButton()
    }

    @When("Ingresa la palabra {string}")
    fun ingresaLaPalabraEnFiltro(palabra: String) {
        composeRuleHolder.composeRule.waitForIdle()
        helperPage.enterFilterText(palabra)
        composeRuleHolder.composeRule.waitForIdle()
    }
}
