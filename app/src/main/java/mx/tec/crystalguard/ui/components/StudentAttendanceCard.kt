package mx.tec.crystalguard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.StudentAttendance

@Composable
fun StudentAttendanceCard(
    student: StudentAttendance,
    isEditing: Boolean,
    onToggleLlego: () -> Unit,
    onToggleSeFue: () -> Unit,
    onToggleNoAsistio: () -> Unit,
    onDejoSelected: (String) -> Unit,
    onRecogioSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Detectar advertencias visuales de tutores no asignados
    val advierteDejo = student.llego && (student.dejoPersona == "No asistió hoy" || student.dejoPersona.isBlank())
    val advierteRecogio = student.seFue && (student.recogioPersona == "Pendiente de salida" || student.recogioPersona == "No asistió hoy" || student.recogioPersona.isBlank())

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
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
            // Fila superior: Foto, Nombre e Íconos de estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Avatar circular
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(28.dp),
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Nombre del estudiante
                Text(
                    text = student.studentName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                )

                // Controles de estado (Llegó, Salida, Inasistencia)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    // Checkbox 1: Llegada (Entrada)
                    StatusCheckbox(
                        labelIcon = Icons.AutoMirrored.Filled.ArrowForward,
                        isChecked = student.llego,
                        activeColor = Color(0xFFD84315),
                        isEnabled = true,
                        onClick = onToggleLlego,
                    )

                    // Checkbox 2: Salida (SÓLO activo si student.llego == true)
                    StatusCheckbox(
                        labelIcon = Icons.AutoMirrored.Filled.ArrowBack,
                        isChecked = student.seFue,
                        activeColor = Color(0xFFD84315),
                        isEnabled = student.llego,
                        onClick = onToggleSeFue,
                    )

                    // Checkbox 3: Inasistencia
                    StatusCheckbox(
                        labelIcon = Icons.Filled.CalendarMonth,
                        isChecked = student.noAsistio,
                        activeColor = MaterialTheme.colorScheme.error,
                        isEnabled = true,
                        onClick = onToggleNoAsistio,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fila inferior: Dejó y Recogió
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                // Columna DEJÓ
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "➔ DEJÓ:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (advierteDejo) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    PersonaDropdownPill(
                        currentValue = student.dejoPersona,
                        options = student.dejoOpciones,
                        isEditing = isEditing,
                        isWarning = advierteDejo,
                        onOptionSelected = onDejoSelected,
                    )
                }

                // Columna RECOGIÓ
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "← RECOGIÓ:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (advierteRecogio) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    PersonaDropdownPill(
                        currentValue = student.recogioPersona,
                        options = student.recogioOpciones,
                        isEditing = isEditing,
                        isWarning = advierteRecogio,
                        onOptionSelected = onRecogioSelected,
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusCheckbox(
    labelIcon: androidx.compose.ui.graphics.vector.ImageVector,
    isChecked: Boolean,
    activeColor: Color,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    val iconTint = when {
        isChecked -> activeColor
        !isEnabled -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.25f)
        else -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = labelIcon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    if (isChecked) activeColor else Color.Transparent,
                )
                .border(
                    width = 1.5.dp,
                    color = when {
                        isChecked -> activeColor
                        !isEnabled -> MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    },
                    shape = RoundedCornerShape(8.dp),
                )
                .clickable(enabled = isEnabled || isChecked, onClick = onClick),
            contentAlignment = Alignment.Center,
        ) {
            if (isChecked) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp),
                )
            }
        }
    }
}

@Composable
private fun PersonaDropdownPill(
    currentValue: String,
    options: List<String>,
    isEditing: Boolean,
    isWarning: Boolean = false,
    onOptionSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (isWarning) MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
                .border(
                    width = if (isWarning) 1.dp else 0.dp,
                    color = if (isWarning) MaterialTheme.colorScheme.error else Color.Transparent,
                    shape = RoundedCornerShape(12.dp),
                )
                .clickable(enabled = isEditing && options.isNotEmpty()) {
                    expanded = true
                }
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = if (isWarning) "⚠️ Seleccionar" else currentValue,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = if (isWarning) FontWeight.Bold else FontWeight.Medium,
                color = if (isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )
            if (isEditing && options.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Cambiar persona",
                    tint = if (isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp),
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                )
            }
        }
    }
}
