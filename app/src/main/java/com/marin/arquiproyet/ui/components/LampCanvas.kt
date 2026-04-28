package com.marin.arquiproyet.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import com.marin.arquiproyet.ui.theme.GlacierWhite
import com.marin.arquiproyet.ui.theme.NeonGold
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun LampCanvas(modifier: Modifier = Modifier) {
    // ANIMACIONES
    val infiniteTransition = rememberInfiniteTransition(label = "cyber_lamp")

    val glow by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    val rotationCog by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation_cog"
    )

    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height * 0.42f
        val glassRadius = size.width * 0.32f

        // 1. GLOW AMBIENTAL (Luz de fondo)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(NeonGold.copy(alpha = 0.15f * glow), Color.Transparent),
                center = Offset(centerX, centerY),
                radius = glassRadius * 2.5f
            ),
            radius = glassRadius * 2.5f,
            center = Offset(centerX, centerY)
        )

        // 2. RAYOS DE LUZ (Tubos de neón flotantes con bordes redondeados)
        for (i in 0..7) {
            withTransform({
                rotate(degrees = i * 45f + (rotationCog / 3f), pivot = Offset(centerX, centerY))
            }) {
                drawLine(
                    color = NeonGold.copy(alpha = 0.8f * glow),
                    start = Offset(centerX, centerY - glassRadius - 15f),
                    end = Offset(centerX, centerY - glassRadius - 45f),
                    strokeWidth = 10f,
                    cap = StrokeCap.Round
                )
            }
        }

        // 3. CUERPO DE CRISTAL 3D
        // Efecto de reflejo interno
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    GlacierWhite.copy(alpha = 0.05f),
                    GlacierWhite.copy(alpha = 0.2f),
                    GlacierWhite.copy(alpha = 0.6f)
                ),
                center = Offset(centerX - glassRadius * 0.3f, centerY - glassRadius * 0.3f),
                radius = glassRadius * 1.5f
            ),
            radius = glassRadius,
            center = Offset(centerX, centerY)
        )
        // Borde exterior del cristal
        drawCircle(
            color = GlacierWhite.copy(alpha = 0.9f),
            radius = glassRadius,
            center = Offset(centerX, centerY),
            style = Stroke(width = 5f)
        )

        // 4. ENGRANAJE INTERNO ("El Motor de la Idea")
        withTransform({
            rotate(degrees = rotationCog, pivot = Offset(centerX, centerY))
            scale(scaleX = 0.8f, scaleY = 0.8f, pivot = Offset(centerX, centerY))
        }) {
            val cogPath = Path().apply {
                val numberOfTeeth = 8
                val innerRadiusCog = glassRadius * 0.40f
                val outerRadiusCog = glassRadius * 0.60f

                for (i in 0 until numberOfTeeth * 2) {
                    val isInner = i % 2 == 0
                    val currentRadius = if (isInner) innerRadiusCog else outerRadiusCog
                    val stepAngle = 360f / (numberOfTeeth * 2)
                    // Matemáticas puras de Kotlin
                    val angle = (i * stepAngle) * (PI / 180f).toFloat()

                    val x = centerX + currentRadius * cos(angle)
                    val y = centerY + currentRadius * sin(angle)

                    if (i == 0) moveTo(x, y) else lineTo(x, y)
                }
                close()

                // Agujero central del engranaje
                val holeRadius = innerRadiusCog * 0.35f
                addOval(Rect(
                    centerX - holeRadius, centerY - holeRadius,
                    centerX + holeRadius, centerY + holeRadius
                ))
            }

            // Dibujamos el engranaje con un contorno dorado vibrante
            drawPath(
                path = cogPath,
                color = NeonGold,
                style = Stroke(width = 6f)
            )
            // Brillo interno del engranaje
            drawPath(
                path = cogPath,
                brush = Brush.radialGradient(
                    colors = listOf(NeonGold.copy(alpha = 0.4f * glow), Color.Transparent),
                    center = Offset(centerX, centerY),
                    radius = glassRadius * 0.6f
                )
            )
        }

        // 5. BASE DEL BOMBILLO (Metálica y con rosca realista)
        val baseWidth = glassRadius * 0.8f
        val baseHeight = glassRadius * 0.7f
        val baseTopY = centerY + glassRadius * 0.85f

        // Cuerpo de la base
        drawRoundRect(
            color = Color(0xFF2C2C2C), // Gris oscuro metálico
            topLeft = Offset(centerX - baseWidth / 2, baseTopY),
            size = Size(baseWidth, baseHeight),
            cornerRadius = CornerRadius(12f, 12f)
        )

        // Relieves de la rosca
        for (i in 0..2) {
            drawRoundRect(
                color = GlacierWhite.copy(alpha = 0.6f),
                topLeft = Offset(centerX - baseWidth / 2 - 4f, baseTopY + (baseHeight * 0.25f) * (i + 1) - 5f),
                size = Size(baseWidth + 8f, 8f),
                cornerRadius = CornerRadius(4f, 4f)
            )
        }

        // Contacto eléctrico inferior
        drawArc(
            color = Color(0xFF1A1A1A),
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(centerX - baseWidth * 0.3f, baseTopY + baseHeight - 8f),
            size = Size(baseWidth * 0.6f, 25f)
        )
    }
}