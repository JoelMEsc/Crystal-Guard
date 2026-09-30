package mx.tec.crystalguard.ui.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.WeekDay
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun WeekSelector(
    week: List<WeekDay>,
    modifier: Modifier = Modifier,
    onDayClick: (WeekDay) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.widthIn(max = 520.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            week.forEach { day ->
                WeekDayCard(
                    day = day,
                    onClick = { onDayClick(day) },
                    modifier = Modifier.weight(1f, fill = false),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeekSelectorPreview() {
    val sampleWeek = listOf(
        WeekDay(letter = "L", number = 28, isSelected = false),
        WeekDay(letter = "M", number = 29, isSelected = true),
        WeekDay(letter = "M", number = 30, isSelected = false),
        WeekDay(letter = "J", number = 1, isSelected = false),
        WeekDay(letter = "V", number = 2, isSelected = false),
        WeekDay(letter = "S", number = 3, isSelected = false),
        WeekDay(letter = "D", number = 4, isSelected = false),
    )
    CrystalGuardTheme {
        WeekSelector(
            week = sampleWeek,
            onDayClick = { },
        )
    }
}
