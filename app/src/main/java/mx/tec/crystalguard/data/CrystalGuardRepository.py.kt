package mx.tec.crystalguard.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CrueltyFree
import androidx.compose.material.icons.filled.EmojiNature
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import mx.tec.crystalguard.domain.Announcement
import mx.tec.crystalguard.domain.Group

class CrystalGuardRepository private constructor() {

    companion object {
        val instance: CrystalGuardRepository by lazy { CrystalGuardRepository() }
    }

    private val groupData = listOf(
        Group(1, "Abejitas", "Gpo 302", Icons.Filled.EmojiNature, Color.Yellow),
        Group(2, "Estrellitas", "Gpo 101", Icons.Filled.Star, Color.Cyan),
        Group(3, "Conejitos", "Gpo 204", Icons.Filled.CrueltyFree, Color.Magenta),
    )

    private val announcementsData = mutableListOf(
        Announcement(
            id = 1,
            groupId = 1,
            title = "Suspensión de clases por día festivo",
            author = "Ana Martínez (Educadora)",
            date = "24 Oct 2024",
            content = "Estimada comunidad escolar, se les informa que el día de mañana no habrá labores escolares con motivo del día festivo oficial.",
        ),
        Announcement(
            id = 2,
            groupId = 1,
            title = "Taller de pintura con padres de familia",
            author = "Ana Martínez (Educadora)",
            date = "18 Oct 2024",
            content = "Los invitamos cordialmente a participar en nuestro taller interactivo de pintura junto a los alumnos.",
        ),
        Announcement(
            id = 3,
            groupId = 1,
            title = "Reunión trimestral de seguimiento pedagógico",
            author = "Dirección Escolar",
            date = "10 Oct 2024",
            content = "Estimados tutores, los convocamos a la sesión de evaluación del primer periodo pedagógico en el salón principal.",
        ),
    )

    fun getAll(): List<Group> = groupData
    fun getById(id: Int): Group? = groupData.firstOrNull { it.id == id }

    fun getAnnouncementsByGroupId(groupId: Int): List<Announcement> {
        return announcementsData.filter { it.groupId == groupId }
    }

    fun addAnnouncement(
        groupId: Int,
        title: String,
        author: String,
        date: String,
        content: String,
    ): Announcement {
        val newId = (announcementsData.maxOfOrNull { it.id } ?: 0) + 1
        val newAnnouncement = Announcement(
            id = newId,
            groupId = groupId,
            title = title,
            author = author,
            date = date,
            content = content,
        )
        announcementsData.add(0, newAnnouncement)
        return newAnnouncement
    }
}
