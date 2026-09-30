package mx.tec.crystalguard.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.BitacoraSection
import mx.tec.crystalguard.domain.DailyReport
import mx.tec.crystalguard.domain.EgresoData
import mx.tec.crystalguard.domain.EstadiaData
import mx.tec.crystalguard.domain.IngresoData
import mx.tec.crystalguard.domain.people.Student
import mx.tec.crystalguard.ui.components.CargandoView
import mx.tec.crystalguard.ui.components.SlidingSelector
import mx.tec.crystalguard.ui.components.bitacora.BitacoraCheckboxCard
import mx.tec.crystalguard.ui.components.bitacora.BitacoraCounterCard
import mx.tec.crystalguard.ui.components.bitacora.BitacoraMealSelectorCard
import mx.tec.crystalguard.ui.components.bitacora.BitacoraPhotoUploadCard
import mx.tec.crystalguard.ui.state.BitacoraUiState
import mx.tec.crystalguard.ui.state.BitacoraViewModel
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun BitacoraScreen(
    viewModel: BitacoraViewModel,
    modifier: Modifier = Modifier,
) {
    BitacoraContent(
        uiState = viewModel.uiState,
        onSelectStudent = viewModel::seleccionarEstudiante,
        onSelectSection = viewModel::cambiarSeccion,
        onDateChange = viewModel::cambiarFecha,
        onUpdateIngreso = viewModel::actualizarIngreso,
        onUpdateEstadia = viewModel::actualizarEstadia,
        onUpdateEgreso = viewModel::actualizarEgreso,
        onGuardarClick = viewModel::guardarBitacora,
        onLimpiarMensaje = viewModel::limpiarMensaje,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BitacoraContent(
    uiState: BitacoraUiState,
    onSelectStudent: (Student) -> Unit,
    onSelectSection: (BitacoraSection) -> Unit,
    onDateChange: (String) -> Unit,
    onUpdateIngreso: (IngresoData) -> Unit,
    onUpdateEstadia: (EstadiaData) -> Unit,
    onUpdateEgreso: (EgresoData) -> Unit,
    onGuardarClick: () -> Unit,
    onLimpiarMensaje: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isLoading || (uiState.currentReport == null)) {
        CargandoView(modifier = modifier.fillMaxSize())
    } else {
        val report = uiState.currentReport

        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            SlidingSelector(
                text = uiState.dateText,
                onClickLeft = { onDateChange("28 de Septiembre, 2026") },
                onClickRight = { onDateChange("30 de Septiembre, 2026") },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(4.dp))

            var expandedDropdown by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expandedDropdown,
                onExpandedChange = { expandedDropdown = !expandedDropdown },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                OutlinedTextField(
                    value = uiState.selectedStudent?.name ?: "Seleccionar Alumno",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Alumno/a") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                )

                ExposedDropdownMenu(
                    expanded = expandedDropdown,
                    onDismissRequest = { expandedDropdown = false },
                ) {
                    uiState.groupStudents.forEach { student ->
                        DropdownMenuItem(
                            text = { Text(student.name) },
                            onClick = {
                                onSelectStudent(student)
                                expandedDropdown = false
                            },
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            TabRow(
                selectedTabIndex = uiState.selectedSection.ordinal,
                modifier = Modifier.fillMaxWidth(),
            ) {
                BitacoraSection.entries.forEach { section ->
                    Tab(
                        selected = (uiState.selectedSection == section),
                        onClick = { onSelectSection(section) },
                        text = {
                            Text(
                                text = section.name,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                            )
                        },
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                when (uiState.selectedSection) {
                    BitacoraSection.INGRESO -> {
                        val ingreso = report.ingreso

                        item {
                            OutlinedTextField(
                                value = ingreso.horaEntrada,
                                onValueChange = { onUpdateIngreso(ingreso.copy(horaEntrada = it)) },
                                label = { Text("Hora de entrada") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = ingreso.personaEntregaNombre,
                                onValueChange = { onUpdateIngreso(ingreso.copy(personaEntregaNombre = it)) },
                                label = { Text("Persona que entrega al alumno/a") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }

                        item {
                            BitacoraPhotoUploadCard(
                                title = "Firma o Foto de Entrega (Check-In)",
                                subtitle = "Subir foto/firma de quien entrega",
                                onUploadClick = {},
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = ingreso.personalRecibeNombre,
                                onValueChange = { onUpdateIngreso(ingreso.copy(personalRecibeNombre = it)) },
                                label = { Text("Personal de la estancia que recibe") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }

                        item {
                            BitacoraCheckboxCard(
                                question = "¿La niña o el niño está enfermo/a?",
                                value = ingreso.estaEnfermo,
                                onValueChange = { onUpdateIngreso(ingreso.copy(estaEnfermo = it)) },
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = ingreso.sintomasIngreso,
                                onValueChange = { onUpdateIngreso(ingreso.copy(sintomasIngreso = it)) },
                                label = { Text("Síntomas de enfermedad (si aplica)") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }

                        item {
                            BitacoraCheckboxCard(
                                question = "¿Se presentó limpio/a?",
                                value = ingreso.presentoLimpio,
                                onValueChange = { onUpdateIngreso(ingreso.copy(presentoLimpio = it)) },
                            )
                        }

                        item {
                            BitacoraCheckboxCard(
                                question = "¿Mochila completa según sus necesidades?",
                                value = ingreso.mochilaCompleta,
                                onValueChange = { onUpdateIngreso(ingreso.copy(mochilaCompleta = it)) },
                            )
                        }

                        item {
                            BitacoraCheckboxCard(
                                question = "¿Se presentó en buen estado físico?",
                                value = ingreso.buenEstadoFisico,
                                onValueChange = { onUpdateIngreso(ingreso.copy(buenEstadoFisico = it)) },
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = ingreso.lesionesFisicas,
                                onValueChange = { onUpdateIngreso(ingreso.copy(lesionesFisicas = it)) },
                                label = { Text("Especificar rasguño, moretón o golpe con el que ingresa") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = ingreso.observacionesPadres,
                                onValueChange = { onUpdateIngreso(ingreso.copy(observacionesPadres = it)) },
                                label = { Text("Observaciones para padres/tutores") },
                                minLines = 2,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }

                    BitacoraSection.ESTADIA -> {
                        val estadia = report.estadia

                        item {
                            Text(
                                text = "Alimentación",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                        item {
                            BitacoraMealSelectorCard(
                                mealName = "Desayuno",
                                selectedOption = estadia.desayuno,
                                onOptionSelected = { onUpdateEstadia(estadia.copy(desayuno = it)) },
                            )
                        }

                        item {
                            BitacoraMealSelectorCard(
                                mealName = "Colación",
                                selectedOption = estadia.colacion,
                                onOptionSelected = { onUpdateEstadia(estadia.copy(colacion = it)) },
                            )
                        }

                        item {
                            BitacoraMealSelectorCard(
                                mealName = "Comida",
                                selectedOption = estadia.comida,
                                onOptionSelected = { onUpdateEstadia(estadia.copy(comida = it)) },
                            )
                        }

                        item {
                            Text(
                                text = "Descanso / Siesta",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                        item {
                            BitacoraCheckboxCard(
                                question = "¿El alumno/a durmió siesta?",
                                value = estadia.durmioSiesta,
                                onValueChange = { onUpdateEstadia(estadia.copy(durmioSiesta = it)) },
                            )
                        }

                        item {
                            BitacoraCounterCard(
                                title = "Tiempo de siesta",
                                count = estadia.tiempoSiestaMinutos,
                                unitText = "min",
                                onIncrement = { onUpdateEstadia(estadia.copy(tiempoSiestaMinutos = estadia.tiempoSiestaMinutos + 15)) },
                                onDecrement = { onUpdateEstadia(estadia.copy(tiempoSiestaMinutos = (estadia.tiempoSiestaMinutos - 15).coerceAtLeast(0))) },
                            )
                        }

                        item {
                            Text(
                                text = "Baño y Esfínteres",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                        item {
                            BitacoraCounterCard(
                                title = "Pipí (veces)",
                                count = estadia.conteoPipi,
                                onIncrement = { onUpdateEstadia(estadia.copy(conteoPipi = estadia.conteoPipi + 1)) },
                                onDecrement = { onUpdateEstadia(estadia.copy(conteoPipi = (estadia.conteoPipi - 1).coerceAtLeast(0))) },
                            )
                        }

                        item {
                            BitacoraCounterCard(
                                title = "Popó (veces)",
                                count = estadia.conteoPopo,
                                onIncrement = { onUpdateEstadia(estadia.copy(conteoPopo = estadia.conteoPopo + 1)) },
                                onDecrement = { onUpdateEstadia(estadia.copy(conteoPopo = (estadia.conteoPopo - 1).coerceAtLeast(0))) },
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = estadia.obsAnimo,
                                onValueChange = { onUpdateEstadia(estadia.copy(obsAnimo = it)) },
                                label = { Text("Comentarios sobre estado de ánimo y participación") },
                                minLines = 2,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }

                    BitacoraSection.EGRESO -> {
                        val egreso = report.egreso

                        item {
                            OutlinedTextField(
                                value = egreso.horaSalida,
                                onValueChange = { onUpdateEgreso(egreso.copy(horaSalida = it)) },
                                label = { Text("Hora de salida") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = egreso.personaRecibeNombre,
                                onValueChange = { onUpdateEgreso(egreso.copy(personaRecibeNombre = it)) },
                                label = { Text("Nombre de quien recibe al alumno/a") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }

                        item {
                            BitacoraPhotoUploadCard(
                                title = "Firma o Foto de Salida (Check-Out)",
                                subtitle = "Subir foto/firma de quien recibe",
                                onUploadClick = {},
                            )
                        }

                        item {
                            BitacoraCheckboxCard(
                                question = "¿Se entrega en buen estado físico?",
                                value = egreso.buenEstadoFisicoSalida,
                                onValueChange = { onUpdateEgreso(egreso.copy(buenEstadoFisicoSalida = it)) },
                            )
                        }

                        item {
                            BitacoraCheckboxCard(
                                question = "¿Se entrega limpio/a?",
                                value = egreso.entregoLimpio,
                                onValueChange = { onUpdateEgreso(egreso.copy(entregoLimpio = it)) },
                            )
                        }

                        item {
                            BitacoraCheckboxCard(
                                question = "¿Se entrega con pertenencias completas?",
                                value = egreso.pertenenciasCompletas,
                                onValueChange = { onUpdateEgreso(egreso.copy(pertenenciasCompletas = it)) },
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = egreso.observacionesSalida,
                                onValueChange = { onUpdateEgreso(egreso.copy(observacionesSalida = it)) },
                                label = { Text("Observaciones de salida") },
                                minLines = 2,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
            }

            Button(
                onClick = onGuardarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD84315),
                ),
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp),
                )
                Text(
                    text = "Guardar Bitácora",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
        }

        val mensaje = uiState.mensajeGuardado
        if (mensaje != null) {
            AlertDialog(
                onDismissRequest = onLimpiarMensaje,
                title = { Text("Bitácora Guardada") },
                text = { Text(mensaje) },
                confirmButton = {
                    TextButton(onClick = onLimpiarMensaje) {
                        Text("Aceptar")
                    }
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BitacoraScreenPreview() {
    val sampleReport = DailyReport(
        studentId = 101,
        studentName = "Lucas Martínez López",
        groupId = 1,
    )
    val sampleState = BitacoraUiState(
        isLoading = false,
        selectedStudent = Student(101, "Lucas Martínez López", 5),
        currentReport = sampleReport,
    )

    CrystalGuardTheme {
        BitacoraContent(
            uiState = sampleState,
            onSelectStudent = {},
            onSelectSection = {},
            onDateChange = {},
            onUpdateIngreso = {},
            onUpdateEstadia = {},
            onUpdateEgreso = {},
            onGuardarClick = {},
            onLimpiarMensaje = {},
        )
    }
}
