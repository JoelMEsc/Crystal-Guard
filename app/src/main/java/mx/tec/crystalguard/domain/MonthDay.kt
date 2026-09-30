package mx.tec.crystalguard.domain

data class MonthDay(
    val dayNumber: Int,
    val isCurrentMonth: Boolean,
    val isSelected: Boolean = false,
)
