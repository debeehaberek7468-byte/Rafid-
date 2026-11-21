package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val RafidDarkColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = DeepSpaceNavy,
    primaryContainer = ElectricBlueContainer,
    onPrimaryContainer = ElectricBlueSoft,
    secondary = CalmGold,
    onSecondary = DeepSpaceNavy,
    secondaryContainer = CalmGoldContainer,
    onSecondaryContainer = CalmGoldSoft,
    tertiary = EmeraldSuccess,
    onTertiary = DeepSpaceNavy,
    tertiaryContainer = EmeraldContainer,
    onTertiaryContainer = EmeraldSuccess,
    background = DeepSpaceNavy,
    onBackground = TextPrimary,
    surface = MidnightNavy,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceNavy,
    onSurfaceVariant = TextSecondary,
    surfaceContainerLow = MidnightNavy,
    surfaceContainer = SurfaceNavy,
    surfaceContainerHigh = ElevatedNavy,
    outline = GlassBorderCyan,
    outlineVariant = GlassBorderSubtle,
    error = RoseError,
    onError = DeepSpaceNavy,
    errorContainer = RoseContainer,
    onErrorContainer = RoseError
)

val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(30.dp)
)

@Composable
fun RafidPhysicsTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = RafidDarkColorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
