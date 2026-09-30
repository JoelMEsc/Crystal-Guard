package mx.tec.crystalguard.ui.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.MonthDay
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun MonthDayCell(
    day: MonthDay,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = when {
        day.isSelected -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surface
    }

    val textColor = when {
        day.isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
        !day.isCurrentMonth -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
        else -> MaterialTheme.colorScheme.onSurface
    }

    val fontWeight = when {
        day.isSelected -> FontWeight.Bold
        !day.isCurrentMonth -> FontWeight.Normal
        else -> FontWeight.Medium
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(enabled = day.isCurrentMonth, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.dayNumber.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = fontWeight,
            color = textColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MonthDayCellPreview() {
    CrystalGuardTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            MonthDayCell(
                day = MonthDay(dayNumber = 15, isCurrentMonth = true, isSelected = true),
                onClick = {},
            )
        }
    }
}
