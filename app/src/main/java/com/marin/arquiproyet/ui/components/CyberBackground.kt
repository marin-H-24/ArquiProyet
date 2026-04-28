package com.marin.arquiproyet.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.marin.arquiproyet.ui.theme.DeepObsidian

@Composable
fun CyberBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepObsidian)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val spacing = 40f
            for (x in 0..size.width.toInt() step spacing.toInt()) {
                for (y in 0..size.height.toInt() step spacing.toInt()) {
                    drawCircle(
                        color = Color.White.copy(alpha = 0.03f),
                        radius = 1.5f,
                        center = Offset(x.toFloat(), y.toFloat())
                    )
                }
            }

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF00E676).copy(alpha = 0.05f), Color.Transparent),
                    center = Offset(size.width * 0.8f, size.height * 0.2f),
                    radius = 800f
                ),
                radius = 800f,
                center = Offset(size.width * 0.8f, size.height * 0.2f)
            )
        }
        content()
    }
}