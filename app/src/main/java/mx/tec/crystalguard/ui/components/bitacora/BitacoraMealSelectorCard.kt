package mx.tec.crystalguard.ui.components.bitacora

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.MealConsumption
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun BitacoraMealSelectorCard(
    mealName: String,
    selectedOption: MealConsumption,
    onOptionSelected: (MealConsumption) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
        ) {
            Text(
                text = mealName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                MealConsumption.entries.forEach { option ->
                    FilterChip(
                        selected = (selectedOption == option),
                        onClick = { onOptionSelected(option) },
                        label = { Text(option.label, style = MaterialTheme.typography.labelSmall) },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BitacoraMealSelectorCardPreview() {
    CrystalGuardTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            BitacoraMealSelectorCard(
                mealName = "Desayuno",
                selectedOption = MealConsumption.TODO,
                onOptionSelected = {},
            )
        }
    }
}
