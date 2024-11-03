package com.example.vinilosapp.steps

import android.util.Log
import com.example.vinilosapp.screens.LoginScreenPage
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import javax.inject.Inject

@HiltAndroidTest
class DemoStepsTest @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
) {

    init {
        Log.d("DemoStepsTest", "DemoStepsTest initialized with ComposeRuleHolder")
    }

    @When("La app se inicializa")
    fun termine_de_cargar() {
        Log.d("DemoStepsTest", "ComposeTestRule is available")
        composeRuleHolder.composeRule.waitForIdle()
    }

    @Then("Puedo ver el logo de la app")
    fun puedo_ver_el_logo_de_la_app() {
        val loginScreenPage = LoginScreenPage(composeRuleHolder.composeRule)
        loginScreenPage.assertAppLogoIsDisplayed()
    }
}
