package mx.tec.crystalguard.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.crystalguard.data.CrystalGuardRepository
import mx.tec.crystalguard.domain.StudentAttendance

data class AttendanceUiState(
    val isLoading: Boolean = true,
    val students: List<StudentAttendance> = emptyList(),
    val dateText: String = "Jueves, 24 de Octubre",
    val isEditing: Boolean = true,
    val mensajeExito: String? = null,
    val mensajeAdvertencia: String? = null,
    val errorMessage: String? = null,
)

class AttendanceViewModel(
    private val repository: CrystalGuardRepository = CrystalGuardRepository.instance,
) : ViewModel() {

    var uiState by mutableStateOf(AttendanceUiState())
        private set

    private var currentGroupId: Int = 1

    fun cargar(groupId: Int) {
        currentGroupId = groupId
        uiState = uiState.copy(isLoading = true)
        val list = repository.getAttendanceByGroupId(groupId)
        uiState = AttendanceUiState(
            isLoading = false,
            students = list,
            dateText = "Jueves, 24 de Octubre",
            isEditing = true,
        )
    }

    fun toggleLlego(studentId: Int) {
        val updated = uiState.students.map { student ->
            if (student.id == studentId) {
                val newLlego = !student.llego
                student.copy(
                    llego = newLlego,
                    // Si desmarca la entrada, automáticamente se desmarca la salida
                    seFue = if (!newLlego) false else student.seFue,
                    noAsistio = if (newLlego) false else student.noAsistio,
                )
            } else student
        }
        uiState = uiState.copy(students = updated, mensajeAdvertencia = null)
    }

    fun toggleSeFue(studentId: Int) {
        val student = uiState.students.firstOrNull { it.id == studentId } ?: return

        // Regla: Solo se puede activar salida si se tiene activada la entrada
        if (!student.llego) {
            uiState = uiState.copy(
                mensajeAdvertencia = "No se puede registrar la Salida de ${student.studentName} si no ha registrado Entrada primero.",
            )
            return
        }

        val updated = uiState.students.map { s ->
            if (s.id == studentId) {
                s.copy(
                    seFue = !s.seFue,
                    noAsistio = false,
                )
            } else s
        }
        uiState = uiState.copy(students = updated, mensajeAdvertencia = null)
    }

    fun toggleNoAsistio(studentId: Int) {
        val updated = uiState.students.map { student ->
            if (student.id == studentId) {
                val newNoAsistio = !student.noAsistio
                if (newNoAsistio) {
                    student.copy(
                        llego = false,
                        seFue = false,
                        noAsistio = true,
                        dejoPersona = "No asistió hoy",
                        recogioPersona = "No asistió hoy",
                    )
                } else {
                    student.copy(noAsistio = false)
                }
            } else student
        }
        uiState = uiState.copy(students = updated, mensajeAdvertencia = null)
    }

    fun actualizarDejoPersona(studentId: Int, persona: String) {
        val updated = uiState.students.map { student ->
            if (student.id == studentId) {
                student.copy(dejoPersona = persona)
            } else student
        }
        uiState = uiState.copy(students = updated, mensajeAdvertencia = null)
    }

    fun actualizarRecogioPersona(studentId: Int, persona: String) {
        val updated = uiState.students.map { student ->
            if (student.id == studentId) {
                student.copy(recogioPersona = persona)
            } else student
        }
        uiState = uiState.copy(students = updated, mensajeAdvertencia = null)
    }

    fun toggleModoEdicion() {
        uiState = uiState.copy(isEditing = !uiState.isEditing)
    }

    fun guardarCambios() {
        // Validación 1: Alumnos con Entrada marcada sin tutor de entrega
        val conLlegadaSinTutor = uiState.students.filter {
            it.llego && (it.dejoPersona == "No asistió hoy" || it.dejoPersona.isBlank())
        }

        if (conLlegadaSinTutor.isNotEmpty()) {
            val nombres = conLlegadaSinTutor.joinToString(", ") { it.studentName }
            uiState = uiState.copy(
                mensajeAdvertencia = "Atención: Debe seleccionar quién dejó a: $nombres.",
            )
            return
        }

        // Validación 2: Alumnos con Salida marcada sin tutor de retiro
        val conSalidaSinTutor = uiState.students.filter {
            it.seFue && (it.recogioPersona == "Pendiente de salida" || it.recogioPersona == "No asistió hoy" || it.recogioPersona.isBlank())
        }

        if (conSalidaSinTutor.isNotEmpty()) {
            val nombres = conSalidaSinTutor.joinToString(", ") { it.studentName }
            uiState = uiState.copy(
                mensajeAdvertencia = "Atención: Debe seleccionar quién recogió a: $nombres.",
            )
            return
        }

        repository.updateAttendanceList(currentGroupId, uiState.students)
        uiState = uiState.copy(
            mensajeExito = "¡Pase de lista guardado exitosamente!",
            mensajeAdvertencia = null,
        )
    }

    fun limpiarMensajes() {
        uiState = uiState.copy(
            mensajeExito = null,
            mensajeAdvertencia = null,
        )
    }
}
