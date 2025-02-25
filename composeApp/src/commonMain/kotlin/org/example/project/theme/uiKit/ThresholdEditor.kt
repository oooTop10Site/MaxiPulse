package org.example.project.theme.uiKit

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.chss_max
import maxipuls.composeapp.generated.resources.chss_min
import org.example.project.ext.roundToIntOrNull
import org.example.project.theme.MaxiPulsTheme
import org.example.project.utils.orEmpty
import org.jetbrains.compose.resources.stringResource
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun ThresholdEditor(modifier: Modifier) {
    val thresholds = remember {
        mutableStateListOf(
            Pair(0f, listOf(Color(0xFF364562), Color(0xFFAEC6F3))),
            Pair(70f, listOf(Color(0xFFAEC6F3), Color(0xFF3B6ECF))),
            Pair(80f, listOf(Color(0xFF3B6ECF), Color(0xFF96D34B))),
            Pair(91f, listOf(Color(0xFF96D34B), Color(0xFFFFA93A))),
            Pair(96f, listOf(Color(0xFFFFA93A), Color(0xFFDF0B40))),
            Pair(100f, listOf(Color(0xFFDF0B40), Color(0xFF6C001B))),
        )
    }
    val min = 0
    val max = 230
    val delta = abs(max - min)

    val colors = listOf(
        Color(0xFFB3D8FF),
        Color(0xFF6DA6FF),
        Color(0xFF9EDDA6),
        Color(0xFFFFC085),
        Color(0xFFFF7D7D)
    )
    BoxWithConstraints(
        modifier = modifier
    ) {
        var boxWidthPx by remember { mutableStateOf(0f) }
        val density = LocalDensity.current // Для перевода dp <-> px

        Column() {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (i in listOf(thresholds.first(), thresholds.last())) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.size(width = 70.dp, height = 50.dp)
                                    .background(
                                        shape = RoundedCornerShape(25.dp),
                                        brush = Brush.horizontalGradient(i.second)
                                    ).alpha(0.75f)
                            ) {
                                Text(
                                    text = (delta.toFloat() * i.first / 100).roundToInt()
                                        .toString(),
                                    modifier = Modifier.padding(8.dp),
                                    style = MaxiPulsTheme.typography.bold.copy(
                                        fontSize = 15.sp,
                                        lineHeight = 15.sp,
                                        color = MaxiPulsTheme.colors.uiKit.textColor
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                            Spacer(
                                modifier = Modifier.size(37.dp),
                            )
                        }
                    }
                }
                for (i in 1 until thresholds.size - 1) {
                    Box(
                        modifier = Modifier
                            .offset {
                                val xOffsetPx = (thresholds[i].first / 100f) * boxWidthPx
                                with(density) { IntOffset(x = xOffsetPx.toInt(), y = 0) }
                            }

                            .pointerInput(Unit) {
                                detectDragGestures(
                                ) { change, dragAmount ->
                                    change.consume()
                                    val deltaPercent = (dragAmount.x / boxWidthPx) * 100f
                                    try {
                                        thresholds[i] = thresholds[i].copy(
                                            first = (thresholds[i].first + deltaPercent)
                                                .coerceIn(
                                                    thresholds[i - 1].first + 7,
                                                    thresholds[i + 1].first - 7
                                                )
                                        )
                                    } catch (e: Exception) {

                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.size(width = 70.dp, height = 50.dp)
                                    .background(
                                        shape = RoundedCornerShape(25.dp),
                                        brush = Brush.horizontalGradient(thresholds[i].second)
                                    ).alpha(0.75f)
                            ) {
                                Text(
                                    text = (delta.toFloat() * thresholds[i].first / 100).roundToIntOrNull().orEmpty()
                                        .toString(),
                                    modifier = Modifier.padding(8.dp),
                                    style = MaxiPulsTheme.typography.bold.copy(
                                        fontSize = 16.sp,
                                        lineHeight = 16.sp,
                                        color = MaxiPulsTheme.colors.uiKit.textColor
                                    )
                                )
                            }
                            Spacer(
                                modifier = Modifier.size(37.dp),
                            )
                        }
                    }
                }
            }
            // График с порогами
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val colorDivider = MaxiPulsTheme.colors.uiKit.divider
                val first = colors.first()
                val last = colors.last()
                Box(
                    Modifier.width(35.dp).fillMaxHeight().background(
                        color = first,
                        shape = RoundedCornerShape(topStart = 25.dp, bottomStart = 25.dp)
                    )
                )
                Canvas(
                    modifier = Modifier.weight(1f).fillMaxHeight()
                ) {
                    boxWidthPx = size.width
                    for (i in 1 until thresholds.size) {
                        val startX = (thresholds[i - 1].first / 100f) * size.width
                        val endX = (thresholds[i].first / 100f) * size.width
                        drawRect(
                            color = colors[i - 1],
                            topLeft = Offset(startX, 0f),
                            size = androidx.compose.ui.geometry.Size(endX - startX, size.height)
                        )
                    }
                }
                Box(
                    Modifier.width(35.dp).fillMaxHeight().background(
                        color = last,
                        shape = RoundedCornerShape(topEnd = 25.dp, bottomEnd = 25.dp)
                    )
                )

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (i in listOf(thresholds.first(), thresholds.last())) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Spacer(
                                modifier = Modifier.size(37.dp),
                            )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.size(width = 70.dp, height = 50.dp)
                                    .background(
                                        shape = RoundedCornerShape(25.dp),
                                        brush = Brush.horizontalGradient(i.second)
                                    ).alpha(0.75f)
                            ) {
                                Text(
                                    text = when (i.first) {
                                        0f -> {
                                            stringResource(Res.string.chss_min)
                                        }

                                        100f -> {
                                            stringResource(Res.string.chss_max)
                                        }

                                        else -> {
                                            "${i.first.toInt()}%"
                                        }
                                    },
                                    modifier = Modifier.padding(8.dp),
                                    style = MaxiPulsTheme.typography.bold.copy(
                                        fontSize = 15.sp,
                                        lineHeight = 15.sp,
                                        color = MaxiPulsTheme.colors.uiKit.textColor
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                for (i in 1 until thresholds.size - 1) {
                    Box(
                        modifier = Modifier
                            .offset {
                                val xOffsetPx = (thresholds[i].first / 100f) * boxWidthPx
                                with(density) { IntOffset(x = xOffsetPx.toInt(), y = 0) }
                            }

                            .pointerInput(Unit) {
                                detectDragGestures(
                                ) { change, dragAmount ->
                                    change.consume()
                                    val deltaPercent = (dragAmount.x / boxWidthPx) * 100f
                                    try {
                                        thresholds[i] = thresholds[i].copy(
                                            first = (thresholds[i].first + deltaPercent)
                                                .coerceIn(
                                                    thresholds[i - 1].first + 7,
                                                    thresholds[i + 1].first - 7
                                                )
                                        )
                                    } catch (e: Exception) {

                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Spacer(
                                modifier = Modifier.height(37.dp),
                            )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.size(width = 70.dp, height = 50.dp)
                                    .background(
                                        shape = RoundedCornerShape(25.dp),
                                        brush = Brush.horizontalGradient(thresholds[i].second)
                                    ).alpha(0.75f)
                            ) {
                                Text(
                                    text = "${thresholds[i].first.toInt()}%",
                                    modifier = Modifier.padding(8.dp),
                                    style = MaxiPulsTheme.typography.bold.copy(
                                        fontSize = 16.sp,
                                        lineHeight = 16.sp,
                                        color = MaxiPulsTheme.colors.uiKit.textColor
                                    )
                                )
                            }
                        }
                    }
                }

            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth().padding(vertical = 50.dp)
        ) {
            for (i in listOf(thresholds.first(), thresholds.last())) {
                Box(modifier = Modifier.width(width = 70.dp).fillMaxHeight().offset {
                    val xOffsetPx = (i.first / 100f) * boxWidthPx
                    with(density) { IntOffset(x = xOffsetPx.toInt(), y = 0) }
                }, contentAlignment = Alignment.Center) {
                    VerticalDivider(
                        color = Color.Transparent,
                        modifier = Modifier.fillMaxHeight().width(2.dp)
                            .background(brush = Brush.horizontalGradient(colors = i.second.map {
                                it.copy(alpha = 0.75f)
                            }))
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth().padding(vertical = 50.dp)
        ) {
            for (i in 1 until thresholds.size - 1) {
                Box(modifier = Modifier.width(width = 70.dp).fillMaxHeight().offset {
                    val xOffsetPx = (thresholds[i].first / 100f) * boxWidthPx
                    with(density) { IntOffset(x = xOffsetPx.toInt(), y = 0) }
                }
                    .pointerInput(Unit) {
                        detectDragGestures(
                        ) { change, dragAmount ->
                            change.consume()
                            val deltaPercent = (dragAmount.x / boxWidthPx) * 100f
                            try {
                                thresholds[i] = thresholds[i].copy(
                                    first = (thresholds[i].first + deltaPercent)
                                        .coerceIn(
                                            thresholds[i - 1].first + 7,
                                            thresholds[i + 1].first - 7
                                        )
                                )
                            } catch (e: Exception) {

                            }
                        }
                    }, contentAlignment = Alignment.Center) {
                    VerticalDivider(
                        color = Color.Transparent,
                        modifier = Modifier.fillMaxHeight().width(2.dp)
                            .background(brush = Brush.horizontalGradient(colors = thresholds[i].second.map {
                                it.copy(alpha = 0.75f)
                            }))
                    )
                }
            }
        }
    }
}
