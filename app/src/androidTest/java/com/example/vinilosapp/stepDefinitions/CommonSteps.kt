package com.example.vinilosapp.stepDefinitions
import com.example.vinilosapp.pageobject.LoginScreenPage
import io.cucumber.java.en.When
class CommonSteps() {

    private val loginScreenPage = LoginScreenPage(TestHelper.composeTestRule)

    @When("Un usuario invitado ingresa a Vinilos App")
    fun invitadoIngresaAVinilosApp() {
        loginScreenPage.assertLogoIsDisplayed()
        loginScreenPage.clickLoginAsGuest()
    }
}
