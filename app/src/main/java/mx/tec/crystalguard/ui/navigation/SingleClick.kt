package mx.tec.crystalguard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

/**
 * Extensión para NavController que evita abrir pantallas duplicadas cuando el usuario
 * presiona un botón varias veces seguidas o toca múltiples elementos rápidamente.
 */
fun NavController.navigateSingle(route: String) {
    if (currentDestination?.route != route) {
        navigate(route) {
            launchSingleTop = true
        }
    }
}

/**
 * Helper composable que ignora clics repetidos en un botón dentro del intervalo (debouncing),
 * previniendo que se disparen múltiples acciones o se abran múltiples diálogos a la vez.
 */
@Composable
fun rememberSingleClick(
    debounceTimeMs: Long = 500L,
    onClick: () -> Unit,
): () -> Unit {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    return {
        val currentTime = System.currentTimeMillis()
        if ((currentTime - lastClickTime) >= debounceTimeMs) {
            lastClickTime = currentTime
            onClick()
        }
    }
}
