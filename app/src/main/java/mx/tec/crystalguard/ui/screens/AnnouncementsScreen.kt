package mx.tec.crystalguard.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.Announcement
import mx.tec.crystalguard.ui.components.AnnouncementCard
import mx.tec.crystalguard.ui.components.CargandoView
import mx.tec.crystalguard.ui.components.ErrorView
import mx.tec.crystalguard.ui.components.PublicarAnuncioButton
import mx.tec.crystalguard.ui.state.AnnouncementsUiState

@Composable
fun AnnouncementsScreen(
    uiState: AnnouncementsUiState,
    onAbrirCrearClick: () -> Unit,
    onCerrarCrearClick: () -> Unit,
    onPublicarAnuncioClick: (String, String) -> Unit,
    onAnuncioClick: (Announcement?) -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onReintentar: () -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        when {
            uiState.isLoading -> {
                CargandoView(modifier = Modifier.fillMaxSize())
            }
            uiState.errorMessage != null -> {
                ErrorView(
                    mensaje = uiState.errorMessage,
                    onReintentar = onReintentar,
                    modifier = Modifier.fillMaxSize(),
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    item {
                        PublicarAnuncioButton(
                            onClick = onAbrirCrearClick,
                        )
                    }

                    items(
                        items = uiState.announcements,
                        key = { announcement -> announcement.id },
                    ) { announcement ->
                        AnnouncementCard(
                            announcement = announcement,
                            onClick = { onAnuncioClick(announcement) },
                        )
                    }
                }
            }
        }

        if (uiState.isCreandoAnuncio) {
            var titulo by remember { mutableStateOf("") }
            var contenido by remember { mutableStateOf("") }

            AlertDialog(
                onDismissRequest = onCerrarCrearClick,
                title = {
                    Text(
                        text = "Publicar nuevo aviso",
                        fontWeight = FontWeight.Bold,
                    )
                },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        OutlinedTextField(
                            value = titulo,
                            onValueChange = { titulo = it },
                            label = { Text("Título del anuncio") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        OutlinedTextField(
                            value = contenido,
                            onValueChange = { contenido = it },
                            label = { Text("Contenido del aviso") },
                            minLines = 3,
                            maxLines = 5,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                },
                confirmButton = {
                    Button(
                        enabled = titulo.isNotBlank() && contenido.isNotBlank(),
                        onClick = {
                            onPublicarAnuncioClick(titulo, contenido)
                        },
                    ) {
                        Text("Publicar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = onCerrarCrearClick) {
                        Text("Cancelar")
                    }
                },
            )
        }

        val selected = uiState.selectedAnnouncement
        if (selected != null) {
            AlertDialog(
                onDismissRequest = { onAnuncioClick(null) },
                title = {
                    Text(
                        text = selected.title,
                        fontWeight = FontWeight.Bold,
                    )
                },
                text = {
                    Column {
                        Text(
                            text = "Publicado por: ${selected.author}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = "Fecha: ${selected.date}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = selected.content,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = { onAnuncioClick(null) }) {
                        Text("Cerrar")
                    }
                },
            )
        }
    }
}
