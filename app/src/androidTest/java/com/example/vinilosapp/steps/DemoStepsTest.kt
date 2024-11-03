package com.example.vinilosapp.steps

import com.example.vinilosapp.screens.LoginScreenPage
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import javax.inject.Inject

@HiltAndroidTest
class DemoStepsTest @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
    private val loginScreenPage: LoginScreenPage,
) {

    @When("La app se inicializa")
    fun termine_de_cargar() {
        composeRuleHolder.composeRule.waitForIdle()
    }

    @Then("Puedo ver el logo de la app")
    fun puedo_ver_el_logo_de_la_app() {
        loginScreenPage.assertAppLogoIsDisplayed()
    }
}
