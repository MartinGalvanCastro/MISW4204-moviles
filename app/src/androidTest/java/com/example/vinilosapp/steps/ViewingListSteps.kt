package com.example.vinilosapp.steps

import com.example.vinilosapp.screens.AlbumScreenPage
import com.example.vinilosapp.screens.ArtistaScreenPage
import com.example.vinilosapp.screens.BandScreenPage
import com.example.vinilosapp.screens.ColeccionistaScreenPage
import io.cucumber.java.en.Then
import javax.inject.Inject

class ViewingListSteps @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
    private val albumScreenPage: AlbumScreenPage,
    private val artistaScreenPage: ArtistaScreenPage,
    private val bandScreenPage: BandScreenPage,
    private val coleccionistaScreenPage: ColeccionistaScreenPage,
) {

    @Then("Puede ver el listado de {string}")
    fun puedeVerListado(entidades: String) {
        composeRuleHolder.composeRule.waitForIdle()
        when (entidades) {
            "albumes" -> composeRuleHolder.composeRule.waitUntil(timeoutMillis = 15_000) {
                albumScreenPage.assertAlbumGridIsDisplayed()
            }
            "artistas" -> composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
                artistaScreenPage.assertMusicianGridIsDisplayed()
            }
            "bandas" -> composeRuleHolder.composeRule.waitUntil(timeoutMillis = 5_000) {
                bandScreenPage.assertBandGridIsDisplayed()
            }
            "coleccionistas" -> composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
                coleccionistaScreenPage.assertCollectorListIsDisplayed()
            }
            else -> throw IllegalArgumentException("Unsupported entity type: $entidades")
        }
    }

    @Then("Cada {string} tiene {string}")
    fun cadaEntidadTieneSusDetalles(entidad: String, detalles: String) {
        composeRuleHolder.composeRule.waitForIdle()
        when (entidad) {
            "album" -> composeRuleHolder.composeRule.waitUntil(timeoutMillis = 15_000) {
                albumScreenPage.assertEachAlbumHasImageAndText()
            }
            "artista" -> composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
                artistaScreenPage.assertEachMusicianHasImageAndText()
            }
            "banda" -> composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
                bandScreenPage.assertEachBandHasImageAndText()
            }
            "coleccionista" -> composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
                coleccionistaScreenPage.assertAllCollectorsHaveDetails()
            }
            else -> throw IllegalArgumentException("Unsupported entity type: $entidad")
        }
    }

    @Then("Solo puede ver las entidades {string} que contengan la palabra {string}")
    fun soloPuedeVerEntidadesFiltradas(entidades: String, palabra: String) {
        composeRuleHolder.composeRule.waitForIdle()
        when (entidades) {
            "albumes" -> composeRuleHolder.composeRule.waitUntil(timeoutMillis = 15_000) {
                albumScreenPage.assertTextIsDisplayed(palabra)
            }
            "artistas" -> composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
                artistaScreenPage.assertTextIsDisplayed(palabra)
            }
            "bandas" -> composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
                bandScreenPage.assertTextIsDisplayed(palabra)
            }
            "coleccionistas" -> composeRuleHolder.composeRule.waitUntil(timeoutMillis = 10_000) {
                coleccionistaScreenPage.assertCollectorsListIsFiltered(palabra)
            }
            else -> throw IllegalArgumentException("Unsupported entity type: $entidades")
        }
    }
}
