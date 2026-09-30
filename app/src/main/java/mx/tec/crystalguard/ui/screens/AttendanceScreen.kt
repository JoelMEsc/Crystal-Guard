package mx.tec.crystalguard.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.ui.components.CargandoView
import mx.tec.crystalguard.ui.components.ErrorView
import mx.tec.crystalguard.ui.components.StudentAttendanceCard
import mx.tec.crystalguard.ui.state.AttendanceUiState

@Composable
fun AttendanceScreen(
    uiState: AttendanceUiState,
    onToggleLlego: (Int) -> Unit,
    onToggleSeFue: (Int) -> Unit,
    onToggleNoAsistio: (Int) -> Unit,
    onDejoSelected: (Int, String) -> Unit,
    onRecogioSelected: (Int, String) -> Unit,
    onToggleEditarClick: () -> Unit,
    onGuardarClick: () -> Unit,
    onLimpiarMensaje: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onReintentar: () -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        when {
            uiState.isLoading -> {
                CargandoView(modifier = Modifier.fillMaxSize())
            }
            uiState.errorMessage != null -> {
                ErrorView(
                    mensaje = uiState.errorMessage,
                    onReintentar = onReintentar,
                    modifier = Modifier.fillMaxSize(),
                )
            }
            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        item {
                            DateHeaderCard(
                                dateText = uiState.dateText,
                            )
                        }

                        items(
                            items = uiState.students,
                            key = { student -> student.id },
                        ) { student ->
                            StudentAttendanceCard(
                                student = student,
                                isEditing = uiState.isEditing,
                                onToggleLlego = { onToggleLlego(student.id) },
                                onToggleSeFue = { onToggleSeFue(student.id) },
                                onToggleNoAsistio = { onToggleNoAsistio(student.id) },
                                onDejoSelected = { persona -> onDejoSelected(student.id, persona) },
                                onRecogioSelected = { persona -> onRecogioSelected(student.id, persona) },
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        OutlinedButton(
                            onClick = onToggleEditarClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(24.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (uiState.isEditing) "Modo Edición" else "Editar",
                                fontWeight = FontWeight.Bold,
                            )
                        }

                        Button(
                            onClick = onGuardarClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD84315),
                            ),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Save,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Guardar",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                            )
                        }
                    }
                }
            }
        }

        val exito = uiState.mensajeExito
        if (exito != null) {
            AlertDialog(
                onDismissRequest = onLimpiarMensaje,
                title = { Text("Asistencia Guardada") },
                text = { Text(exito) },
                confirmButton = {
                    TextButton(onClick = onLimpiarMensaje) {
                        Text("Aceptar")
                    }
                },
            )
        }

        val advertencia = uiState.mensajeAdvertencia
        if (advertencia != null) {
            AlertDialog(
                onDismissRequest = onLimpiarMensaje,
                title = { Text("Atención", fontWeight = FontWeight.Bold) },
                text = { Text(advertencia) },
                confirmButton = {
                    TextButton(onClick = onLimpiarMensaje) {
                        Text("Entendido")
                    }
                },
            )
        }
    }
}

@Composable
private fun DateHeaderCard(
    dateText: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Día anterior",
                )
            }

            Text(
                text = dateText,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
            )

            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Día siguiente",
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFD84315)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = "Seleccionar fecha",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp),
                )
            }
        }
    }
}
