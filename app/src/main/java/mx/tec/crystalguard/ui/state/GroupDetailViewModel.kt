package mx.tec.crystalguard.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.crystalguard.data.CrystalGuardRepository
import mx.tec.crystalguard.domain.Group

class GroupDetailViewModel(
    private val repository: CrystalGuardRepository = CrystalGuardRepository(),
) : ViewModel() {

    var group by mutableStateOf<Group?>(null)
        private set

    fun cargar(groupId: Int) {
        group = repository.getById(groupId)
    }
}
