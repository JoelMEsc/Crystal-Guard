package mx.tec.crystalguard.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.ui.components.CargandoView
import mx.tec.crystalguard.ui.components.ErrorView
import mx.tec.crystalguard.ui.components.GroupDetailCard
import mx.tec.crystalguard.ui.components.ListSelectionItems
import mx.tec.crystalguard.ui.components.SelectionItemData
import mx.tec.crystalguard.ui.components.TopBar
import mx.tec.crystalguard.ui.state.GroupDetailUiState

@Composable
fun GroupDetailScreen(
    uiState: GroupDetailUiState,
    onBackClick: () -> Unit,
    onItemClick: (SelectionItemData) -> Unit,
    modifier: Modifier = Modifier,
    onReintentar: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                title = "Detalles del Grupo",
                onBackClick = onBackClick,
            )
        },
        modifier = modifier,
    ) { padding ->
        when {
            uiState.isLoading -> {
                CargandoView(modifier = Modifier.padding(padding))
            }
            uiState.errorMessage != null -> {
                ErrorView(
                    mensaje = uiState.errorMessage,
                    onReintentar = onReintentar,
                    modifier = Modifier.padding(padding),
                )
            }
            uiState.group != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    GroupDetailCard(group = uiState.group)

                    Spacer(modifier = Modifier.height(24.dp))

                    ListSelectionItems(
                        items = uiState.selectionItems,
                        onItemClick = onItemClick,
                    )
                }
            }
        }
    }
}
