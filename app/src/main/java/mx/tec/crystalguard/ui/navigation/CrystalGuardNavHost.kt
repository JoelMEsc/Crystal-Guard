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
import mx.tec.crystalguard.ui.components.SecondaryTopBar
import mx.tec.crystalguard.ui.components.TopBar
import mx.tec.crystalguard.ui.components.VacioView
import mx.tec.crystalguard.ui.screens.AnnouncementsScreen
import mx.tec.crystalguard.ui.screens.AttendanceScreen
import mx.tec.crystalguard.ui.screens.ClassMaterialScreen
import mx.tec.crystalguard.ui.screens.EducatorCalendarScreen
import mx.tec.crystalguard.ui.screens.EducatorDashboardScreen
import mx.tec.crystalguard.ui.screens.GalleryScreen
import mx.tec.crystalguard.ui.screens.GroupCalendarScreen
import mx.tec.crystalguard.ui.screens.GroupDetailScreen
import mx.tec.crystalguard.ui.screens.PeopleScreen
import mx.tec.crystalguard.ui.screens.StudentDetailScreen
import mx.tec.crystalguard.ui.state.AnnouncementsViewModel
import mx.tec.crystalguard.ui.state.AttendanceViewModel
import mx.tec.crystalguard.ui.state.ClassMaterialViewModel
import mx.tec.crystalguard.ui.state.EducatorCalendarViewModel
import mx.tec.crystalguard.ui.state.EducatorDashboardViewModel
import mx.tec.crystalguard.ui.state.GalleryViewModel
import mx.tec.crystalguard.ui.state.GroupCalendarViewModel
import mx.tec.crystalguard.ui.state.GroupDetailViewModel
import mx.tec.crystalguard.ui.state.NavigationViewModel
import mx.tec.crystalguard.ui.state.PeopleViewModel
import mx.tec.crystalguard.ui.state.StudentDetailViewModel

@Composable
fun CrystalGuardNavHost(onSalir: () -> Unit) {
    val nav = rememberNavController()
    val navViewModel: NavigationViewModel = viewModel()

    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val isMainScreen = currentRoute in navViewModel.educatorTopBarScreens

    val mainScreenSubtitle = when (currentRoute) {
        Route.EDUCATOR_DASHBOARD -> "Tablero"
        Route.EDUCATOR_CALENDAR -> "Calendario"
        Route.EDUCATOR_NOTIFICATIONS -> "Notificaciones"
        else -> null
    }

    val secondaryScreenTitle = when {
        currentRoute?.startsWith("detail") == true || currentRoute == Route.GROUP_DETAIL -> "Detalle del Grupo"
        currentRoute?.startsWith("calendar/") == true || currentRoute == Route.CALENDAR -> "Calendario de Grupo"
        currentRoute?.startsWith("classMaterial/") == true || currentRoute == Route.CLASS_MATERIAL -> "Material de Clase"
        currentRoute?.startsWith("attendance/") == true || currentRoute == Route.ATTENDANCE -> "Pase de Lista"
        currentRoute?.startsWith("announcements/") == true || currentRoute == Route.ANNOUNCEMENTS -> "Anuncios"
        currentRoute?.startsWith("bitacora/") == true || currentRoute == Route.BITACORA -> "Bitácora"
        currentRoute?.startsWith("galery") == true || currentRoute == Route.GALERIA -> "Galería"
        currentRoute?.startsWith("people/") == true || currentRoute == Route.PEOPLE -> "Personas"
        currentRoute?.startsWith("studentDetail/") == true || currentRoute == Route.STUDENT_DETAIL -> "Detalles del Alumno"
        else -> "Detalle"
    }

    Scaffold(
        topBar = {
            if (isMainScreen) {
                TopBar(
                    title = "Crystal Guard",
                    subtitle = mainScreenSubtitle,
                    onProfileClick = { /* Perfil */ },
                )
            } else {
                SecondaryTopBar(
                    title = secondaryScreenTitle,
                    onBackClick = { nav.popBackStack() },
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
                            nav.navigate(route)
                        }
                    },
                    onReintentar = { viewModel.cargarGrupo(groupId) },
                )
            }

            composable(Route.CLASS_MATERIAL) { entry: NavBackStackEntry ->
                val groupIdStr = entry.arguments?.getString("groupId")
                val groupId = groupIdStr?.toIntOrNull() ?: 1

                val viewModel: ClassMaterialViewModel = viewModel()

                LaunchedEffect(groupId) {
                    viewModel.cargarMateriales(groupId)
                }

                ClassMaterialScreen(
                    viewModel = viewModel,
                )
            }

            composable(Route.CALENDAR) { entry: NavBackStackEntry ->
                val groupIdStr = entry.arguments?.getString("groupId")
                val groupId = groupIdStr?.toIntOrNull() ?: 1

                val viewModel: GroupCalendarViewModel = viewModel()

                LaunchedEffect(groupId) {
                    viewModel.cargarCalendarioGrupo(groupId)
                }

                GroupCalendarScreen(
                    viewModel = viewModel,
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

            composable(Route.PEOPLE) { entry: NavBackStackEntry ->
                val groupIdStr = entry.arguments?.getString("groupId")
                val groupId = groupIdStr?.toIntOrNull() ?: 1

                val viewModel: PeopleViewModel = viewModel()

                LaunchedEffect(groupId) {
                    viewModel.cargarPersonas(groupId)
                }

                PeopleScreen(
                    viewModel = viewModel,
                    onNavigate = { route -> nav.navigateSingle(route) },
                )
            }

            composable(Route.STUDENT_DETAIL) { entry: NavBackStackEntry ->
                val studentIdStr = entry.arguments?.getString("studentId")
                val studentId = studentIdStr?.toIntOrNull() ?: 101

                val viewModel: StudentDetailViewModel = viewModel()

                LaunchedEffect(studentId) {
                    viewModel.cargarDetalleEstudiante(studentId)
                }

                StudentDetailScreen(
                    viewModel = viewModel,
                )
            }

            composable(Route.BITACORA) {
                VacioView(mensaje = "Pantalla de Bitácora en construcción.")
            }

            composable(Route.EDUCATOR_CALENDAR) {
                val viewModel: EducatorCalendarViewModel = viewModel()

                EducatorCalendarScreen(
                    viewModel = viewModel,
                )
            }

            composable(Route.EDUCATOR_NOTIFICATIONS) {
                VacioView(mensaje = "Pantalla de Notificaciones en construcción.")
            }
        }
    }
}
