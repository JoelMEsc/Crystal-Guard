package mx.tec.crystalguard.domain

data class WeekDay(
    val letter: String,
    val number: Int,
    val isSelected: Boolean = false,
)
