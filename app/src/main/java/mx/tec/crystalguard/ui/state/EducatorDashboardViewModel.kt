package mx.tec.crystalguard.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.crystalguard.data.CrystalGuardRepository
import mx.tec.crystalguard.domain.Group

class EducatorDashboardViewModel(
    private val repository: CrystalGuardRepository = CrystalGuardRepository()
) : ViewModel() {

    var groups by mutableStateOf<List<Group>>(repository.getAll())
        private set

    fun cargar(): List<Group> = repository.getAll()
}