package mx.tec.crystalguard.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = TerracottaPrimary,
    onPrimary = TerracottaOnPrimary,
    primaryContainer = TerracottaContainer,
    onPrimaryContainer = TerracottaOnContainer,
    secondary = AmberSecondary,
    secondaryContainer = AmberContainer,
    onSecondaryContainer = AmberOnContainer,
    tertiary = SageTertiary,
    tertiaryContainer = SageContainer,
    onTertiaryContainer = SageOnContainer,
    background = WarmBackground,
    onBackground = WarmOnBackground,
    surface = WarmSurface,
    onSurface = WarmOnSurface,
    surfaceVariant = WarmSurfaceVariant,
    onSurfaceVariant = WarmOnSurfaceVariant,
    outline = WarmOutline,
    outlineVariant = WarmOutlineVariant,
)

private val DarkColorScheme = darkColorScheme(
    primary = TerracottaPrimaryDark,
    onPrimary = TerracottaOnPrimaryDark,
    primaryContainer = TerracottaContainerDark,
    onPrimaryContainer = TerracottaOnContainerDark,
    background = WarmBackgroundDark,
    onBackground = WarmOnBackgroundDark,
    surface = WarmSurfaceDark,
    onSurface = WarmOnSurfaceDark,
    surfaceVariant = WarmSurfaceVariantDark,
    onSurfaceVariant = WarmOnSurfaceVariantDark,
)

@Composable
fun CrystalGuardTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
