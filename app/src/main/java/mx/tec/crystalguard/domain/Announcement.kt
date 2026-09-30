package mx.tec.crystalguard.domain

data class Announcement(
    val id: Int,
    val groupId: Int,
    val title: String,
    val author: String,
    val date: String,
    val content: String = "",
)
