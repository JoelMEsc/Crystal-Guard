package mx.tec.crystalguard.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.Group
import mx.tec.crystalguard.ui.components.GroupDetailCard
import mx.tec.crystalguard.ui.components.ListSelectionItems
import mx.tec.crystalguard.ui.components.SelectionItemData
import mx.tec.crystalguard.ui.components.TopBar

@Composable
fun GroupDetailScreen(
    group: Group,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onItemClick: (SelectionItemData) -> Unit = {},
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            GroupDetailCard(group = group)

            Spacer(modifier = Modifier.height(24.dp))

            ListSelectionItems(
                onItemClick = onItemClick,
            )
        }
    }
}
