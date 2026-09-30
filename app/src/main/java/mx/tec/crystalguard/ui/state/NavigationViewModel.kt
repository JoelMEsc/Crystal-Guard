package mx.tec.crystalguard.ui.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.tec.crystalguard.ui.components.NavItem
import mx.tec.crystalguard.ui.navigation.Route

data class NavigationUiState(
    val items: List<NavItem> = listOf(
        NavItem(
            id = Route.EDUCATOR_DASHBOARD,
            title = "Tablero",
            icon = Icons.Filled.Dashboard,
        ),
        NavItem(
            id = Route.EDUCATOR_CALENDAR,
            title = "Calendario",
            icon = Icons.Filled.CalendarMonth,
        ),
        NavItem(
            id = Route.EDUCATOR_NOTIFICATIONS,
            title = "Notificaciones",
            icon = Icons.Filled.Notifications,
        ),
    ),
)

class NavigationViewModel : ViewModel() {
    var uiState by mutableStateOf(NavigationUiState())
        private set

    val educatorTopBarScreens: List<String> = listOf(
        Route.EDUCATOR_DASHBOARD,
        Route.EDUCATOR_CALENDAR,
        Route.EDUCATOR_NOTIFICATIONS,
    )
}
