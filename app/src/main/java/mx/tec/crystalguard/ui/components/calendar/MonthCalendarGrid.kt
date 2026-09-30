package mx.tec.crystalguard.ui.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.MonthDay
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun MonthCalendarGrid(
    days: List<MonthDay>,
    modifier: Modifier = Modifier,
    onDayClick: (MonthDay) -> Unit = {},
) {
    val weekHeaderLetters = listOf("L", "M", "M", "J", "V", "S", "D")
    val rows = days.chunked(7)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.widthIn(max = 480.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                weekHeaderLetters.forEach { letter ->
                    Text(
                        text = letter,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            rows.take(6).forEach { weekRow ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    weekRow.forEach { day ->
                        MonthDayCell(
                            day = day,
                            onClick = { onDayClick(day) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                    repeat(7 - weekRow.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MonthCalendarGridPreview() {
    val sampleDays = buildList {
        for (i in 28..31) {
            add(MonthDay(dayNumber = i, isCurrentMonth = false))
        }
        for (i in 1..30) {
            add(MonthDay(dayNumber = i, isCurrentMonth = true, isSelected = (i == 15)))
        }
        for (i in 1..8) {
            add(MonthDay(dayNumber = i, isCurrentMonth = false))
        }
    }

    CrystalGuardTheme {
        MonthCalendarGrid(
            days = sampleDays,
            onDayClick = { },
        )
    }
}
