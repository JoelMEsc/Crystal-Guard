package mx.tec.crystalguard.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.crystalguard.data.FileRepository
import mx.tec.crystalguard.domain.FileInfo

data class ClassMaterialUiState(
    val groupId: Int = 1,
    val isLoading: Boolean = true,
    val books: List<FileInfo> = emptyList(),
    val worksheets: List<FileInfo> = emptyList(),
)

class ClassMaterialViewModel(
    private val repository: FileRepository = FileRepository(),
) : ViewModel() {

    var uiState by mutableStateOf(ClassMaterialUiState())
        private set

    fun cargarMateriales(groupId: Int) {
        uiState = uiState.copy(isLoading = true, groupId = groupId)

        val groupBooks = repository.getBooksForGroup(groupId)
        val groupWorksheets = repository.getWorksheetsForGroup(groupId)

        uiState = ClassMaterialUiState(
            groupId = groupId,
            isLoading = false,
            books = groupBooks,
            worksheets = groupWorksheets,
        )
    }

    fun onDeleteBook(file: FileInfo) {
        val updated = uiState.books.filterNot { it.id == file.id }
        uiState = uiState.copy(books = updated)
    }

    fun onDownloadBook(file: FileInfo) {
        // Acción mock de descarga de libro
    }

    fun onUploadBook() {
        // Acción mock de subida de libro
    }

    fun onDeleteWorksheet(file: FileInfo) {
        val updated = uiState.worksheets.filterNot { it.id == file.id }
        uiState = uiState.copy(worksheets = updated)
    }

    fun onDownloadWorksheet(file: FileInfo) {
        // Acción mock de descarga de hoja de trabajo
    }

    fun onUploadWorksheet() {
        // Acción mock de subida de hoja de trabajo
    }
}
