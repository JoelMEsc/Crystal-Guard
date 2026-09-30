package mx.tec.crystalguard.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.ui.components.CargandoView
import mx.tec.crystalguard.ui.components.ErrorView
import mx.tec.crystalguard.ui.components.GroupDetailCard
import mx.tec.crystalguard.ui.components.ListSelectionItems
import mx.tec.crystalguard.ui.components.SelectionItemData
import mx.tec.crystalguard.ui.state.GroupDetailUiState

@Composable
fun GroupDetailScreen(
    uiState: GroupDetailUiState,
    onBackClick: () -> Unit,
    onItemClick: (SelectionItemData) -> Unit,
    modifier: Modifier = Modifier,
    onReintentar: () -> Unit = {},
) {
    when {
        uiState.isLoading -> {
            CargandoView(modifier = modifier.fillMaxSize())
        }
        uiState.errorMessage != null -> {
            ErrorView(
                mensaje = uiState.errorMessage,
                onReintentar = onReintentar,
                modifier = modifier.fillMaxSize(),
            )
        }
        uiState.group != null -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
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
