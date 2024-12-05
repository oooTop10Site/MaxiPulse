package org.example.project.theme.uiKit

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.theme.MaxiPulsTheme
import kotlin.math.max
import kotlin.math.min

@Composable
fun ThresholdEditor() {
    val thresholds = remember { mutableStateListOf(0f, 30f, 50f, 70f, 85f, 100f) }

    val colors = listOf(
        Color(0xFFB3D8FF),
        Color(0xFF6DA6FF),
        Color(0xFF9EDDA6),
        Color(0xFFFFC085),
        Color(0xFFFF7D7D)
    )

    val regionNames = listOf("ЧСС покоя", "70%", "80%", "91%", "96%", "ЧСС max")

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val boxWidthPx = constraints.maxWidth.toFloat() // Общая ширина в пикселях
        val density = LocalDensity.current // Для перевода dp <-> px

        Column {
            Text(
                text = "Изменение порогов ЧСС",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            // График с порогами
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    for (i in 1 until thresholds.size) {
                        val startX = (thresholds[i - 1] / 100f) * boxWidthPx
                        val endX = (thresholds[i] / 100f) * boxWidthPx
                        drawRect(
                            color = colors[i - 1],
                            topLeft = Offset(startX, 0f),
                            size = androidx.compose.ui.geometry.Size(endX - startX, size.height)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Облака со значениями под графиком

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {

                for (i in 1 until thresholds.size - 1) {
                    Box(
                        modifier = Modifier
                            .offset {
                                val xOffsetPx = (thresholds[i] / 100f) * boxWidthPx
                                with(density) { IntOffset(x = xOffsetPx.toInt(), y = 0) }
                            }

                            .pointerInput(Unit) {
                                detectDragGestures(
                                ) { change, dragAmount ->
                                    change.consume()
                                    val deltaPercent = (dragAmount.x / boxWidthPx) * 100f

                                    // Обновляем временное значение порога, но не меняем значение в списке thresholds сразу
                                    thresholds[i] = (thresholds[i] + deltaPercent)
                                        .coerceIn(thresholds[i - 1] + 7, thresholds[i + 1] - 7)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White,
                                shadowElevation = 4.dp
                            ) {
                                Text(
                                    text = "${thresholds[i].toInt()}%",
                                    modifier = Modifier.padding(8.dp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    for (i in listOf(thresholds.first(), thresholds.last())) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White,
                                shadowElevation = 4.dp
                            ) {
                                Text(
                                    text = "${i.toInt()}%",
                                    modifier = Modifier.padding(8.dp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
