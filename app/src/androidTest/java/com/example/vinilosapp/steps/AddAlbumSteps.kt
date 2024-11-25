package com.example.vinilosapp.steps

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.vinilosapp.screens.AddAlbumScreenPage
import com.example.vinilosapp.screens.AlbumScreenPage
import com.example.vinilosapp.screens.HelperPage
import io.cucumber.java.en.And
import io.cucumber.java.en.Then
import javax.inject.Inject

class AddAlbumSteps @Inject constructor(
    private val albumScreenPage: AlbumScreenPage,
    private val composeRuleHolder: ComposeRuleHolder,
    private val helperPage: HelperPage,
    private val addAlbumScreenPage: AddAlbumScreenPage,

) {

    val ALBUM_STANDARD_NAME = "Album"
    val CANCION_STANDARD_NAME = "Cancion"

    @And("Quiere agregar {string}")
    fun quiereAgregarEntidad(entidad: String) {
        when (entidad) {
            "un album" -> albumScreenPage.clickAddAlbumButton()
            else -> throw IllegalArgumentException("Unsupported entity type: $entidad")
        }
    }

    @And("{string} se llama test-e2e-{string}")
    fun darNombreEntidad(tipo: String, nombre: String) {
        val randomUuid = java.util.UUID.randomUUID().toString()
        val generatedName = "$nombre-$randomUuid"
        val tipoStandard = if (tipo == "El album") ALBUM_STANDARD_NAME else CANCION_STANDARD_NAME

        composeRuleHolder.composeRule.waitForIdle()

        helperPage.saveGeneratedName(tipoStandard, nombre)

        composeRuleHolder.composeRule.waitUntil(10_000) {
            helperPage.saveGeneratedName(tipoStandard, nombre)
            addAlbumScreenPage.enterAlbumName(generatedName)
        }
        composeRuleHolder.composeRule.waitForIdle()
    }

    @Then("Puede ingresar la informacion {string} y crearlo")
    fun llenarYCrear(tipo: String) {
        when (tipo) {
            "del album" -> {
                val name = helperPage.getGeneratedName(ALBUM_STANDARD_NAME)
                addAlbumScreenPage.enterDescription("This is a description for $name") // Random description
                addAlbumScreenPage.selectReleaseDate() // Default date
                addAlbumScreenPage.selectFirstGenre() // First item in Genre dropdown
                addAlbumScreenPage.selectFirstRecordLabel() // First item in Record Label dropdown
                addAlbumScreenPage.selectFirstPerformer() // First item in Performer dropdown
            }
            else -> throw IllegalArgumentException("Unsupported entity type: $tipo")
        }
        composeRuleHolder.composeRule.onNodeWithTag("SubmitButton").performClick()
    }
}
