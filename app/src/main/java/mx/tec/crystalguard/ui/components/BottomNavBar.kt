package mx.tec.crystalguard.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

data class NavItem(
    val id: String,
    val title: String,
    val icon: ImageVector,
)

@Composable
fun BottomNavBar(
    items: List<NavItem>,
    currentRoute: String?,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            val selected = currentRoute == item.id
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item.id) },
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

@Preview(showBackground = true)
@Composable
private fun BottomNavBarPreview() {
    val sampleItems = listOf(
        NavItem("dashboard", "Tablero", Icons.Filled.Dashboard),
        NavItem("calendar", "Calendario", Icons.Filled.CalendarMonth),
        NavItem("notifications", "Notificaciones", Icons.Filled.Notifications),
    )
    CrystalGuardTheme {
        BottomNavBar(
            items = sampleItems,
            currentRoute = "dashboard",
            onItemClick = {},
        )
    }
}
