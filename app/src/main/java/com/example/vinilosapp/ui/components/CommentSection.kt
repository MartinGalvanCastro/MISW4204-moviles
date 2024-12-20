package com.example.vinilosapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.models.CommentSimpleDTO

@Composable
fun CommentSection(comentarios: List<CommentSimpleDTO>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        SectionTitle("Comentarios")

        for (index in comentarios.indices) {
            Column {
                Text(
                    text = "\"${comentarios[index].description}\"",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp, bottom = 0.dp, top = 4.dp),
                )
                Row(
                    modifier = Modifier.padding(start = 8.dp, top = 0.dp),
                ) {
                    repeat(comentarios[index].rating.toInt()) { rep ->
                        Icon(Icons.Default.Star, contentDescription = "Star-$rep", tint = Color.Black)
                    }
                    repeat(5 - comentarios[index].rating.toInt()) { rep ->
                        Icon(Icons.Outlined.StarBorder, contentDescription = "Empty Star - $rep", tint = Color.Black)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
