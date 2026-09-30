package mx.tec.crystalguard.domain

data class StudentAttendance(
    val id: Int,
    val groupId: Int,
    val studentName: String,
    val dejoPersona: String,
    val recogioPersona: String,
    val dejoOpciones: List<String> = emptyList(),
    val recogioOpciones: List<String> = emptyList(),
    val llego: Boolean = false,
    val seFue: Boolean = false,
    val noAsistio: Boolean = false,
)
