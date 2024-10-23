import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import com.example.vinilosapp.navigation.Routes
import com.example.vinilosapp.ui.components.Navbar
import org.junit.Rule
import org.junit.Test

class NavbarKtTest {

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
    fun navbar_displaysItemsCorrectlyTest() {
        composeTestRule.setContent {
            testNavController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            TestNavGraph(testNavController)
            Navbar(navController = testNavController)
        }

        // Assert that the Navbar items are displayed
        composeTestRule
            .onNodeWithTag("navbar-item-Albumes")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("navbar-item-Artistas")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("navbar-item-Bandas")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("navbar-item-Coleccionistas")
            .assertIsDisplayed()
    }

    @Test
    fun navbar_navigatesOnClickTest() {
        composeTestRule.setContent {
            testNavController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            TestNavGraph(testNavController)
            Navbar(navController = testNavController)
        }

        composeTestRule
            .onNodeWithTag("navbar-item-Artistas")
            .performClick()

        assert(testNavController.currentDestination?.route == Routes.ARTISTAS_SCREEN)
    }
}
