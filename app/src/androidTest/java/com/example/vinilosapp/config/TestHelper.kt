import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.vinilosapp.MainActivity

object TestHelper {
    val composeTestRule: ComposeTestRule = createAndroidComposeRule<MainActivity>()
}
