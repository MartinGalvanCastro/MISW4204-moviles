package com.example.vinilosapp.stepDefinitions

import com.example.vinilosapp.pageobject.AlbumScreenPage
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

class AlbumListSteps() {

    private val albumesScreenPage = AlbumScreenPage(TestHelper.composeTestRule)

    @Then("Puede ver el listado de albumes")
    fun puedeVerListadoAlbumes() {
        albumesScreenPage.assertAlbumGridIsDisplayed()
    }

    @Then("Cada album tiene su nombre, su foto y el nombre del artista")
    fun cadaAlbumTieneNombreFotoYArtista() {
        albumesScreenPage.assertEachAlbumHasImageAndText()
    }

    @When("Ingresa la palabra {string}")
    fun ingresaLaPalabraEnFiltro(palabra: String) {
        albumesScreenPage.enterFilterText(palabra)
    }

    @Then("Solo puede ver el album {string}")
    fun soloPuedeVerElAlbum(albumName: String) {
        albumesScreenPage.assertTextIsDisplayed(albumName)
    }
}
