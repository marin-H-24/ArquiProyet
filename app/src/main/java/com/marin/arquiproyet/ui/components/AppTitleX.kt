package com.marin.arquiproyet.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marin.arquiproyet.ui.theme.GlacierWhite
import com.marin.arquiproyet.ui.theme.NeonGold

@Composable
fun AppTitleX(modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Text(
            text = "ARQUIPROYET",
            color = GlacierWhite,
            fontSize = 26.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.width(12.dp))

        Canvas(modifier = Modifier.size(45.dp, 25.dp)) {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width, size.height)
                moveTo(size.width, 0f)
                lineTo(0f, size.height)
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                moveTo(0f, size.height)
                lineTo(size.width, size.height)
            }

            drawPath(
                path = path,
                brush = Brush.linearGradient(listOf(NeonGold, GlacierWhite)),
                style = Stroke(
                    width = 6f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
    }
}