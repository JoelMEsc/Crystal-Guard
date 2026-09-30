package mx.tec.crystalguard.domain

enum class NoteType(val label: String) {
    SALUD("Salud"),
    ANIMO("Ánimo"),
    JUEGOS("Juegos"),
    RESTROOM("Baño"),
    FOOD("Alimentos"),
    MISCELANEOS("Misceláneos"),
}

data class StudentNote(
    val id: Int,
    val type: NoteType,
    val timeText: String? = null,
    val content: String,
)
