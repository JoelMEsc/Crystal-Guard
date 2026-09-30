package mx.tec.crystalguard.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.crystalguard.data.NotificationRepository
import mx.tec.crystalguard.domain.NotificationItem

data class EducatorNotificationsUiState(
    val isLoading: Boolean = true,
    val todayNotifications: List<NotificationItem> = emptyList(),
    val previousNotifications: List<NotificationItem> = emptyList(),
)

class EducatorNotificationsViewModel(
    private val repository: NotificationRepository = NotificationRepository(),
) : ViewModel() {

    var uiState by mutableStateOf(EducatorNotificationsUiState())
        private set

    init {
        cargarNotificaciones()
    }

    fun cargarNotificaciones() {
        uiState = uiState.copy(isLoading = true)

        val today = repository.getTodayNotifications()
        val previous = repository.getPreviousNotifications()

        uiState = EducatorNotificationsUiState(
            isLoading = false,
            todayNotifications = today,
            previousNotifications = previous,
        )
    }

    fun onNotificationClick(notification: NotificationItem) {
        // Acción mock al pulsar una notificación
    }
}
