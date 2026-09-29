package mx.tec.crystalguard.ui.navigation

import android.R
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.tec.crystalguard.ui.components.TopBar
import mx.tec.crystalguard.ui.screens.EducatorDashboardScreen
import mx.tec.crystalguard.ui.state.EducatorDashboardViewModel

@Composable
fun CrystalGuardNavHost(onSalir: () -> Unit) {
    val nav = rememberNavController()

    val educatorTopBarScreens: List<String> = listOf(Route.EDUCATOR_DASHBOARD, Route.EDUCATOR_CALENDAR, Route.EDUCATOR_NOTIFICATIONS)

    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showTopBar = currentRoute in educatorTopBarScreens

    Scaffold(
//        TopBar = {
//            if (showTopBar) {
//                TopBar()
//            }
//        }
    ) { padding ->
            NavHost(
                navController = nav,
                startDestination = Route.EDUCATOR_DASHBOARD,
                modifier = Modifier.padding(padding)

            ) {

                composable(Route.EDUCATOR_DASHBOARD) {
                    val viewModel: EducatorDashboardViewModel = viewModel()

                    EducatorDashboardScreen(
                        groups = viewModel.groups,
                        onClick = { id -> nav.navigate(Route.detail(id)) }
                    )
                }

                //        composable(Route.PUBLICAR) {
                //            val viewModel: PublicarViewModel = viewModel(factory = AppViewModelProvider.Factory)
                //
                //            PublicarScreen(
                //                uiState = viewModel.uiState,
                //                onTituloChange = viewModel::onTituloChange,
                //                onCuerpoChange = viewModel::onCuerpoChange,
                //                onPublicar = { viewModel.publicar { nav.popBackStack() } },
                //                onCancelar = { nav.popBackStack() }
                //            )
                //        }
                //
                //        composable(Route.SESIONES) {
                //            val viewModel: SesionesViewModel = viewModel(factory = AppViewModelProvider.Factory)
                //
                //            LaunchedEffect(Unit) { viewModel.cargar() }
                //
                //            SesionesScreen(
                //                uiState = viewModel.uiState,
                //                cerrando = viewModel.cerrandoSesiones,
                //                onRecargar = { viewModel.cargar() },
                //                onCerrarEnTodosLados = { viewModel.cerrarEnTodosLados() },
                //                onVolver = { nav.popBackStack() }
                //            )
                //        }
            }
    }
}
