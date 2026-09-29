package mx.tec.crystalguard.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.tec.crystalguard.ui.components.BottomNavBar
import mx.tec.crystalguard.ui.components.TopBar
import mx.tec.crystalguard.ui.screens.EducatorDashboardScreen
import mx.tec.crystalguard.ui.state.EducatorDashboardViewModel
import mx.tec.crystalguard.ui.state.NavigationViewModel

@Composable
fun CrystalGuardNavHost(onSalir: () -> Unit) {
    val nav = rememberNavController()
    val navViewModel: NavigationViewModel = viewModel()

    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showTopBar = currentRoute in navViewModel.educatorTopBarScreens

    val currentRouteFormated = when (val current = currentRoute) {
        "educatorDashboard" -> "Tablero"
        "educatorCalendar" -> "Calendario"
        "educatorNotificacions" -> "Notificaciones"
        else -> null
    }

    Scaffold(
        topBar = {
            if (showTopBar) {
                TopBar(
                    title = "Crystal Guard",
                    subtitle = currentRouteFormated,
                    onProfileClick = { /* Perfil */ },
                )
            }
        },
        bottomBar = {
            BottomNavBar(
                items = navViewModel.uiState.items,
                currentRoute = currentRoute,
                onItemClick = { route ->
                    if (currentRoute != route) {
                        nav.navigate(route) {
                            popUpTo(nav.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
            )
        },
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = Route.EDUCATOR_DASHBOARD,
            modifier = Modifier.padding(padding),
        ) {
            composable(Route.EDUCATOR_DASHBOARD) {
                val viewModel: EducatorDashboardViewModel = viewModel()

                EducatorDashboardScreen(
                    groups = viewModel.groups,
                    onClick = { id -> nav.navigate(Route.detail(id)) },
                )
            }

            composable(Route.EDUCATOR_CALENDAR) {
                // Pantalla futura
            }

            composable(Route.EDUCATOR_NOTIFICATIONS) {
                // Pantalla futura
            }
        }
    }
}
