package com.marin.arquiproyet.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marin.arquiproyet.ui.theme.GlacierWhite
import com.marin.arquiproyet.ui.theme.NeonGold

@Composable
fun CategoryWidget(
    selectedFilter: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Categoría",
            color = GlacierWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = selectedFilter ?: "TODAS",
            color = NeonGold.copy(alpha = 0.8f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // DIBUJO DE BARRAS Y CÍRCULOS (Como en tu libreta)
        Canvas(modifier = Modifier.fillMaxWidth().weight(1f)) {
            val numberOfBars = 5
            // Calculamos el espacio para que quede perfectamente centrado
            val totalSpacing = size.width * 0.2f
            val availableWidth = size.width - totalSpacing
            val barWidth = availableWidth / numberOfBars
            val spacing = totalSpacing / (numberOfBars - 1)
            val maxBarHeight = size.height * 0.7f

            for (i in 0 until numberOfBars) {
                // Alturas variadas para simular un ecualizador / medidor
                val barHeight = when(i) {
                    0 -> maxBarHeight * 0.6f
                    1 -> maxBarHeight * 0.9f
                    2 -> maxBarHeight * 1.0f // La del medio es la más alta
                    3 -> maxBarHeight * 0.8f
                    else -> maxBarHeight * 0.5f
                }

                val xOffset = i * (barWidth + spacing)
                val isHighlighted = selectedFilter != null && i == 2 // Brilla si hay filtro

                // Dibujamos el contorno de la barra
                drawRoundRect(
                    color = if (isHighlighted) NeonGold else GlacierWhite.copy(alpha = 0.4f),
                    topLeft = Offset(xOffset, maxBarHeight - barHeight),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(6f, 6f),
                    style = Stroke(width = 4f)
                )

                // Dibujamos el círculo justo debajo
                drawCircle(
                    color = if (isHighlighted) NeonGold else GlacierWhite.copy(alpha = 0.4f),
                    radius = barWidth * 0.4f,
                    center = Offset(xOffset + (barWidth / 2f), maxBarHeight + 25f),
                    style = Stroke(width = 4f)
                )
            }
        }
    }
}