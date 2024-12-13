package org.example.project.theme.uiKit

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Month
import org.example.project.theme.MaxiPulsTheme

@Composable
fun BarChart(modifier: Modifier = Modifier, data: List<Pair<Month, Int>>) {
    val maxValue = data.maxOf { it.second }
    val barColor = Color(0xFFFFA500) // Оранжевый
    val highlightedBarColor = Color(0xFF32CD32) // Зеленый
    val axisColor = Color.Gray
    val barWidth = 41.dp // Ширина столбца
    val yTicksStep = 10 // Шаг засечек на оси Y
    val textMeasurer = rememberTextMeasurer()
    val textStyle = MaxiPulsTheme.typography.medium.copy(
        color = MaxiPulsTheme.colors.uiKit.textColor,
        fontSize = 14.sp,
        lineHeight = 14.sp
    )
    Box(
        modifier = modifier.padding() // Позволяем вам контролировать высоту
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val xAxisHeight = size.height - 20.dp.toPx()
            val yAxisWidth = 30.dp.toPx()
            val barWidthPx = barWidth.toPx()
            val spacing = (size.width - yAxisWidth - data.size * barWidthPx) / (data.size + 1)

            // Рисуем ось X
            drawLine(
                color = axisColor,
                start = Offset(yAxisWidth, xAxisHeight),
                end = Offset(size.width, xAxisHeight),
                strokeWidth = 2.dp.toPx()
            )

            // Рисуем ось Y
            drawLine(
                color = axisColor,
                start = Offset(yAxisWidth, xAxisHeight),
                end = Offset(yAxisWidth, 0f),
                strokeWidth = 2.dp.toPx()
            )

            // Рисуем засечки на оси Y
            for (i in 0..10) {
                val y = xAxisHeight - (xAxisHeight / 10) * i
                drawLine(
                    color = axisColor,
                    start = Offset(yAxisWidth, y),
                    end = Offset(yAxisWidth + 7.dp.toPx(), y), // Засечки внутрь графика
                    strokeWidth = 1.dp.toPx()
                )
            }

            // Располагаем столбцы
            data.forEachIndexed { index, (month, value) ->
                val barHeightFraction = value / maxValue.toFloat()
                val barHeight = size.height * barHeightFraction
                val barXOffset = yAxisWidth + spacing * (index + 1) + barWidthPx * index
                val left = barXOffset
                val top = xAxisHeight - barHeight
                val right = left + 41.dp.toPx()
                // Создаем Path с верхними скругленными углами
                val path = Path().apply {
                    moveTo(left, xAxisHeight)
                    lineTo(left, top + 30.dp.toPx())
                    arcTo(
                        rect = androidx.compose.ui.geometry.Rect(
                            left,
                            top,
                            left + 30.dp.toPx(),
                            top + 30.dp.toPx()
                        ),
                        startAngleDegrees = 180f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                    lineTo(right - 30.dp.toPx(), top)
                    arcTo(
                        rect = androidx.compose.ui.geometry.Rect(
                            right - 30.dp.toPx(),
                            top,
                            right,
                            top + 30.dp.toPx()
                        ),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                    lineTo(right, xAxisHeight)
                    close()
                }

                // Рисуем столбец
                drawPath(
                    path = path,
                    color = if (value == maxValue) highlightedBarColor else barColor
                )

                // Подпись месяца

                val text = month.name.lowercase().replaceFirstChar { it.uppercase() }
                val textWidth = textMeasurer.measure(text).size.width.toFloat()
                val textX = barXOffset + (barWidthPx - textWidth) / 2
                val textY = xAxisHeight + 10.dp.toPx() // Немного ниже оси X

                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        textMeasurer.measure(text = text, style = textStyle),
                        topLeft = Offset(x = textX, y = textY),
//                                textX,
//                                textY,
//                                paint.asFrameworkPaint() // Преобразуем в стандартный Paint для рисования текста
                    )
                }
            }
        }
    }
}

