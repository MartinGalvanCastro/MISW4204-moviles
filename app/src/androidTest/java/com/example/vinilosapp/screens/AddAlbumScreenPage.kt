package com.example.vinilosapp.screens

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.vinilosapp.steps.ComposeRuleHolder
import com.example.vinilosapp.ui.components.TestTags
import com.example.vinilosapp.ui.screens.ScreenTestTags
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddAlbumScreenPage @Inject constructor(
    composeRuleHolder: ComposeRuleHolder,
) {

    private val composeRule = composeRuleHolder.composeRule

    fun enterAlbumName(name: String): Boolean {
        return try {
            composeRule.onNodeWithTag(ScreenTestTags.ALBUM_NAME_TEXT_FIELD)
                .assertExists()
                .assertIsDisplayed()
                .performClick()
            composeRule.waitUntil(timeoutMillis = 5000) {
                composeRule.onNodeWithTag(ScreenTestTags.ALBUM_NAME_TEXT_FIELD)
                    .fetchSemanticsNode()
                    .config.getOrNull(SemanticsProperties.IsEditable) == true
            }
            composeRule.onNodeWithTag(ScreenTestTags.ALBUM_NAME_TEXT_FIELD)
                .performTextInput(name)
            true
        } catch (err: Exception) {
            err.printStackTrace()
            false
        }
    }

    fun enterDescription(description: String) {
        composeRule.onNodeWithTag(ScreenTestTags.DESCRIPTION_TEXT_FIELD).performTextInput(description)
    }

    fun selectReleaseDate() {
        composeRule.onNodeWithTag(TestTags.DATE_PICKER_TRAILING_ICON)
            .performClick()

        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.getDefault())

        val currentDateString = currentDate.format(formatter)

        composeRule.onNodeWithText("Today, $currentDateString", useUnmergedTree = true)
            .performClick()

        composeRule.onNodeWithTag(TestTags.DATE_PICKER_CONFIRM_BUTTON)
            .performClick()
    }

    fun selectFirstGenre() {
        composeRule.onNodeWithTag(ScreenTestTags.GENRE_DROPDOWN).performClick()
        composeRule.onAllNodesWithTag("Dropdown-option")
            .onFirst()
            .performClick()
    }

    fun selectFirstRecordLabel() {
        composeRule.onNodeWithTag(ScreenTestTags.RECORD_LABEL_DROPDOWN).performClick()
        composeRule.onAllNodesWithTag("Dropdown-option")
            .onFirst()
            .performClick()
    }

    fun selectFirstPerformer() {
        composeRule.onNodeWithTag(ScreenTestTags.PERFORMER_DROPDOWN).performClick()
        composeRule.onAllNodesWithTag("Dropdown-option")
            .onFirst()
            .performClick()
    }
}
