package com.example.vinilosapp.steps

import com.example.vinilosapp.screens.AlbumDetalleScreenPage
import com.example.vinilosapp.screens.AlbumScreenPage
import com.example.vinilosapp.screens.ArtistaDetalleScreenPage
import com.example.vinilosapp.screens.ArtistaScreenPage
import com.example.vinilosapp.screens.BandScreenPage
import com.example.vinilosapp.screens.BandaDetalleScreenPage
import com.example.vinilosapp.screens.ColeccionistaScreenPage
import com.example.vinilosapp.screens.ColeccionistasDetalleScreenPage
import io.cucumber.java.en.And
import io.cucumber.java.en.Then
import javax.inject.Inject

class ViewingDetailSteps @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
    private val albumScreenPage: AlbumScreenPage,
    private val albumDetalleScreenPage: AlbumDetalleScreenPage,
    private val artistaScreenPage: ArtistaScreenPage,
    private val artistaDetalleScreenPage: ArtistaDetalleScreenPage,
    private val bandScreenPage: BandScreenPage,
    private val bandaDetalleScreenPage: BandaDetalleScreenPage,
    private val coleccionistaScreenPage: ColeccionistaScreenPage,
    private val coleccionistasDetalleScreenPage: ColeccionistasDetalleScreenPage,
) {

    @Then("Puede ingresar a ver el detalle de un {string}")
    fun puedeIngresarAVerDetalle(entidad: String) {
        composeRuleHolder.composeRule.waitForIdle()
        when (entidad) {
            "album" -> albumScreenPage.clickOnAlbum()
            "artista" -> artistaScreenPage.clickOnMusician()
            "banda" -> bandScreenPage.clickOnBand()
            "coleccionista" -> coleccionistaScreenPage.clickOnCollector()
            else -> throw IllegalArgumentException("Unsupported entity type: $entidad")
        }
        composeRuleHolder.composeRule.waitForIdle()
    }

    @And("{string} tiene {string}")
    fun entidadTieneDetalles(entidad: String, detalles: String) {
        composeRuleHolder.composeRule.waitForIdle()
        val detailsList = detalles.split(", ").map { it.trim() }

        when (entidad) {
            "album" -> validateAlbumDetails(detailsList)
            "artista" -> validateArtistaDetails(detailsList)
            "banda" -> validateBandaDetails(detailsList)
            "coleccionista" -> validateColeccionistaDetails(detailsList)
            else -> throw IllegalArgumentException("Unsupported entity type: $entidad")
        }
    }

    private fun validateAlbumDetails(detailsList: List<String>) {
        detailsList.forEach { detail ->
            when (detail) {
                "caratula" -> albumDetalleScreenPage.assertAlbumCoverIsDisplayed()
                "descripcion" -> albumDetalleScreenPage.assertAlbumDescriptionIsDisplayed()
                "fecha de publicacion" -> albumDetalleScreenPage.assertAlbumReleaseDateIsDisplayed()
                "disquera" -> albumDetalleScreenPage.assertAlbumLabelIsDisplayed()
                "genero" -> albumDetalleScreenPage.assertAlbumGenreIsDisplayed()
                "canciones" -> albumDetalleScreenPage.assertTracksSectionIsDisplayed()
                "artistas" -> albumDetalleScreenPage.assertPerformersSectionIsDisplayed()
                "comentarios" -> albumDetalleScreenPage.assertCommentsSectionIsDisplayed()
                else -> throw IllegalArgumentException("Unsupported album detail: $detail")
            }
        }
    }

    private fun validateArtistaDetails(detailsList: List<String>) {
        detailsList.forEach { detail ->
            when (detail) {
                "informacion" -> artistaDetalleScreenPage.assertInfoSectionIsDisplayed()
                "premios" -> artistaDetalleScreenPage.assertPremiosSectionIsDisplayed()
                "albumes" -> artistaDetalleScreenPage.assertAlbumsSectionIsDisplayed()
                else -> throw IllegalArgumentException("Unsupported artist detail: $detail")
            }
        }
    }

    private fun validateBandaDetails(detailsList: List<String>) {
        detailsList.forEach { detail ->
            when (detail) {
                "informacion" -> bandaDetalleScreenPage.assertInfoSectionIsDisplayed()
                "premios" -> bandaDetalleScreenPage.assertPremiosSectionIsDisplayed()
                "albumes" -> bandaDetalleScreenPage.assertAlbumsSectionIsDisplayed()
                else -> throw IllegalArgumentException("Unsupported band detail: $detail")
            }
        }
    }

    private fun validateColeccionistaDetails(detailsList: List<String>) {
        detailsList.forEach { detail ->
            when (detail) {
                "un correo" -> coleccionistasDetalleScreenPage.assertCollectorEmailIsDisplayed()
                "un telefono" -> coleccionistasDetalleScreenPage.assertCollectorPhoneIsDisplayed()
                "albumes" -> coleccionistasDetalleScreenPage.assertAlbumSectionIsDisplayed()
                "artistas favoritos" -> coleccionistasDetalleScreenPage.assertArtistasSectionIsDisplayed()
                else -> throw IllegalArgumentException("Unsupported collector detail: $detail")
            }
        }
    }
}
