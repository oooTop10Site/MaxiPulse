package org.example.project.theme.uiKit

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.domain.model.sportsman.HeartBit
import org.example.project.ext.getEvenlyDistributedMills
import org.example.project.ext.secondsToUI
import org.example.project.ext.toTimeUI
import org.example.project.theme.MaxiPulsTheme

@Composable
fun HeartRateGraph(
    modifier: Modifier = Modifier,
    heartRateData: List<HeartBit>,
    showY: Boolean = true,
    showTime: Boolean = false
) {
    val maxValue = 230f
    val zones = listOf(
        0f to 144f,        // Голубая зона
        144f to 165f,      // Синяя зона
        165f to 187f,      // Зеленая зона
        187f to 198f,      // Оранжевая зона
        198f to maxValue   // Красная зона
    )

    val zoneColors = listOf(
        Color(0xFFAEC6F3), // Голубая
        Color(0xFF3B6ECF), // Синяя
        Color(0xFF96D34B), // Зеленая
        Color(0xFFFFA93A), // Оранжевая
        Color(0xFFDF0B40)  // Красная
    )

    val textMeasurer = rememberTextMeasurer()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            val textStyle = MaxiPulsTheme.typography.bold.copy(
                fontSize = 14.sp,
                color = MaxiPulsTheme.colors.uiKit.textColor,
                lineHeight = 14.sp
            )
            if (showY) {
                Canvas(modifier = Modifier.fillMaxHeight().width(40.dp)) {
                    val graphWidth = size.width
                    val graphHeight = size.height
                    val zoneHeight = graphHeight / zones.size
                    zones.forEachIndexed { index, (_, endHR) ->
                        val topY = graphHeight - (index + 1) * zoneHeight
                        val textLayoutResult = textMeasurer.measure(
                            text = endHR.toInt().toString(),
                            style = textStyle
                        )
                        drawText(
                            textLayoutResult,
                            topLeft = Offset(
                                x = 0f,
                                y = topY
                            )
                        )
                    }
                }
            }
            Canvas(modifier = Modifier.weight(1f).fillMaxHeight().clip(RoundedCornerShape(25.dp))) {
                val graphWidth = size.width
                val graphHeight = size.height
                val zoneHeight = graphHeight / zones.size

                // Отрисовка зон
                zones.forEachIndexed { index, (_, endHR) ->
                    val topY = graphHeight - (index + 1) * zoneHeight
                    drawRect(
                        color = zoneColors[index],
                        topLeft = Offset(0f, topY),
                        size = Size(graphWidth, zoneHeight)
                    )
                }

                // Отрисовка линии графика
                val pointGap = graphWidth / (heartRateData.size - 1)
                val path = Path()

                heartRateData.forEachIndexed { index, value ->
                    // Определяем y-координату точки
                    val y = zones.foldIndexed(0f) { i, acc, (startHR, endHR) ->
                        if (value.value.toFloat() in startHR..endHR) {
                            val zoneStartY = graphHeight - (i + 1) * zoneHeight
                            val zoneEndY = graphHeight - i * zoneHeight
                            val zoneRange = endHR - startHR
                            val normalizedY = ((value.value - startHR) / zoneRange) * zoneHeight
                            zoneEndY - normalizedY
                        } else acc
                    }

                    val x = index * pointGap

                    // Добавляем линию графика
                    if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }

                // Отрисовка линии
                drawPath(
                    path = path,
                    color = Color.White,
                    style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }
        if (showTime) {
            val textStyle = MaxiPulsTheme.typography.regular.copy(
                fontSize = 14.sp,
                lineHeight = 14.sp,
                color = MaxiPulsTheme.colors.uiKit.textColor
            )
            // Отрисовка временной шкалы
            Row(
                modifier = Modifier.fillMaxWidth().padding(
                    start = if (showY) {
                        40.dp
                    } else 0.dp
                ).height(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                heartRateData.getEvenlyDistributedMills(5).forEach {
                    Text(text = it.secondsToUI(), style = textStyle)
                }
            }
        }
    }
}

