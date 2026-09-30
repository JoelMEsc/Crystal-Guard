package mx.tec.crystalguard.domain

data class CalendarActivity(
    val groupId: Int,
    val groupName: String,
    val groupNumber: String,
    val title: String,
    val description: String,
    val startTime: String,
    val endTime: String,
)
