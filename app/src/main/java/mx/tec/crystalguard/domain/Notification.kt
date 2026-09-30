package mx.tec.crystalguard.domain

data class Notification (
    val subject: String,
    val from: String,
    val group: String,
    val description: String,
    val date: String
) {}