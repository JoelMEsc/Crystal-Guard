package mx.tec.crystalguard.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.crystalguard.data.PeopleRepository
import mx.tec.crystalguard.domain.people.Educator
import mx.tec.crystalguard.domain.people.Student

data class PeopleUiState(
    val groupId: Int = 1,
    val isLoading: Boolean = true,
    val educator: Educator? = null,
    val students: List<Student> = emptyList(),
)

class PeopleViewModel(
    private val repository: PeopleRepository = PeopleRepository(),
) : ViewModel() {

    var uiState by mutableStateOf(PeopleUiState())
        private set

    fun cargarPersonas(groupId: Int) {
        uiState = uiState.copy(isLoading = true, groupId = groupId)

        val educatorFound = repository.getEducatorForGroup(groupId)
        val studentsFound = repository.getStudentsForGroup(groupId)

        uiState = PeopleUiState(
            groupId = groupId,
            isLoading = false,
            educator = educatorFound,
            students = studentsFound,
        )
    }

    fun onStudentClick(student: Student) {
        // Acción al presionar un estudiante
    }

    fun onEducatorClick(educator: Educator) {
        // Acción al presionar el educador
    }
}
