package mx.tec.crystalguard.data

import mx.tec.crystalguard.domain.FileInfo
import mx.tec.crystalguard.domain.NoteType
import mx.tec.crystalguard.domain.StudentDetail
import mx.tec.crystalguard.domain.StudentNote
import mx.tec.crystalguard.domain.people.Student

class StudentDetailRepository {

    private val sampleDetails = mapOf(
        101 to StudentDetail(
            student = Student(id = 101, name = "Lucas Martínez López", age = 5, groupId = 1),
            groupName = "Abejitas (Gpo 302)",
            tutorName = "Laura López Fernández",
            tutorContact = "+52 55 1234 5678",
            notes = listOf(
                StudentNote(
                    id = 1,
                    type = NoteType.FOOD,
                    timeText = null,
                    content = "Alergia a los lácteos y nueces. Consumir únicamente los alimentos enviados de casa.",
                ),
                StudentNote(
                    id = 2,
                    type = NoteType.SALUD,
                    timeText = "12:00 PM",
                    content = "Administrar jarabe para la alergia (Dosis 5ml) junto con el almuerzo.",
                ),
                StudentNote(
                    id = 3,
                    type = NoteType.MISCELANEOS,
                    timeText = "02:00 PM",
                    content = "Su tutor pasará a recogerlo temprano por cita médica programada.",
                ),
            ),
            attachedFiles = listOf(
                FileInfo(301, "Aviso_Medico_Lucas.pdf", "1.4 MB"),
                FileInfo(302, "Ficha_Inscripcion_Lucas.pdf", "2.1 MB"),
            ),
        ),
        102 to StudentDetail(
            student = Student(id = 102, name = "Sofía Hernández Cruz", age = 4, groupId = 1),
            groupName = "Abejitas (Gpo 302)",
            tutorName = "Carlos Hernández Ramírez",
            tutorContact = "+52 55 9876 5432",
            notes = listOf(
                StudentNote(
                    id = 4,
                    type = NoteType.SALUD,
                    timeText = null,
                    content = "Usa anteojos para leer y dibujar. Asegurar que los traiga puestos durante actividades visuales.",
                ),
                StudentNote(
                    id = 5,
                    type = NoteType.ANIMO,
                    timeText = null,
                    content = "Suele sentirse insegura con ruidos fuertes; requiere acompañamiento durante simulacros.",
                ),
            ),
            attachedFiles = listOf(
                FileInfo(303, "Autorizacion_Paseo.pdf", "950 KB"),
            ),
        ),
    )

    fun getStudentDetail(studentId: Int): StudentDetail {
        return sampleDetails[studentId] ?: StudentDetail(
            student = Student(id = studentId, name = "Estudiante #$studentId", age = 5, groupId = 1),
            groupName = "Abejitas (Gpo 302)",
            tutorName = "Tutor Asignado",
            tutorContact = "+52 55 0000 0000",
            notes = listOf(
                StudentNote(
                    id = 99,
                    type = NoteType.MISCELANEOS,
                    timeText = null,
                    content = "Observación general: Recordar enviar mudas de ropa etiquetadas con el nombre del alumno.",
                ),
            ),
            attachedFiles = listOf(
                FileInfo(999, "Documento_Bienvenida.pdf", "1.0 MB"),
            ),
        )
    }
}
