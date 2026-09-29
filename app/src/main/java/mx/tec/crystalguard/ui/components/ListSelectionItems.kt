package mx.tec.crystalguard.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListSelectionItems(
    modifier: Modifier = Modifier,
    onItemClick: (SelectionItemData) -> Unit = {},
) {
    // Por ahora 3 selecciones
    val items = listOf(
        SelectionItemData(1, "Material de clase", Icons.AutoMirrored.Filled.MenuBook),
        SelectionItemData(2, "Pase de lista", Icons.Filled.AssignmentTurnedIn),
        SelectionItemData(3, "Anuncios", Icons.Filled.Campaign),
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        ),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            items.forEachIndexed { index, item ->
                ItemSelection(
                    item = item,
                    onClick = { onItemClick(item) },
                )
                if (index < (items.size - 1)) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                    )
                }
            }
        }
    }
}
