package mx.tec.crystalguard.ui.components.student

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.FileInfo
import mx.tec.crystalguard.ui.components.FileCard
import mx.tec.crystalguard.ui.components.UploadCard
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun AttachedFilesSectionCard(
    files: List<FileInfo>,
    onDeleteFile: (FileInfo) -> Unit,
    onDownloadFile: (FileInfo) -> Unit,
    onUploadClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = "Archivos adjuntos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (files.isEmpty()) {
                Text(
                    text = "No hay archivos adjuntos.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 8.dp),
                )
            } else {
                files.forEach { file ->
                    FileCard(
                        file = file,
                        onDeleteClick = { onDeleteFile(file) },
                        onDownloadClick = { onDownloadFile(file) },
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            UploadCard(
                title = "+ Subir archivo",
                subtitle = "Formato PDF, DOCX, JPG (max. 20 MB)",
                icon = Icons.Default.CloudUpload,
                onClick = onUploadClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AttachedFilesSectionCardPreview() {
    val sampleFiles = listOf(
        FileInfo(1, "Aviso_Medico_Lucas.pdf", "1.4 MB"),
        FileInfo(2, "Ficha_Inscripcion_Lucas.pdf", "2.1 MB"),
    )

    CrystalGuardTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            AttachedFilesSectionCard(
                files = sampleFiles,
                onDeleteFile = {},
                onDownloadFile = {},
                onUploadClick = {},
            )
        }
    }
}
