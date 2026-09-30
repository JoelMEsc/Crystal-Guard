package mx.tec.crystalguard.ui.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.People
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.crystalguard.data.CrystalGuardRepository
import mx.tec.crystalguard.domain.Group
import mx.tec.crystalguard.ui.components.SelectionItemData
import mx.tec.crystalguard.ui.navigation.Route

data class GroupDetailUiState(
    val isLoading: Boolean = true,
    val group: Group? = null,
    val selectionItems: List<SelectionItemData> = emptyList(),
    val errorMessage: String? = null,
)

class GroupDetailViewModel(
    private val repository: CrystalGuardRepository = CrystalGuardRepository.instance,
) : ViewModel() {

    var uiState by mutableStateOf(GroupDetailUiState())
        private set

    fun cargarGrupo(groupId: Int) {
        uiState = uiState.copy(isLoading = true)
        val groupFound = repository.getById(groupId)

        if (groupFound != null) {
            uiState = GroupDetailUiState(
                isLoading = false,
                group = groupFound,
                selectionItems = listOf(
                    SelectionItemData(1, "Material de clase", Icons.AutoMirrored.Filled.MenuBook),
                    SelectionItemData(2, "Pase de lista", Icons.Filled.AssignmentTurnedIn),
                    SelectionItemData(3, "Anuncios", Icons.Filled.Campaign),
                    SelectionItemData(4, "Bitácora", Icons.Filled.EditNote),
                    SelectionItemData(5, "Galería", Icons.Filled.Collections),
                    SelectionItemData(6, "Personas", Icons.Filled.People),
                    SelectionItemData(7, "Calendario", Icons.Filled.CalendarMonth),
                ),
            )
        } else {
            uiState = GroupDetailUiState(
                isLoading = false,
                errorMessage = "Grupo no encontrado",
            )
        }
    }

    fun onItemClick(item: SelectionItemData, onNavigate: (String) -> Unit) {
        val groupId = uiState.group?.id ?: return
        val targetRoute = when (item.id) {
            3 -> Route.announcements(groupId) // Solo 'Anuncios' está implementado por ahora
            else -> null // Las demás opciones aún no tienen pantalla en NavHost
        }
        if (targetRoute != null) {
            onNavigate(targetRoute)
        }
    }
}
