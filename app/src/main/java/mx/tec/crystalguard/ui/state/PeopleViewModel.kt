package mx.tec.crystalguard.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.crystalguard.data.CrystalGuardRepository
import mx.tec.crystalguard.data.GroupPeople
import mx.tec.crystalguard.data.PersonInfo
import mx.tec.crystalguard.domain.Group

data class PeopleUiState(
    val isLoading: Boolean = true,
    val group: Group? = null,
    val educators: List<PersonInfo> = emptyList(),
    val students: List<PersonInfo> = emptyList(),
    val errorMessage: String? = null,
)

class PeopleViewModel(
    private val repository: CrystalGuardRepository = CrystalGuardRepository(),
) : ViewModel() {

    var uiState by mutableStateOf(PeopleUiState())
        private set

    fun cargarPersonas(groupId: Int) {
        uiState = uiState.copy(isLoading = true)
        val group = repository.getById(groupId)
        val people = repository.getPeopleForGroup(groupId)

        if (group != null && people != null) {
            uiState = PeopleUiState(
                isLoading = false,
                group = group,
                educators = people.educators,
                students = people.students,
            )
        } else if (group != null) {
            uiState = PeopleUiState(
                isLoading = false,
                group = group,
                educators = emptyList(),
                students = emptyList(),
            )
        } else {
            uiState = PeopleUiState(
                isLoading = false,
                errorMessage = "Grupo no encontrado",
            )
        }
    }
}
