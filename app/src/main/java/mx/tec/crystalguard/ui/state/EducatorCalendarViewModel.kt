package mx.tec.crystalguard.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.crystalguard.data.CalendarRepository
import mx.tec.crystalguard.domain.CalendarActivity
import mx.tec.crystalguard.domain.WeekDay
import mx.tec.crystalguard.domain.WeekInfo

data class EducatorCalendarUiState(
    val currentHeaderTitle: String = "",
    val weekDays: List<WeekDay> = emptyList(),
    val selectedDayNumber: Int = 29,
    val activities: List<CalendarActivity> = emptyList(),
)

class EducatorCalendarViewModel(
    private val repository: CalendarRepository = CalendarRepository(),
) : ViewModel() {

    private val availableWeeks: List<WeekInfo> = repository.getAvailableWeeks()
    private var currentWeekIndex = 1

    var uiState by mutableStateOf(EducatorCalendarUiState())
        private set

    init {
        loadCurrentWeek()
    }

    fun selectDay(selectedDay: WeekDay) {
        val updatedDays = uiState.weekDays.map { day ->
            day.copy(isSelected = ((day.number == selectedDay.number) && (day.letter == selectedDay.letter)))
        }
        val dayActivities = repository.getActivitiesForDay(selectedDay.number)
        uiState = uiState.copy(
            weekDays = updatedDays,
            selectedDayNumber = selectedDay.number,
            activities = dayActivities,
        )
    }

    fun previousWeek() {
        if (currentWeekIndex > 0) {
            currentWeekIndex--
            loadCurrentWeek()
        }
    }

    fun nextWeek() {
        if (currentWeekIndex < availableWeeks.lastIndex) {
            currentWeekIndex++
            loadCurrentWeek()
        }
    }

    private fun loadCurrentWeek() {
        val weekInfo = availableWeeks.getOrNull(currentWeekIndex) ?: availableWeeks.first()
        val selectedDay = weekInfo.days.firstOrNull { it.isSelected } ?: weekInfo.days.first()
        val dayActivities = repository.getActivitiesForDay(selectedDay.number)

        uiState = uiState.copy(
            currentHeaderTitle = weekInfo.headerTitle,
            weekDays = weekInfo.days,
            selectedDayNumber = selectedDay.number,
            activities = dayActivities,
        )
    }
}
