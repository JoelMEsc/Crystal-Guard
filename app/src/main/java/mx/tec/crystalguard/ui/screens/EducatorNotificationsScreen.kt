package mx.tec.crystalguard.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.NotificationCategory
import mx.tec.crystalguard.domain.NotificationItem
import mx.tec.crystalguard.ui.components.CargandoView
import mx.tec.crystalguard.ui.components.NotificationCard
import mx.tec.crystalguard.ui.state.EducatorNotificationsUiState
import mx.tec.crystalguard.ui.state.EducatorNotificationsViewModel
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun EducatorNotificationsScreen(
    viewModel: EducatorNotificationsViewModel,
    modifier: Modifier = Modifier,
) {
    EducatorNotificationsContent(
        uiState = viewModel.uiState,
        onNotificationClick = viewModel::onNotificationClick,
        modifier = modifier,
    )
}

@Composable
fun EducatorNotificationsContent(
    uiState: EducatorNotificationsUiState,
    onNotificationClick: (NotificationItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isLoading) {
        CargandoView(modifier = modifier.fillMaxSize())
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                Text(
                    text = "Hoy",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            if (uiState.todayNotifications.isEmpty()) {
                item {
                    Text(
                        text = "No tienes notificaciones hoy.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )
                }
            } else {
                items(
                    items = uiState.todayNotifications,
                    key = { item -> item.id },
                ) { notification ->
                    NotificationCard(
                        notification = notification,
                        onClick = { onNotificationClick(notification) },
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    text = "Anteriores",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            if (uiState.previousNotifications.isEmpty()) {
                item {
                    Text(
                        text = "No hay notificaciones anteriores.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )
                }
            } else {
                items(
                    items = uiState.previousNotifications,
                    key = { item -> item.id },
                ) { notification ->
                    NotificationCard(
                        notification = notification,
                        onClick = { onNotificationClick(notification) },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EducatorNotificationsScreenPreview() {
    val sampleState = EducatorNotificationsUiState(
        isLoading = false,
        todayNotifications = listOf(
            NotificationItem(
                id = 1,
                title = "Solicitud retiro de foto",
                person = "Marta Gómez (Mamá de Carlos)",
                group = "Abejitas (Gpo 302)",
                message = "Hola profe, agradecería que removiera la foto por x o y razón por favor.",
                dateCategory = NotificationCategory.HOY,
                timeText = "Hace 20 min",
            ),
        ),
        previousNotifications = listOf(
            NotificationItem(
                id = 2,
                title = "Confirmación de paseo escolar",
                person = "Laura López (Mamá de Lucas)",
                group = "Abejitas (Gpo 302)",
                message = "Ya firmé la autorización médica para la salida.",
                dateCategory = NotificationCategory.ANTERIORES,
                timeText = "Ayer",
            ),
        ),
    )

    CrystalGuardTheme {
        EducatorNotificationsContent(
            uiState = sampleState,
            onNotificationClick = {},
        )
    }
}
