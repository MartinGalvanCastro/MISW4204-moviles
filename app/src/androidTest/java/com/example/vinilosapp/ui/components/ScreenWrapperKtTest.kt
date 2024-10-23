package com.example.vinilosapp.ui.components

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import com.example.vinilosapp.navigation.Routes
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ScreenWrapperKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var testNavController: TestNavHostController

    @Composable
    fun TestNavGraph(navController: TestNavHostController) {
        NavHost(navController = navController, startDestination = Routes.ALBUMS_SCREEN) {
            composable(Routes.ALBUMS_SCREEN) { /* Your Albums Screen Composable */ }
            composable(Routes.ARTISTAS_SCREEN) { /* Your Artistas Screen Composable */ }
            composable(Routes.BANDAS_SCREEN) { /* Your Bandas Screen Composable */ }
            composable(Routes.COLECCIONISTAS_SCREEN) { /* Your Coleccionistas Screen Composable */ }
        }
    }

    @Test
    fun screenWrapper_displaysContentCorrectlyTest() {
        composeTestRule.setContent {
            testNavController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            TestNavGraph(testNavController)
            ScreenWrapper(navController = testNavController, goBackFunction = {}) {
                Text("Test Content", modifier = Modifier.testTag("testContent"))
            }
        }

        composeTestRule
            .onNodeWithTag("testContent")
            .assertIsDisplayed()
    }

    @Test
    fun screenWrapper_callsGoBackFunctionOnBackClickTest() {
        val mockGoBackFunction = mock<() -> Unit>()

        composeTestRule.setContent {
            testNavController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            TestNavGraph(testNavController)
            ScreenWrapper(navController = testNavController, goBackFunction = mockGoBackFunction) {
                Text("Test Content")
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("backButton", useUnmergedTree = true)
            .performClick()

        verify(mockGoBackFunction).invoke()

        assert(testNavController.currentDestination?.route == Routes.ALBUMS_SCREEN)
    }
}
