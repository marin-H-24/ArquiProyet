package com.marin.arquiproyet.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = darkColorScheme(
    primary = ColorLightTeal,
    secondary = ColorAccentYellow,
    background = ColorDeepTeal,
    surface = ColorMediumTeal,
    onPrimary = ColorDeepTeal,
    onSecondary = ColorDeepTeal,
    onBackground = ColorBeige,
    onSurface = ColorBeige
)

@Composable
fun ArquiproyetTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        content = content
    )
}