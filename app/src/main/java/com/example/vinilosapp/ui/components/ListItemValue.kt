package com.example.vinilosapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class ListItemValueItem(
    val item: String,
    val value: String? = null,
    val modifier: Modifier,
)

@Composable
fun ListItemValue(
    items: List<ListItemValueItem>,
    showBulletPoint: Boolean = false,
    extraSpacing: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEach { item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = if (extraSpacing) Modifier.weight(2f) else Modifier.wrapContentWidth(),
                ) {
                    if (showBulletPoint) {
                        Text(text = "•", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        text = item.item,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }

                if (item.value != null) {
                    Text(
                        text = item.value,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = if (extraSpacing) Modifier else Modifier.align(Alignment.CenterVertically),
                    )
                }
            }
        }
    }
}
