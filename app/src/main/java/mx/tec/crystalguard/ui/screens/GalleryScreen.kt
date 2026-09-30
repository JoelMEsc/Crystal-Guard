package mx.tec.crystalguard.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import mx.tec.crystalguard.domain.GalleryImage
import mx.tec.crystalguard.ui.components.CargandoView
import mx.tec.crystalguard.ui.components.ErrorView
import mx.tec.crystalguard.ui.components.GalleryImageCard
import mx.tec.crystalguard.ui.state.GalleryUiState

@Composable
fun GalleryScreen(
    uiState: GalleryUiState,
    onImageClick: (GalleryImage?) -> Unit,
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        items = uiState.images,
                        key = { image -> image.id },
                    ) { image ->
                        GalleryImageCard(
                            image = image,
                            onClick = { onImageClick(image) },
                        )
                    }
                }
            }
        }

        val selected = uiState.selectedImage
        if (selected != null) {
            Dialog(
                onDismissRequest = { onImageClick(null) },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false,
                ),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .clickable { onImageClick(null) },
                ) {
                    Image(
                        painter = painterResource(id = selected.imageRes),
                        contentDescription = selected.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.65f))
                            .padding(horizontal = 16.dp, vertical = 20.dp)
                            .align(Alignment.TopStart),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = selected.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                            )
                            Text(
                                text = selected.date,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.8f),
                            )
                        }

                        IconButton(onClick = { onImageClick(null) }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Cerrar imagen",
                                tint = Color.White,
                            )
                        }
                    }
                }
            }
        }
    }
}
