package mx.tec.crystalguard.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.tec.crystalguard.ui.components.TopBar
import mx.tec.crystalguard.ui.screens.EducatorDashboardScreen
import mx.tec.crystalguard.ui.state.EducatorDashboardViewModel

private data class NavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
)

@Composable
fun CrystalGuardNavHost(onSalir: () -> Unit) {
    val nav = rememberNavController()

    // Lista de rutas que muestran TopBar y BottomBar
    val educatorScreens: List<String> = listOf(
        Route.EDUCATOR_DASHBOARD,
        Route.EDUCATOR_CALENDAR,
        Route.EDUCATOR_NOTIFICATIONS,
    )

    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showTopAndBottomBar = currentRoute in educatorScreens

    // Definición "de fondo" de los 3 elementos de navegación
    val allNavItems = listOf(
        NavItem(
            route = Route.EDUCATOR_DASHBOARD,
            title = "Tablero",
            icon = Icons.Filled.Dashboard,
        ),
        NavItem(
            route = Route.EDUCATOR_CALENDAR,
            title = "Calendario",
            icon = Icons.Filled.CalendarMonth,
        ),
        NavItem(
            route = Route.EDUCATOR_NOTIFICATIONS,
            title = "Notificaciones",
            icon = Icons.Filled.Notifications,
        ),
    )

    // Por ahora, solo muestra 1 elemento ("Tablero") en la interfaz
    val visibleNavItems = allNavItems.take(1)

    Scaffold(
        topBar = {
            if (showTopAndBottomBar) {
                TopBar(
                    title = "Crystal Guard",
                    onMenuClick = { /* Menú lateral / opciones */ },
                    onProfileClick = { /* Perfil */ },
                )
            }
        },
        bottomBar = {
            if (showTopAndBottomBar) {
                NavigationBar {
                    visibleNavItems.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (currentRoute != item.route) {
                                    nav.navigate(item.route) {
                                        popUpTo(nav.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                )
                            },
                            label = { Text(item.title) },
                        )
                    }
                }
            }
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
