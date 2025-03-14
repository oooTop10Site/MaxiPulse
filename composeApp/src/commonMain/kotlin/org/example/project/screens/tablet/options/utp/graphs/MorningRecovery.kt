package org.example.project.screens.tablet.options.utp.graphs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate
import org.example.project.ext.getCurrentWeekDates
import org.example.project.ext.toAnalizeMorningRecoveryBackgroundColor
import org.example.project.ext.toAnalizeTensionBackgroundColor
import org.example.project.ext.toText
import org.example.project.ext.toTextShort
import org.example.project.ext.toUIDayOfMonth
import org.example.project.screens.tablet.loadAnalize.TensionUI
import org.example.project.theme.MaxiPulsTheme
import org.jetbrains.compose.resources.stringResource
import kotlin.math.max

@Composable
fun MorningRecoveryGraph(
    modifier: Modifier = Modifier,
    days: List<LocalDate>,
    values: List<Int>,
    age: Int
) {
    val firstParam = when (age) {
        in 6..7 -> 94
        in 8..9 -> 91
        in 10..11 -> 88
        in 12..13 -> 85
        in 14..15 -> 80
        else -> 75
    }
    val secondParam = when (age) {
        in 6..7 -> 98
        in 8..9 -> 95
        in 10..11 -> 92
        in 12..13 -> 90
        in 14..15 -> 85
        else -> 80
    }
    val maxValue = 100f
    val zones = listOf(
        secondParam.toFloat() to maxValue,
        firstParam.toFloat() to secondParam.toFloat(),
        0f to firstParam.toFloat()
    )
    Column(modifier = modifier) {
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            // Фон с зонами (оставляем без изменений)
            Column(modifier = Modifier.fillMaxSize()) {
                zones.forEachIndexed { index, (start, end) ->
                    val shape = when {
                        index == zones.size - 1 -> RoundedCornerShape(bottomStart = 25.dp)
                        index == 0 -> RoundedCornerShape(topStart = 25.dp)
                        else -> RoundedCornerShape(0.dp)
                    }
                    Row(modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier.width(72.dp).fillMaxHeight(),
                            contentAlignment = Alignment.TopStart
                        ) {
                            Text(
                                text = end.toInt().toString(),
                                style = MaxiPulsTheme.typography.bold.copy(
                                    fontSize = 14.sp,
                                    color = MaxiPulsTheme.colors.uiKit.textColor,
                                    textAlign = TextAlign.Start
                                ),
                                modifier = Modifier.width(72.dp),
                                maxLines = 1,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Box(
                            Modifier.weight(1f).fillMaxHeight().background(
                                color = end.toInt().toAnalizeMorningRecoveryBackgroundColor(
                                    first = firstParam, second = secondParam, max = maxValue.toInt()
                                ),
                                shape = shape
                            ).clip(shape)
                        )
                    }
                }
            }

            // Точки с учетом смещения
            Row(
                modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth()
                    .padding(start = 112.dp, end = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                values.forEach { value ->
                    Box(Modifier, contentAlignment = Alignment.Center) {
                        val normalizedHeight = normalizeValueInZones(0f, value.toFloat(), zones)
                        val adjustedHeight = (0.15f + normalizedHeight).coerceIn(0f, 1f)
                        Column(
                            modifier = Modifier.fillMaxHeight(adjustedHeight)
                                .align(Alignment.BottomCenter),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier.size(30.dp).clip(CircleShape)
                                    .background(
                                        shape = CircleShape,
                                        color = MaxiPulsTheme.colors.uiKit.white
                                    ),
                                contentAlignment = Alignment.Center
                            ) {}
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }

            Canvas(
                modifier = Modifier.fillMaxSize().padding(start = 72.dp).border(
                    width = 1.dp,
                    color = MaxiPulsTheme.colors.uiKit.divider,
                    shape = RoundedCornerShape(topStart = 25.dp, bottomStart = 25.dp)
                ).padding(start = 40.dp, end = 40.dp)
            ) {
                val elementWidth = 30.dp.toPx()
                val spaceBetween = (size.width - elementWidth * values.size) / (values.size - 1)
                val circleCenters = mutableListOf<Offset>()
                val pointOffsetPx = 15.dp.toPx() // Смещение в пикселях (половина высоты точки)

                values.forEachIndexed { index, data ->
                    if (data != 0) {
                        val x = elementWidth / 2 + index * (elementWidth + spaceBetween)
                        val normalizedHeight = normalizeValueInZones(0f, data.toFloat(), zones)
                        val y = size.height * (1f - normalizedHeight.coerceIn(0f, 1f)) - pointOffsetPx // Смещаем точку вверх
                        circleCenters.add(Offset(x, y))
                    }
                }

                for (i in 0 until circleCenters.size - 1) {
                    if (circleCenters[i + 1].y >= -pointOffsetPx) { // Измененное условие
                        drawLine(
                            color = Color.White,
                            start = circleCenters[i],
                            end = circleCenters[i + 1],
                            strokeWidth = 4f
                        )
                    }
                }
            }
        }

        Spacer(Modifier.size(20.dp))
        // Точки с учетом смещения
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 112.dp, end = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            days.forEach { value ->
                Text(
                    text = "${value.toUIDayOfMonth()}\n${stringResource(value.dayOfWeek.toTextShort()).uppercase()}",
                    style = MaxiPulsTheme.typography.regular.copy(
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.width(40.dp)
                )
            }
        }
    }
}

// Вспомогательная функция для нормализации значений в зонах (без изменений)
private fun normalizeValueInZones(
    startValue: Float,
    endValue: Float,
    zones: List<Pair<Float, Float>>
): Float {
    var normalizedStart = 0f
    var normalizedEnd = 0f

    zones.sortedBy { it.first }.forEachIndexed { index, (zoneStart, zoneEnd) ->
        val zoneRange = zoneEnd - zoneStart
        val zoneFraction = 1f / zones.size

        if (startValue >= zoneStart && startValue <= zoneEnd) {
            val valueInZone = startValue - zoneStart
            normalizedStart = (index * zoneFraction) + (valueInZone / zoneRange * zoneFraction)
        }

        if (endValue >= zoneStart && endValue <= zoneEnd) {
            val valueInZone = endValue - zoneStart
            normalizedEnd = (index * zoneFraction) + (valueInZone / zoneRange * zoneFraction)
        }
    }

    return normalizedEnd - normalizedStart
}