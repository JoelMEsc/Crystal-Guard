package mx.tec.crystalguard.ui.screens

import androidx.compose.foundation.layout.Arrangement
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
import mx.tec.crystalguard.domain.MonthDay
import mx.tec.crystalguard.ui.components.SlidingSelector
import mx.tec.crystalguard.ui.components.calendar.CalendarActivityCard
import mx.tec.crystalguard.ui.components.calendar.MonthCalendarGrid
import mx.tec.crystalguard.ui.state.GroupCalendarUiState
import mx.tec.crystalguard.ui.state.GroupCalendarViewModel
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun GroupCalendarScreen(
    viewModel: GroupCalendarViewModel,
    modifier: Modifier = Modifier,
) {
    GroupCalendarContent(
        uiState = viewModel.uiState,
        onDaySelected = viewModel::selectDay,
        onPreviousMonth = viewModel::previousMonth,
        onNextMonth = viewModel::nextMonth,
        modifier = modifier,
    )
}

@Composable
fun GroupCalendarContent(
    uiState: GroupCalendarUiState,
    onDaySelected: (MonthDay) -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            SlidingSelector(
                text = uiState.currentHeaderTitle,
                onClickLeft = onPreviousMonth,
                onClickRight = onNextMonth,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            MonthCalendarGrid(
                days = uiState.monthDays,
                onDayClick = onDaySelected,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Text(
                text = "Actividades del Día ${uiState.selectedDayNumber}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        if (uiState.activities.isEmpty()) {
            item {
                Text(
                    text = "No hay actividades programadas para este grupo en este día.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                )
            }
        } else {
            items(
                items = uiState.activities,
                key = { activity -> "${activity.groupId}_${activity.title}_${activity.startTime}" },
            ) { activity ->
                CalendarActivityCard(
                    activity = activity,
                    onClick = { },
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GroupCalendarScreenPreview() {
    val sampleState = GroupCalendarUiState()
    CrystalGuardTheme {
        GroupCalendarContent(
            uiState = sampleState,
            onDaySelected = {},
            onPreviousMonth = {},
            onNextMonth = {},
        )
    }
}
