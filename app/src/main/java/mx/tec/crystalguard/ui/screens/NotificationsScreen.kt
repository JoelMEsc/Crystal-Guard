package mx.tec.crystalguard.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.Notification
import mx.tec.crystalguard.ui.components.NotificationCard

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier
) {
    val todayNotifications = listOf(
        Notification(
            subject = "Solicitud de retiro de foto",
            from = "Marta Gómez (Mamá de Carlos)",
            group = "Abejitas (Gpo 302)",
            description = "Solicita retirar una fotografía del álbum \"Taller de pintura en Abejitas\" por motivos de privacidad familiar.",
            date = "Hace 20 min"
        ),
        Notification(
            subject = "Justificante médico",
            from = "Luis Pérez (Papá de Sofía)",
            group = "Abejitas (Gpo 302)",
            description = "Adjunta justificante médico por inasistencia del día de hoy.",
            date = "Hace 2 hrs"
        )
    )

    val yesterdayNotifications = listOf(
        Notification(
            subject = "Duda sobre material",
            from = "Ana Laura (Mamá de Diego)",
            group = "Abejitas (Gpo 302)",
            description = "¿Podrían confirmar si las cartulinas para mañana deben ser de un color en específico?",
            date = "Ayer"
        )
    )
    
    val lastWeekNotifications = listOf(
        Notification(
            subject = "Aviso de llegada tarde",
            from = "Carlos Ruiz (Papá de Mateo)",
            group = "Abejitas (Gpo 302)",
            description = "Aviso que Mateo llegará 20 minutos tarde por tráfico en la zona.",
            date = "Hace 4 días"
        )
    )

    val lastMonthNotifications = listOf(
        Notification(
            subject = "Autorización de salida",
            from = "Marta Gómez (Mamá de Carlos)",
            group = "Abejitas (Gpo 302)",
            description = "Autorizo a mi hermana, Laura Gómez, a recoger a Carlos el día de hoy.",
            date = "Hace 1 mes"
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item { Spacer(modifier = Modifier.height(16.dp)) }

        if (todayNotifications.isNotEmpty()) {
            item {
                Text(
                    text = "Hoy",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(todayNotifications) { notification ->
                NotificationCard(
                    notification = notification,
                    onArchiveClick = { /*TODO*/ }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        if (yesterdayNotifications.isNotEmpty()) {
            item {
                Text(
                    text = "Ayer",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(yesterdayNotifications) { notification ->
                NotificationCard(
                    notification = notification,
                    onArchiveClick = { /*TODO*/ }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        if (lastWeekNotifications.isNotEmpty()) {
            item {
                Text(
                    text = "La semana pasada",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(lastWeekNotifications) { notification ->
                NotificationCard(
                    notification = notification,
                    onArchiveClick = { /*TODO*/ }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        if (lastMonthNotifications.isNotEmpty()) {
            item {
                Text(
                    text = "El mes pasado",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(lastMonthNotifications) { notification ->
                NotificationCard(
                    notification = notification,
                    onArchiveClick = { /*TODO*/ }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}
