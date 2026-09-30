package mx.tec.crystalguard.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.crystalguard.data.StudentDetailRepository
import mx.tec.crystalguard.domain.FileInfo
import mx.tec.crystalguard.domain.NoteType
import mx.tec.crystalguard.domain.StudentDetail
import mx.tec.crystalguard.domain.StudentNote

data class StudentDetailUiState(
    val studentId: Int = 101,
    val isLoading: Boolean = true,
    val studentDetail: StudentDetail? = null,
    val isAddingNote: Boolean = false,
)

class StudentDetailViewModel(
    private val repository: StudentDetailRepository = StudentDetailRepository(),
) : ViewModel() {

    var uiState by mutableStateOf(StudentDetailUiState())
        private set

    private var nextNoteId = 500
    private var nextFileId = 900

    fun cargarDetalleEstudiante(studentId: Int) {
        uiState = uiState.copy(isLoading = true, studentId = studentId)

        val detail = repository.getStudentDetail(studentId)

        uiState = StudentDetailUiState(
            studentId = studentId,
            isLoading = false,
            studentDetail = detail,
        )
    }

    fun onRemoveNote(noteId: Int) {
        val current = uiState.studentDetail ?: return
        val updatedNotes = current.notes.filterNot { it.id == noteId }
        uiState = uiState.copy(
            studentDetail = current.copy(notes = updatedNotes),
        )
    }

    fun onAbrirAgregarNota() {
        uiState = uiState.copy(isAddingNote = true)
    }

    fun onCerrarAgregarNota() {
        uiState = uiState.copy(isAddingNote = false)
    }

    fun onAgregarNota(type: NoteType, timeText: String, content: String) {
        val current = uiState.studentDetail ?: return
        val newNote = StudentNote(
            id = ++nextNoteId,
            type = type,
            timeText = timeText.ifBlank { null },
            content = content,
        )
        uiState = uiState.copy(
            studentDetail = current.copy(notes = current.notes + newNote),
            isAddingNote = false,
        )
    }

    fun onDeleteFile(file: FileInfo) {
        val current = uiState.studentDetail ?: return
        val updatedFiles = current.attachedFiles.filterNot { it.id == file.id }
        uiState = uiState.copy(
            studentDetail = current.copy(attachedFiles = updatedFiles),
        )
    }

    fun onDownloadFile(file: FileInfo) {
        // Acción mock de descarga
    }

    fun onUploadFile() {
        val current = uiState.studentDetail ?: return
        val newFile = FileInfo(
            id = ++nextFileId,
            name = "Documento_Nuevo_${nextFileId}.pdf",
            sizeText = "1.5 MB",
        )
        uiState = uiState.copy(
            studentDetail = current.copy(attachedFiles = current.attachedFiles + newFile),
        )
    }
}
