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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.tec.crystalguard.ui.components.TopBar
import mx.tec.crystalguard.ui.screens.AnnouncementsScreen
import mx.tec.crystalguard.ui.screens.AttendanceScreen
import mx.tec.crystalguard.ui.screens.EducatorDashboardScreen
import mx.tec.crystalguard.ui.screens.GalleryScreen
import mx.tec.crystalguard.ui.screens.GroupDetailScreen
import mx.tec.crystalguard.ui.state.AnnouncementsViewModel
import mx.tec.crystalguard.ui.state.AttendanceViewModel
import mx.tec.crystalguard.ui.state.EducatorDashboardViewModel
import mx.tec.crystalguard.ui.state.GalleryViewModel
import mx.tec.crystalguard.ui.state.GroupDetailViewModel

private data class NavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
)

@Composable
fun CrystalGuardNavHost(onSalir: () -> Unit) {
    val nav = rememberNavController()

    // Rutas que muestran la TopBar principal
    val topBarScreens: List<String> = listOf(
        Route.EDUCATOR_DASHBOARD,
        Route.EDUCATOR_CALENDAR,
        Route.EDUCATOR_NOTIFICATIONS,
    )

    // Rutas que muestran la BottomBar (incluyendo detalle de grupo, anuncios, pase de lista y galería)
    val bottomBarScreens: List<String> = listOf(
        Route.EDUCATOR_DASHBOARD,
        Route.EDUCATOR_CALENDAR,
        Route.EDUCATOR_NOTIFICATIONS,
        Route.GROUP_DETAIL,
        Route.ANNOUNCEMENTS,
        Route.ATTENDANCE,
        Route.GALERIA,
    )

    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showTopBar = currentRoute in topBarScreens
    val showBottomBar = currentRoute in bottomBarScreens

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
            if (showTopBar) {
                TopBar(
                    title = "Crystal Guard",
                    onMenuClick = { /* Menú lateral / opciones */ },
                    onProfileClick = { /* Perfil */ },
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    visibleNavItems.forEach { item ->
                        val selected = currentRoute == item.route ||
                                (item.route == Route.EDUCATOR_DASHBOARD &&
                                        (currentRoute == Route.GROUP_DETAIL ||
                                                currentRoute == Route.ANNOUNCEMENTS ||
                                                currentRoute == Route.ATTENDANCE ||
                                                currentRoute == Route.GALERIA))
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (currentRoute != item.route) {
                                    nav.navigateSingle(item.route)
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
                    onClick = { id -> nav.navigateSingle(Route.detail(id)) },
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
                            try {
                                nav.navigateSingle(route)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    },
                    onReintentar = { viewModel.cargarGrupo(groupId) },
                )
            }

            composable(Route.ATTENDANCE) { entry: NavBackStackEntry ->
                val groupIdStr = entry.arguments?.getString("groupId")
                val groupId = groupIdStr?.toIntOrNull() ?: 1

                val viewModel: AttendanceViewModel = viewModel()

                LaunchedEffect(groupId) {
                    viewModel.cargar(groupId)
                }

                AttendanceScreen(
                    uiState = viewModel.uiState,
                    onBackClick = { nav.popBackStack() },
                    onToggleLlego = { id -> viewModel.toggleLlego(id) },
                    onToggleSeFue = { id -> viewModel.toggleSeFue(id) },
                    onToggleNoAsistio = { id -> viewModel.toggleNoAsistio(id) },
                    onDejoSelected = { id, persona -> viewModel.actualizarDejoPersona(id, persona) },
                    onRecogioSelected = { id, persona -> viewModel.actualizarRecogioPersona(id, persona) },
                    onToggleEditarClick = { viewModel.toggleModoEdicion() },
                    onGuardarClick = { viewModel.guardarCambios() },
                    onLimpiarMensaje = { viewModel.limpiarMensajes() },
                    onReintentar = { viewModel.cargar(groupId) },
                )
            }

            composable(Route.ANNOUNCEMENTS) { entry: NavBackStackEntry ->
                val groupIdStr = entry.arguments?.getString("groupId")
                val groupId = groupIdStr?.toIntOrNull() ?: 1

                val viewModel: AnnouncementsViewModel = viewModel()

                LaunchedEffect(groupId) {
                    viewModel.cargar(groupId)
                }

                AnnouncementsScreen(
                    uiState = viewModel.uiState,
                    onBackClick = { nav.popBackStack() },
                    onAbrirCrearClick = { viewModel.abrirDialogoCrear() },
                    onCerrarCrearClick = { viewModel.cerrarDialogoCrear() },
                    onPublicarAnuncioClick = { titulo, contenido ->
                        viewModel.publicarAnuncioPersonalizado(titulo, contenido)
                    },
                    onAnuncioClick = { anuncio -> viewModel.seleccionarAnuncio(anuncio) },
                    onReintentar = { viewModel.cargar(groupId) },
                )
            }

            composable(Route.GALERIA) { entry: NavBackStackEntry ->
                val groupIdStr = entry.arguments?.getString("groupId")
                val groupId = groupIdStr?.toIntOrNull() ?: 1

                val viewModel: GalleryViewModel = viewModel()

                LaunchedEffect(groupId) {
                    viewModel.cargar(groupId)
                }

                GalleryScreen(
                    uiState = viewModel.uiState,
                    onBackClick = { nav.popBackStack() },
                    onImageClick = { image -> viewModel.seleccionarImagen(image) },
                    onReintentar = { viewModel.cargar(groupId) },
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
