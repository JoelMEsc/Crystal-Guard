package mx.tec.crystalguard.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.tec.crystalguard.ui.components.BottomNavBar
import mx.tec.crystalguard.ui.components.TopBar
import mx.tec.crystalguard.ui.screens.EducatorCalendarScreen
import mx.tec.crystalguard.ui.screens.EducatorDashboardScreen
import mx.tec.crystalguard.ui.state.EducatorCalendarViewModel
import mx.tec.crystalguard.ui.state.EducatorDashboardViewModel
import mx.tec.crystalguard.ui.state.NavigationViewModel
import mx.tec.crystalguard.ui.screens.GroupDetailScreen
import mx.tec.crystalguard.ui.state.GroupDetailViewModel

@Composable
fun CrystalGuardNavHost(onSalir: () -> Unit) {
    val nav = rememberNavController()
    val navViewModel: NavigationViewModel = viewModel()

    // Rutas que muestran la BottomBar (incluyendo el detalle de grupo)
    val bottomBarScreens: List<String> = listOf(
        Route.EDUCATOR_DASHBOARD,
        Route.EDUCATOR_CALENDAR,
        Route.EDUCATOR_NOTIFICATIONS,
        Route.GROUP_DETAIL,
    )

    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showTopBar = currentRoute in navViewModel.educatorTopBarScreens

    val currentRouteFormated = when (currentRoute) {
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

            composable(Route.GROUP_DETAIL) { entry: NavBackStackEntry ->
                val groupIdStr = entry.arguments?.getString("groupId")
                val groupId = groupIdStr?.toIntOrNull() ?: 1

                val viewModel: GroupDetailViewModel = viewModel()

                LaunchedEffect(groupId) {
                    viewModel.cargarGrupo(groupId)
                }

                GroupDetailScreen(
                    uiState = viewModel.uiState,
                    onBackClick = { nav.popBackStack() },
                    onItemClick = { item ->
                        viewModel.onItemClick(item) { route ->
                            // nav.navigate(route) // Se puede descomentar cuando existan las pantallas
                        }
                    },
                    onReintentar = { viewModel.cargarGrupo(groupId) },
                )
            }

            composable(Route.EDUCATOR_CALENDAR) {
                val viewModel: EducatorCalendarViewModel = viewModel()

                EducatorCalendarScreen(
                    viewModel = viewModel,
                )
            }

            composable(Route.EDUCATOR_NOTIFICATIONS) {
                // Pantalla futura
            }
        }
    }
}
