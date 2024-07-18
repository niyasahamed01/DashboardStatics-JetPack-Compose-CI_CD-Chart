package com.example.staticsapp.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.example.staticsapp.model.Slice
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CustomPieChart(slices: List<Slice>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(200.dp)) {
                val totalValue = slices.map { it.value }.sum()
                var startAngle = -90f
                val radius = size.minDimension / 2

                slices.forEach { slice ->
                    val sweepAngle = 360 * (slice.value / totalValue)
                    drawArc(
                        color = slice.color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true
                    )

                    // Calculate the position for the percentage text
                    val angle = Math.toRadians((startAngle + sweepAngle / 2).toDouble()).toFloat()
                    val textX = (radius / 2 * cos(angle) + size.width / 2).toFloat()
                    val textY = (radius / 2 * sin(angle) + size.height / 2).toFloat()

                    // Draw the percentage text
                    drawContext.canvas.nativeCanvas.drawText(
                        "${(slice.value / totalValue * 100).toInt()}%",
                        textX,
                        textY,
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.BLACK
                            textSize = 30f
                            textAlign = android.graphics.Paint.Align.CENTER
                        }
                    )

                    startAngle += sweepAngle
                }
            }
        }
    }
}
