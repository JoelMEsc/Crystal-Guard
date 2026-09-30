package mx.tec.crystalguard.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.FileInfo
import mx.tec.crystalguard.ui.components.CargandoView
import mx.tec.crystalguard.ui.components.FileCard
import mx.tec.crystalguard.ui.components.UploadCard
import mx.tec.crystalguard.ui.state.ClassMaterialUiState
import mx.tec.crystalguard.ui.state.ClassMaterialViewModel
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun ClassMaterialScreen(
    viewModel: ClassMaterialViewModel,
    modifier: Modifier = Modifier,
) {
    ClassMaterialContent(
        uiState = viewModel.uiState,
        onDeleteBook = viewModel::onDeleteBook,
        onDownloadBook = viewModel::onDownloadBook,
        onUploadBook = viewModel::onUploadBook,
        onDeleteWorksheet = viewModel::onDeleteWorksheet,
        onDownloadWorksheet = viewModel::onDownloadWorksheet,
        onUploadWorksheet = viewModel::onUploadWorksheet,
        modifier = modifier,
    )
}

@Composable
fun ClassMaterialContent(
    uiState: ClassMaterialUiState,
    onDeleteBook: (FileInfo) -> Unit,
    onDownloadBook: (FileInfo) -> Unit,
    onUploadBook: () -> Unit,
    onDeleteWorksheet: (FileInfo) -> Unit,
    onDownloadWorksheet: (FileInfo) -> Unit,
    onUploadWorksheet: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isLoading) {
        CargandoView(modifier = modifier.fillMaxSize())
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Sección 1: Libros
            item {
                Text(
                    text = "Libros",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            items(
                items = uiState.books,
                key = { file -> "book_${file.id}" },
            ) { file ->
                FileCard(
                    file = file,
                    onDeleteClick = { onDeleteBook(file) },
                    onDownloadClick = { onDownloadBook(file) },
                )
            }

            item {
                UploadCard(
                    title = "+ Subir libro",
                    subtitle = "Formato PDF (max. 30 MB)",
                    icon = Icons.Default.CloudUpload,
                    onClick = onUploadBook,
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Sección 2: Hojas de trabajo
            item {
                Text(
                    text = "Hojas de trabajo",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            items(
                items = uiState.worksheets,
                key = { file -> "worksheet_${file.id}" },
            ) { file ->
                FileCard(
                    file = file,
                    onDeleteClick = { onDeleteWorksheet(file) },
                    onDownloadClick = { onDownloadWorksheet(file) },
                )
            }

            item {
                UploadCard(
                    title = "+ Subir hoja de trabajo",
                    subtitle = "Formato PDF, DOCX (max. 20 MB)",
                    icon = Icons.Default.CloudUpload,
                    onClick = onUploadWorksheet,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ClassMaterialScreenPreview() {
    val sampleState = ClassMaterialUiState(
        isLoading = false,
        books = listOf(
            FileInfo(1, "Libro_Lectura_Inicial.pdf", "12.4 MB"),
            FileInfo(2, "Matemáticas_Divertidas_Vol1.pdf", "18.1 MB"),
        ),
        worksheets = listOf(
            FileInfo(3, "Ejercicios_Grafomotricidad_Semana1.pdf", "1.8 MB"),
            FileInfo(4, "Ficha_Colorear_y_Trazar_Figuras.docx", "2.4 MB"),
        ),
    )

    CrystalGuardTheme {
        ClassMaterialContent(
            uiState = sampleState,
            onDeleteBook = {},
            onDownloadBook = {},
            onUploadBook = {},
            onDeleteWorksheet = {},
            onDownloadWorksheet = {},
            onUploadWorksheet = {},
        )
    }
}
