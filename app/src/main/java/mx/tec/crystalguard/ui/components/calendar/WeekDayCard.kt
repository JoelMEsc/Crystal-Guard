package mx.tec.crystalguard.ui.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.WeekDay
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun WeekDayCard(
    day: WeekDay,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isWeekend = day.letter.trim().equals("S", ignoreCase = true) || day.letter.trim().equals("D", ignoreCase = true)

    val containerColor = when {
        day.isSelected -> MaterialTheme.colorScheme.primaryContainer
        isWeekend -> MaterialTheme.colorScheme.surfaceContainerHighest
        else -> MaterialTheme.colorScheme.surfaceContainerLow
    }

    val letterTextColor = when {
        day.isSelected -> MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val numberTextColor = when {
        day.isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
        else -> MaterialTheme.colorScheme.onSurface
    }

    Card(
        onClick = onClick,
        modifier = modifier
            .widthIn(min = 40.dp, max = 64.dp)
            .height(68.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (day.isSelected) 4.dp else 1.dp,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = day.letter,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                color = letterTextColor,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = day.number.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = numberTextColor,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeekDayCardPreview() {
    CrystalGuardTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            val sampleDays = listOf(
                WeekDay(letter = "J", number = 28, isSelected = false),
                WeekDay(letter = "V", number = 29, isSelected = true),
                WeekDay(letter = "S", number = 30, isSelected = false),
            )
            sampleDays.forEach { day ->
                WeekDayCard(day = day, onClick = {})
            }
        }
    }
}
