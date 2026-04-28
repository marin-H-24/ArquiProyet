package com.marin.arquiproyet.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = darkColorScheme(
    primary = NeonGold,
    secondary = Emerald,
    background = DeepObsidian,
    surface = DarkTeal,
    onBackground = GlacierWhite,
    onSurface = GlacierWhite
)

@Composable
fun ArquiproyetTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        content = content
    )
}