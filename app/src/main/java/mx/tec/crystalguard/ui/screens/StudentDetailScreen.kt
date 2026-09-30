package mx.tec.crystalguard.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.FileInfo
import mx.tec.crystalguard.domain.NoteType
import mx.tec.crystalguard.domain.StudentDetail
import mx.tec.crystalguard.domain.StudentNote
import mx.tec.crystalguard.domain.people.Student
import mx.tec.crystalguard.ui.components.CargandoView
import mx.tec.crystalguard.ui.components.student.AddNoteButtonCard
import mx.tec.crystalguard.ui.components.student.AttachedFilesSectionCard
import mx.tec.crystalguard.ui.components.student.InfoTagCard
import mx.tec.crystalguard.ui.components.student.NoteCard
import mx.tec.crystalguard.ui.components.student.StudentProfileCard
import mx.tec.crystalguard.ui.state.StudentDetailUiState
import mx.tec.crystalguard.ui.state.StudentDetailViewModel
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun StudentDetailScreen(
    viewModel: StudentDetailViewModel,
    modifier: Modifier = Modifier,
) {
    StudentDetailContent(
        uiState = viewModel.uiState,
        onRemoveNote = viewModel::onRemoveNote,
        onAbrirAgregarNota = viewModel::onAbrirAgregarNota,
        onCerrarAgregarNota = viewModel::onCerrarAgregarNota,
        onAgregarNota = viewModel::onAgregarNota,
        onDeleteFile = viewModel::onDeleteFile,
        onDownloadFile = viewModel::onDownloadFile,
        onUploadFile = viewModel::onUploadFile,
        modifier = modifier,
    )
}

@Composable
fun StudentDetailContent(
    uiState: StudentDetailUiState,
    onRemoveNote: (Int) -> Unit,
    onAbrirAgregarNota: () -> Unit,
    onCerrarAgregarNota: () -> Unit,
    onAgregarNota: (NoteType, String, String) -> Unit,
    onDeleteFile: (FileInfo) -> Unit,
    onDownloadFile: (FileInfo) -> Unit,
    onUploadFile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isLoading || (uiState.studentDetail == null)) {
        CargandoView(modifier = modifier.fillMaxSize())
    } else {
        val detail = uiState.studentDetail

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                StudentProfileCard(
                    student = detail.student,
                    groupName = detail.groupName,
                )
            }

            item { Spacer(modifier = Modifier.height(4.dp)) }

            item {
                InfoTagCard(
                    title = "Edad",
                    value = "${detail.student.age} años",
                    icon = Icons.Default.Cake,
                )
            }

            item {
                InfoTagCard(
                    title = "Tutor",
                    value = detail.tutorName,
                    icon = Icons.Default.Person,
                )
            }

            item {
                InfoTagCard(
                    title = "Contacto",
                    value = detail.tutorContact,
                    icon = Icons.Default.Call,
                )
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                Text(
                    text = "Notas del educador",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            items(
                items = detail.notes,
                key = { note -> note.id },
            ) { note ->
                NoteCard(
                    note = note,
                    onRemoveClick = { onRemoveNote(note.id) },
                )
            }

            item {
                AddNoteButtonCard(
                    onClick = onAbrirAgregarNota,
                )
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }

            item {
                AttachedFilesSectionCard(
                    files = detail.attachedFiles,
                    onDeleteFile = onDeleteFile,
                    onDownloadFile = onDownloadFile,
                    onUploadClick = onUploadFile,
                )
            }
        }

        if (uiState.isAddingNote) {
            var selectedType by remember { mutableStateOf(NoteType.SALUD) }
            var timeText by remember { mutableStateOf("") }
            var contentText by remember { mutableStateOf("") }

            AlertDialog(
                onDismissRequest = onCerrarAgregarNota,
                title = { Text("Añadir nota del educador", fontWeight = FontWeight.Bold) },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text("Tipo de nota:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectableGroup(),
                        ) {
                            NoteType.entries.chunked(2).forEach { rowTypes ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    rowTypes.forEach { type ->
                                        Row(
                                            modifier = Modifier.weight(1f),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            RadioButton(
                                                selected = (selectedType == type),
                                                onClick = { selectedType = type },
                                            )
                                            Text(type.label, style = MaterialTheme.typography.bodySmall)
                                        }
                                    }
                                }
                            }
                        }

                        OutlinedTextField(
                            value = timeText,
                            onValueChange = { timeText = it },
                            label = { Text("Hora u Horario (Opcional)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        OutlinedTextField(
                            value = contentText,
                            onValueChange = { contentText = it },
                            label = { Text("Contenido de la nota") },
                            minLines = 3,
                            maxLines = 5,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                },
                confirmButton = {
                    Button(
                        enabled = contentText.isNotBlank(),
                        onClick = {
                            onAgregarNota(selectedType, timeText, contentText)
                        },
                    ) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = onCerrarAgregarNota) {
                        Text("Cancelar")
                    }
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StudentDetailScreenPreview() {
    val sampleDetail = StudentDetail(
        student = Student(id = 101, name = "Lucas Martínez López", age = 5),
        notes = listOf(
            StudentNote(1, NoteType.FOOD, null, "Alergia a los lácteos y nueces. Consumir únicamente alimentos enviados de casa."),
            StudentNote(2, NoteType.SALUD, "12:00 PM", "Administrar jarabe para la alergia (Dosis 5ml)."),
            StudentNote(3, NoteType.MISCELANEOS, "02:00 PM", "Su tutor pasará a recogerlo temprano por cita médica."),
        ),
        attachedFiles = listOf(
            FileInfo(1, "Aviso_Medico_Lucas.pdf", "1.4 MB"),
        ),
    )

    val sampleState = StudentDetailUiState(
        isLoading = false,
        studentDetail = sampleDetail,
    )

    CrystalGuardTheme {
        StudentDetailContent(
            uiState = sampleState,
            onRemoveNote = {},
            onAbrirAgregarNota = {},
            onCerrarAgregarNota = {},
            onAgregarNota = { _, _, _ -> },
            onDeleteFile = {},
            onDownloadFile = {},
            onUploadFile = {},
        )
    }
}
