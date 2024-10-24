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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.models.CommentSimpleDTO
import com.example.vinilosapp.ui.theme.VinilosAppTheme
import java.math.BigDecimal

@Composable
fun CommentSection(comentarios: List<CommentSimpleDTO>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        SectionTitle("Comentarios")

        comentarios.forEach { comentario ->
            Column {
                Text(
                    text = "\"${comentario.description}\"",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp, bottom = 0.dp, top = 4.dp),
                )
                Row(
                    modifier = Modifier.padding(start = 8.dp, top = 0.dp),
                ) {
                    repeat(comentario.rating.toInt()) {
                        Icon(Icons.Default.Star, contentDescription = "Star", tint = Color.Black)
                    }
                    repeat(5 - comentario.rating.toInt()) {
                        Icon(Icons.Outlined.StarBorder, contentDescription = "Empty Star", tint = Color.Black)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommentSectionPreview() {
    val comentarios = listOf<CommentSimpleDTO>(
        CommentSimpleDTO(BigDecimal(1), "Lorem Ipsum", BigDecimal(4)),
        CommentSimpleDTO(BigDecimal(2), "Lorem Ipsum", BigDecimal(4)),
        CommentSimpleDTO(BigDecimal(3), "Lorem Ipsum", BigDecimal(4)),
    )

    VinilosAppTheme {
        CommentSection(comentarios)
    }
}
