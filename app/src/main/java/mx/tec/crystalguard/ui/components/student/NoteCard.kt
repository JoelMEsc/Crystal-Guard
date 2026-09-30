package mx.tec.crystalguard.ui.components.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.NoteType
import mx.tec.crystalguard.domain.StudentNote
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun NoteCard(
    note: StudentNote,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (typeBg, typeFg) = when (note.type) {
        NoteType.SALUD -> Color(0xFFFFEBEE) to Color(0xFFC62828)
        NoteType.ANIMO -> Color(0xFFFFF3E0) to Color(0xFFE65100)
        NoteType.JUEGOS -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
        NoteType.RESTROOM -> Color(0xFFE1F5FE) to Color(0xFF0277BD)
        NoteType.FOOD -> Color(0xFFFFF8E1) to Color(0xFFF57F17)
        NoteType.MISCELANEOS -> Color(0xFFF3E5F5) to Color(0xFF6A1B9A)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .background(typeBg, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                    ) {
                        Text(
                            text = note.type.label,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = typeFg,
                        )
                    }

                    if (!note.timeText.isNullOrBlank()) {
                        Spacer(modifier = Modifier.width(8.dp))

                        Box(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(8.dp),
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Schedule,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(end = 4.dp),
                                )
                                Text(
                                    text = note.timeText,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }

                IconButton(
                    onClick = onRemoveClick,
                    modifier = Modifier.padding(start = 8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Eliminar nota",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteCardPreview() {
    val sampleNote = StudentNote(
        id = 1,
        type = NoteType.SALUD,
        timeText = "09:00 AM",
        content = "Tomar jarabe para la tos (Dosis 5ml).",
    )

    CrystalGuardTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            NoteCard(
                note = sampleNote,
                onRemoveClick = {},
            )
        }
    }
}
