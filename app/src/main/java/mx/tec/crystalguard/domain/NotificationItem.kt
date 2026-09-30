package mx.tec.crystalguard.domain

enum class NotificationCategory(val label: String) {
    HOY("Hoy"),
    ANTERIORES("Anteriores"),
}

data class NotificationItem(
    val id: Int,
    val title: String,
    val person: String,
    val group: String,
    val message: String,
    val dateCategory: NotificationCategory = NotificationCategory.HOY,
    val timeText: String = "",
    val isRead: Boolean = false,
)
