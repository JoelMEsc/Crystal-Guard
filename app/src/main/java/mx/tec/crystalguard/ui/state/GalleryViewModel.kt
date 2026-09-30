package mx.tec.crystalguard.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.crystalguard.data.CrystalGuardRepository
import mx.tec.crystalguard.domain.GalleryImage

data class GalleryUiState(
    val isLoading: Boolean = true,
    val images: List<GalleryImage> = emptyList(),
    val selectedImage: GalleryImage? = null,
    val errorMessage: String? = null,
)

class GalleryViewModel(
    private val repository: CrystalGuardRepository = CrystalGuardRepository,
) : ViewModel() {

    var uiState by mutableStateOf(GalleryUiState())
        private set

    fun cargar(groupId: Int) {
        uiState = uiState.copy(isLoading = true)
        val list = repository.getGalleryByGroupId(groupId)
        uiState = GalleryUiState(
            isLoading = false,
            images = list,
        )
    }

    fun seleccionarImagen(image: GalleryImage?) {
        uiState = uiState.copy(selectedImage = image)
    }
}
