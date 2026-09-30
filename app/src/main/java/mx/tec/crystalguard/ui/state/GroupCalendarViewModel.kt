package mx.tec.crystalguard.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.crystalguard.data.CalendarRepository
import mx.tec.crystalguard.data.MonthCalendarInfo
import mx.tec.crystalguard.domain.CalendarActivity
import mx.tec.crystalguard.domain.MonthDay

data class GroupCalendarUiState(
    val groupId: Int = 1,
    val currentHeaderTitle: String = "Septiembre 2026",
    val monthDays: List<MonthDay> = emptyList(),
    val selectedDayNumber: Int = 29,
    val activities: List<CalendarActivity> = emptyList(),
)

class GroupCalendarViewModel(
    private val repository: CalendarRepository = CalendarRepository(),
) : ViewModel() {

    private val availableMonths: List<MonthCalendarInfo> = repository.getAvailableMonths()
    private var currentMonthIndex = 1
    private var currentGroupId: Int = 1

    var uiState by mutableStateOf(GroupCalendarUiState())
        private set

    fun cargarCalendarioGrupo(groupId: Int) {
        currentGroupId = groupId
        loadCurrentMonth()
    }

    fun selectDay(selectedDay: MonthDay) {
        if (!selectedDay.isCurrentMonth) return

        val updatedDays = uiState.monthDays.map { day ->
            day.copy(isSelected = (day.dayNumber == selectedDay.dayNumber && day.isCurrentMonth))
        }
        val dayActivities = repository.getActivitiesForGroupAndDay(currentGroupId, selectedDay.dayNumber)
        uiState = uiState.copy(
            monthDays = updatedDays,
            selectedDayNumber = selectedDay.dayNumber,
            activities = dayActivities,
        )
    }

    fun previousMonth() {
        if (currentMonthIndex > 0) {
            currentMonthIndex--
            loadCurrentMonth()
        }
    }

    fun nextMonth() {
        if (currentMonthIndex < availableMonths.lastIndex) {
            currentMonthIndex++
            loadCurrentMonth()
        }
    }

    private fun loadCurrentMonth() {
        val monthInfo = availableMonths.getOrNull(currentMonthIndex) ?: availableMonths.first()
        val selectedDay = monthInfo.days.firstOrNull { it.isSelected && it.isCurrentMonth }
            ?: monthInfo.days.first { it.isCurrentMonth }

        val dayActivities = repository.getActivitiesForGroupAndDay(currentGroupId, selectedDay.dayNumber)

        uiState = uiState.copy(
            groupId = currentGroupId,
            currentHeaderTitle = monthInfo.monthYearTitle,
            monthDays = monthInfo.days,
            selectedDayNumber = selectedDay.dayNumber,
            activities = dayActivities,
        )
    }
}
