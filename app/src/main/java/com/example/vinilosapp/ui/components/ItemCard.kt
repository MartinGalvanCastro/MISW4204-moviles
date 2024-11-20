import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemCard(
    title: String,
    description: String,
    footer: String,
    onSelect: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    var isPressed by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .pointerInput(onSelect) {
                onSelect?.let {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            try {
                                awaitRelease()
                                isPressed = false
                                it()
                            } catch (e: Exception) {
                                isPressed = false
                            }
                        },
                    )
                }
            }
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    fontSize = 16.sp,
                    modifier = Modifier.testTag("itemCardTitle"),
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp,
                    modifier = Modifier.testTag("itemCardDescription"),
                )
                Text(
                    text = footer,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Gray,
                    ),
                    fontSize = 12.sp,
                    modifier = Modifier.testTag("itemCardFooter"),
                )
            }
        }

        if (isPressed) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.1f))
                    .testTag("ItemCardOverlay"),
            )
        }
    }
}
