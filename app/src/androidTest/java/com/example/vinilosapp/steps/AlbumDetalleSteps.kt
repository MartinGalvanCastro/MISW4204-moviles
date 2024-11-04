package com.example.vinilosapp.steps

import io.cucumber.java.en.And
import io.cucumber.java.en.Then
import com.example.vinilosapp.screens.AlbumDetalleScreenPage
import com.example.vinilosapp.screens.AlbumScreenPage
import javax.inject.Inject

class AlbumDetalleSteps @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
    private val albumScreenPage: AlbumScreenPage,
    private val albumDetalleScreenPage: AlbumDetalleScreenPage
) {

    @Then("Puede ingresar a ver el detalle de un album")
    fun puedeIngresarAVerElDetalleDeUnAlbum() {
        composeRuleHolder.composeRule.waitForIdle()
        albumScreenPage.clickOnAlbum()
        composeRuleHolder.composeRule.waitForIdle()
    }

    @And("El album tiene caratula")
    fun elAlbumTieneCaratula() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            albumDetalleScreenPage.assertAlbumCoverIsDisplayed()
        }
    }

    @And("El album tiene descripcion")
    fun elAlbumTieneDescripcion() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            albumDetalleScreenPage.assertAlbumDescriptionIsDisplayed()
        }
    }

    @And("El album tiene fecha de publicacion")
    fun elAlbumTieneFechaDePublicacion() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            albumDetalleScreenPage.assertAlbumReleaseDateIsDisplayed()
        }
    }

    @And("El album tiene disquera")
    fun elAlbumTieneDisquera() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            albumDetalleScreenPage.assertAlbumLabelIsDisplayed()
        }
    }

    @And("El album tiene genero")
    fun elAlbumTieneGenero() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            albumDetalleScreenPage.assertAlbumGenreIsDisplayed()
        }
    }

    @And("El album tiene canciones")
    fun elAlbumTieneCanciones() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            albumDetalleScreenPage.assertTracksSectionIsDisplayed()
        }
    }

    @And("El album tiene artistas")
    fun elAlbumTieneArtistas() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            albumDetalleScreenPage.assertPerformersSectionIsDisplayed()
        }
    }

    @And("El album tiene comentarios")
    fun elAlbumTieneComentarios() {
        composeRuleHolder.composeRule.waitForIdle()
        composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
            albumDetalleScreenPage.assertCommentsSectionIsDisplayed()
        }
    }
}
