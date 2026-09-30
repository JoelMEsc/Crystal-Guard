package mx.tec.crystalguard.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import mx.tec.crystalguard.domain.WeekDay
import mx.tec.crystalguard.ui.components.SlidingSelector
import mx.tec.crystalguard.ui.components.calendar.CalendarActivityCard
import mx.tec.crystalguard.ui.components.calendar.WeekSelector
import mx.tec.crystalguard.ui.state.EducatorCalendarUiState
import mx.tec.crystalguard.ui.state.EducatorCalendarViewModel
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun EducatorCalendarScreen(
    viewModel: EducatorCalendarViewModel,
    modifier: Modifier = Modifier,
) {
    EducatorCalendarContent(
        uiState = viewModel.uiState,
        onDaySelected = viewModel::selectDay,
        onPreviousWeek = viewModel::previousWeek,
        onNextWeek = viewModel::nextWeek,
        modifier = modifier,
    )
}

@Composable
fun EducatorCalendarContent(
    uiState: EducatorCalendarUiState,
    onDaySelected: (WeekDay) -> Unit,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        SlidingSelector(
            text = uiState.currentHeaderTitle,
            onClickLeft = onPreviousWeek,
            onClickRight = onNextWeek,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        WeekSelector(
            week = uiState.weekDays,
            onDayClick = onDaySelected,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Actividades del Día ${uiState.selectedDayNumber}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState.activities.isEmpty()) {
            Text(
                text = "No hay actividades programadas para este día.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = uiState.activities,
                    key = { activity -> "${activity.groupId}_${activity.title}_${activity.startTime}" },
                ) { activity ->
                    CalendarActivityCard(
                        activity = activity,
                        onClick = { /* Acción futura al presionar actividad */ },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EducatorCalendarScreenPreview() {
    val sampleState = EducatorCalendarUiState()
    CrystalGuardTheme {
        EducatorCalendarContent(
            uiState = sampleState,
            onDaySelected = {},
            onPreviousWeek = {},
            onNextWeek = {},
        )
    }
}
