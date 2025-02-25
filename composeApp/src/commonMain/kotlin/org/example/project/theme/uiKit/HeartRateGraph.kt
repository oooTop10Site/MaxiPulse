package org.example.project.theme.uiKit

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import org.example.project.domain.model.sportsman.HeartBit
import org.example.project.ext.getEvenlyDistributedMills
import org.example.project.ext.secondsToUI
import org.example.project.ext.toTimeUI
import org.example.project.theme.MaxiPulsTheme
import org.example.project.utils.orEmpty
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import org.example.project.utils.Constants

@Composable
fun HeartRateGraph(
    modifier: Modifier = Modifier,
    heartRateData: List<HeartBit>,
    showY: Boolean = true,
    showTime: Boolean = false
) {
    val state = rememberLazyListState()
    val maxValue = 230f
    val zones = listOf(
        Constants.zone1Start.toFloat() to Constants.zone1End.toFloat(),        // Голубая зона
        Constants.zone2Start.toFloat() to Constants.zone2End.toFloat(),    // Синяя зона
        Constants.zone3Start.toFloat() to Constants.zone3End.toFloat(),       // Зеленая зона
        Constants.zone4Start.toFloat() to Constants.zone4End.toFloat(),      // Оранжевая зона
        Constants.zone5Start.toFloat() to Constants.zone5End.toFloat(),    // Красная зона
    )

    val zoneColors = listOf(
        MaxiPulsTheme.colors.uiKit.zone1, // Голубая
        MaxiPulsTheme.colors.uiKit.zone2, // Синяя
        MaxiPulsTheme.colors.uiKit.zone3, // Зеленая
        MaxiPulsTheme.colors.uiKit.zone4, // Оранжевая
        MaxiPulsTheme.colors.uiKit.zone5, // Красная
    )

    val textMeasurer = rememberTextMeasurer()
    val pointWidth = 3.dp // Ширина одной полоски
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
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
            BoxWithConstraints(modifier = Modifier.weight(1f).fillMaxHeight()) {
                var width by remember { mutableStateOf(maxWidth) }
                LaunchedEffect(heartRateData) {
                    println("hear - ${heartRateData.size}")
                    println("--------------")
                    width = (maxWidth - pointWidth * heartRateData.size).let {
                        if (it <= 0.dp) {
                            0.dp
                        } else it
                    }
                }
                LazyRow(
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(25.dp)),
                    reverseLayout = true,
                    state = state
                ) {
                    item {
                        Canvas(
                            modifier = Modifier
                                .width(if (width == 0.dp) pointWidth * heartRateData.size else maxWidth) // Ширина графика
                                .fillMaxHeight()
                        ) {
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
                            val pointGap = pointWidth.toPx() // Расстояние между точками
                            val path = Path()

                            heartRateData.forEachIndexed { index, value ->
                                // Определяем y-координату точки
                                val y = zones.foldIndexed(0f) { i, acc, (startHR, endHR) ->
                                    if (value.value.toFloat() in startHR..endHR) {
                                        val zoneStartY = graphHeight - (i + 1) * zoneHeight
                                        val zoneEndY = graphHeight - i * zoneHeight
                                        val zoneRange = endHR - startHR
                                        val normalizedY =
                                            ((value.value - startHR) / zoneRange) * zoneHeight
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
                }
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
                val mills = heartRateData.getEvenlyDistributedMills(5)
                mills.forEach {
                    Text(
                        text = ((it - mills.firstOrNull().orEmpty()) / 1000).secondsToUI(),
                        style = textStyle
                    )
                }
            }
        }
    }
}
