package mx.tec.crystalguard.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CrueltyFree
import androidx.compose.material.icons.filled.EmojiNature
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import mx.tec.crystalguard.R
import mx.tec.crystalguard.domain.Announcement
import mx.tec.crystalguard.domain.GalleryImage
import mx.tec.crystalguard.domain.Group
import mx.tec.crystalguard.domain.StudentAttendance

object CrystalGuardRepository {

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

    private val attendanceData = mutableListOf(
        StudentAttendance(
            id = 1,
            groupId = 1,
            studentName = "Leo Gómez",
            dejoPersona = "Marta Gómez (Madre)",
            recogioPersona = "Carlos Gómez (Padre)",
            dejoOpciones = listOf("Marta Gómez (Madre)", "Carlos Gómez (Padre)", "Roberto Gómez (Abuelo)"),
            recogioOpciones = listOf("Carlos Gómez (Padre)", "Marta Gómez (Madre)", "Roberto Gómez (Abuelo)"),
            llego = true,
            seFue = true,
            noAsistio = false,
        ),
        StudentAttendance(
            id = 2,
            groupId = 1,
            studentName = "Sofía Morales",
            dejoPersona = "Elena Morales (Madre)",
            recogioPersona = "Pendiente de salida",
            dejoOpciones = listOf("Elena Morales (Madre)", "David Morales (Padre)"),
            recogioOpciones = listOf("Pendiente de salida", "Elena Morales (Madre)", "David Morales (Padre)"),
            llego = true,
            seFue = false,
            noAsistio = false,
        ),
        StudentAttendance(
            id = 3,
            groupId = 1,
            studentName = "Mateo Torres",
            dejoPersona = "Lucía Ramos (Tía)",
            recogioPersona = "Pendiente de salida",
            dejoOpciones = listOf("Lucía Ramos (Tía)", "Jorge Torres (Padre)"),
            recogioOpciones = listOf("Pendiente de salida", "Jorge Torres (Padre)", "Lucía Ramos (Tía)"),
            llego = true,
            seFue = false,
            noAsistio = false,
        ),
        StudentAttendance(
            id = 4,
            groupId = 1,
            studentName = "Valentina Díaz",
            dejoPersona = "No asistió hoy",
            recogioPersona = "No asistió hoy",
            dejoOpciones = listOf("No asistió hoy", "Carmen Díaz (Madre)"),
            recogioOpciones = listOf("No asistió hoy", "Carmen Díaz (Madre)"),
            llego = false,
            seFue = false,
            noAsistio = true,
        ),
    )

    private val galleryData = listOf(
        GalleryImage(
            id = 1,
            groupId = 1,
            title = "Actividad de Pintura",
            date = "18 Oct 2024",
            imageRes = R.drawable.galeria_img_1,
        ),
        GalleryImage(
            id = 2,
            groupId = 1,
            title = "Festival de Otoño",
            date = "15 Oct 2024",
            imageRes = R.drawable.galeria_img_2,
        ),
        GalleryImage(
            id = 3,
            groupId = 1,
            title = "Juegos en el Patio",
            date = "10 Oct 2024",
            imageRes = R.drawable.galeria_img_3,
        ),
        GalleryImage(
            id = 4,
            groupId = 1,
            title = "Taller de Lectura",
            date = "05 Oct 2024",
            imageRes = R.drawable.galeria_img_4,
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

    fun getAttendanceByGroupId(groupId: Int): List<StudentAttendance> {
        val items = attendanceData.filter { it.groupId == groupId }
        if (items.isEmpty() && groupId != 1) {
            return listOf(
                StudentAttendance(
                    id = 10 + groupId,
                    groupId = groupId,
                    studentName = "Alumno Ejemplo",
                    dejoPersona = "Tutor 1",
                    recogioPersona = "Pendiente de salida",
                    dejoOpciones = listOf("Tutor 1", "Tutor 2"),
                    recogioOpciones = listOf("Pendiente de salida", "Tutor 1", "Tutor 2"),
                    llego = true,
                    seFue = false,
                    noAsistio = false,
                ),
            )
        }
        return items
    }

    fun updateAttendanceList(groupId: Int, updatedList: List<StudentAttendance>) {
        attendanceData.removeAll { it.groupId == groupId }
        attendanceData.addAll(updatedList)
    }

    fun getGalleryByGroupId(groupId: Int): List<GalleryImage> {
        val items = galleryData.filter { it.groupId == groupId }
        return if (items.isNotEmpty()) items else galleryData.map { it.copy(groupId = groupId) }
    }
}
