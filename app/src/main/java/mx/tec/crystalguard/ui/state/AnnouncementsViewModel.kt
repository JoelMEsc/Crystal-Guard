package mx.tec.crystalguard.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.crystalguard.data.CrystalGuardRepository
import mx.tec.crystalguard.domain.Announcement
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class AnnouncementsUiState(
    val isLoading: Boolean = true,
    val announcements: List<Announcement> = emptyList(),
    val selectedAnnouncement: Announcement? = null,
    val isCreandoAnuncio: Boolean = false,
    val errorMessage: String? = null,
)

class AnnouncementsViewModel(
    private val repository: CrystalGuardRepository = CrystalGuardRepository.instance,
) : ViewModel() {

    var uiState by mutableStateOf(AnnouncementsUiState())
        private set

    private var currentGroupId: Int = 1

    fun cargar(groupId: Int) {
        currentGroupId = groupId
        uiState = uiState.copy(isLoading = true)
        val list = repository.getAnnouncementsByGroupId(groupId)
        uiState = AnnouncementsUiState(
            isLoading = false,
            announcements = list,
        )
    }

    fun abrirDialogoCrear() {
        uiState = uiState.copy(isCreandoAnuncio = true)
    }

    fun cerrarDialogoCrear() {
        uiState = uiState.copy(isCreandoAnuncio = false)
    }

    fun publicarAnuncioPersonalizado(title: String, content: String) {
        val fechaActual = obtenerFechaActual()
        val autorAutomatico = "Eduardo Castillo (Educador)"

        repository.addAnnouncement(
            groupId = currentGroupId,
            title = title,
            author = autorAutomatico,
            date = fechaActual,
            content = content,
        )

        uiState = uiState.copy(
            isCreandoAnuncio = false,
            announcements = repository.getAnnouncementsByGroupId(currentGroupId),
        )
    }

    fun seleccionarAnuncio(announcement: Announcement?) {
        uiState = uiState.copy(selectedAnnouncement = announcement)
    }

    private fun obtenerFechaActual(): String {
        return try {
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.forLanguageTag("es-MX"))
            sdf.format(Date())
        } catch (_: Exception) {
            "29 Sep 2026"
        }
    }
}
