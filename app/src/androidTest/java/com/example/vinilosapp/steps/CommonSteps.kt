package com.example.vinilosapp.steps

import com.example.vinilosapp.screens.LoginScreenPage
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.When
import javax.inject.Inject

@HiltAndroidTest
class CommonSteps @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
    private val loginScreenPage: LoginScreenPage,
) {

    @Before(order = 1)
    fun setupComposeTestRule() {
        composeRuleHolder.composeRule.waitForIdle()
    }

    @After(order = 1)
    fun teardownComposeTestRule() {
        composeRuleHolder.composeRule.runOnUiThread {
            composeRuleHolder.composeRule.activity.recreate()
        }
    }

    @When("Un usuario invitado ingresa a Vinilos App")
    fun invitadoIngresaAVinilosApp() {
        loginScreenPage.assertAppLogoIsDisplayed()
        loginScreenPage.clickInvitadoButton()
    }
}
