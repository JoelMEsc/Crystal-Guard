package mx.tec.crystalguard.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.data.CrystalGuardRepository
import mx.tec.crystalguard.domain.Group
import mx.tec.crystalguard.ui.components.GroupCard
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun EducatorDashboardScreen(
    groups: List<Group>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = groups,
            key = { group -> group.id }
        ) { group ->
            GroupCard(
                group = group,
                onClick = { onClick(group.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EducatorDashboardScreenPreview() {
    val repository = CrystalGuardRepository
    CrystalGuardTheme {
        EducatorDashboardScreen(
            groups = repository.getAll(),
            onClick = {}
        )
    }
}
