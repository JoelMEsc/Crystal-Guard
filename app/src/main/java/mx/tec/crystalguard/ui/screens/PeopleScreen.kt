package mx.tec.crystalguard.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.people.Educator
import mx.tec.crystalguard.domain.people.Student
import mx.tec.crystalguard.ui.components.CargandoView
import mx.tec.crystalguard.ui.components.PersonCard
import mx.tec.crystalguard.ui.state.PeopleUiState
import mx.tec.crystalguard.ui.state.PeopleViewModel
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun PeopleScreen(
    viewModel: PeopleViewModel,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    PeopleContent(
        uiState = viewModel.uiState,
        onStudentClick = { student -> viewModel.onStudentClick(student, onNavigate) },
        onEducatorClick = viewModel::onEducatorClick,
        modifier = modifier,
    )
}

@Composable
fun PeopleContent(
    uiState: PeopleUiState,
    onStudentClick: (Student) -> Unit,
    onEducatorClick: (Educator) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isLoading) {
        CargandoView(modifier = modifier.fillMaxSize())
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                Text(
                    text = "Alumnos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            if (uiState.students.isEmpty()) {
                item {
                    Text(
                        text = "No hay alumnos registrados en este grupo.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )
                }
            } else {
                items(
                    items = uiState.students,
                    key = { student -> student.id },
                ) { student ->
                    PersonCard(
                        student = student,
                        onClick = { onStudentClick(student) },
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    text = "Profesor/a",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            item {
                val educator = uiState.educator
                if (educator != null) {
                    PersonCard(
                        educator = educator,
                        onClick = { onEducatorClick(educator) },
                    )
                } else {
                    Text(
                        text = "Sin profesor asignado.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PeopleScreenPreview() {
    val sampleState = PeopleUiState(
        isLoading = false,
        educator = Educator(
            id = 1,
            name = "Profra. María Elena García",
            age = 34,
            roleTitle = "Educadora Titular",
        ),
        students = listOf(
            Student(id = 101, name = "Lucas Martínez López", age = 5),
            Student(id = 102, name = "Sofía Hernández Cruz", age = 4),
            Student(id = 103, name = "Mateo Gómez Ramírez", age = 5),
        ),
    )

    CrystalGuardTheme {
        PeopleContent(
            uiState = sampleState,
            onStudentClick = {},
            onEducatorClick = {},
        )
    }
}
