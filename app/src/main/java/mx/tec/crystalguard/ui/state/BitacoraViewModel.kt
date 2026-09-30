package mx.tec.crystalguard.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.crystalguard.data.BitacoraRepository
import mx.tec.crystalguard.data.PeopleRepository
import mx.tec.crystalguard.domain.BitacoraSection
import mx.tec.crystalguard.domain.DailyReport
import mx.tec.crystalguard.domain.EgresoData
import mx.tec.crystalguard.domain.EstadiaData
import mx.tec.crystalguard.domain.IngresoData
import mx.tec.crystalguard.domain.people.Student

data class BitacoraUiState(
    val groupId: Int = 1,
    val dateText: String = "29 de Septiembre, 2026",
    val selectedSection: BitacoraSection = BitacoraSection.INGRESO,
    val groupStudents: List<Student> = emptyList(),
    val selectedStudent: Student? = null,
    val currentReport: DailyReport? = null,
    val isLoading: Boolean = true,
    val mensajeGuardado: String? = null,
)

class BitacoraViewModel(
    private val bitacoraRepository: BitacoraRepository = BitacoraRepository(),
    private val peopleRepository: PeopleRepository = PeopleRepository(),
) : ViewModel() {

    var uiState by mutableStateOf(BitacoraUiState())
        private set

    fun cargarBitacora(groupId: Int) {
        uiState = uiState.copy(isLoading = true, groupId = groupId)

        val students = peopleRepository.getStudentsForGroup(groupId)
        val initialStudent = students.firstOrNull()

        val report = if (initialStudent != null) {
            bitacoraRepository.getReport(
                studentId = initialStudent.id,
                studentName = initialStudent.name,
                groupId = groupId,
                dateText = uiState.dateText,
            )
        } else null

        uiState = BitacoraUiState(
            groupId = groupId,
            dateText = uiState.dateText,
            selectedSection = BitacoraSection.INGRESO,
            groupStudents = students,
            selectedStudent = initialStudent,
            currentReport = report,
            isLoading = false,
        )
    }

    fun seleccionarEstudiante(student: Student) {
        val report = bitacoraRepository.getReport(
            studentId = student.id,
            studentName = student.name,
            groupId = uiState.groupId,
            dateText = uiState.dateText,
        )
        uiState = uiState.copy(
            selectedStudent = student,
            currentReport = report,
        )
    }

    fun cambiarSeccion(section: BitacoraSection) {
        uiState = uiState.copy(selectedSection = section)
    }

    fun cambiarFecha(nuevaFecha: String) {
        val student = uiState.selectedStudent ?: return
        val report = bitacoraRepository.getReport(
            studentId = student.id,
            studentName = student.name,
            groupId = uiState.groupId,
            dateText = nuevaFecha,
        )
        uiState = uiState.copy(
            dateText = nuevaFecha,
            currentReport = report,
        )
    }

    fun actualizarIngreso(nuevoIngreso: IngresoData) {
        val current = uiState.currentReport ?: return
        val updated = current.copy(ingreso = nuevoIngreso)
        uiState = uiState.copy(currentReport = updated)
    }

    fun actualizarEstadia(nuevaEstadia: EstadiaData) {
        val current = uiState.currentReport ?: return
        val updated = current.copy(estadia = nuevaEstadia)
        uiState = uiState.copy(currentReport = updated)
    }

    fun actualizarEgreso(nuevoEgreso: EgresoData) {
        val current = uiState.currentReport ?: return
        val updated = current.copy(egreso = nuevoEgreso)
        uiState = uiState.copy(currentReport = updated)
    }

    fun guardarBitacora() {
        val report = uiState.currentReport ?: return
        bitacoraRepository.saveReport(report)
        uiState = uiState.copy(mensajeGuardado = "Bitácora guardada correctamente para ${report.studentName}.")
    }

    fun limpiarMensaje() {
        uiState = uiState.copy(mensajeGuardado = null)
    }
}
