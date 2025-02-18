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
import org.example.project.ext.getCurrentWeekDates
import org.example.project.ext.toAnalizeTensionBackgroundColor
import org.example.project.ext.toAnalizeTensionValueColor
import org.example.project.screens.tablet.loadAnalize.TensionUI
import org.example.project.theme.MaxiPulsTheme

@Composable
fun TensionGraph(modifier: Modifier = Modifier) {
    val trainingUIData = listOf(
        TensionUI(1.2),
        TensionUI(1.6),
        TensionUI(1.7),
        TensionUI(2.0),
        TensionUI(2.1),
        TensionUI(1.3),
        TensionUI(1.75)
    )
    val daysOfWeek = getCurrentWeekDates()
    val ratings = listOf(2.5, 2.0, 1.7)
    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            ratings.forEachIndexed { index, item ->
                val shape = when {
                    index == ratings.size - 1 -> RoundedCornerShape(
                        bottomStart = 25.dp
                    )

                    index == 0 -> RoundedCornerShape(
                        topStart = 25.dp,
                    )

                    else -> RoundedCornerShape(
                        0.dp
                    )
                }
                val next = ratings.getOrNull(index + 1) ?: 0.0
                Row(modifier = Modifier.weight(((item - next).toDouble() / 2.5).toFloat())) {
                    Box(
                        modifier = Modifier.width(72.dp).fillMaxHeight(),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Text(
                            text = item.toString(),
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
                            color = item.toAnalizeTensionBackgroundColor(),
                            shape = shape
                        ).clip(
                            shape
                        )
                    )
                }
            }
        }
        Row(
            modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth()
                .padding(start = 112.dp, end = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            trainingUIData.forEach {
                Box(Modifier, contentAlignment = Alignment.Center) {
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxHeight((it.value.toDouble() / 2.5).toFloat())
                            .width(55.dp).background(
                                color = MaxiPulsTheme.colors.uiKit.white.copy(
                                    alpha = 0.2f
                                ),
                                shape = RoundedCornerShape(
                                    topStart = 25.dp,
                                    topEnd = 25.dp
                                )
                            )
                            .background(
                                color = it.value.toAnalizeTensionValueColor(),
                                shape = RoundedCornerShape(
                                    topStart = 25.dp,
                                    topEnd = 25.dp
                                )
                            ).clip(
                                shape = RoundedCornerShape(
                                    topStart = 25.dp,
                                    topEnd = 25.dp
                                )
                            ).align(Alignment.BottomCenter)
                    ) {
                        Spacer(Modifier.weight(1f))
                    }
                    Column(
                        modifier = Modifier.fillMaxHeight(((it.value.toFloat() - 0.1f) / 2.5f))
                            .align(
                                Alignment.BottomCenter
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box(
                            modifier = Modifier.size(30.dp).clip(CircleShape)
                                .background(
                                    shape = CircleShape,
                                    color = MaxiPulsTheme.colors.uiKit.white
                                ), contentAlignment = Alignment.Center
                        ) {

                        }
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
            val elementWidth = 30.dp.toPx() // Ширина каждого элемента
            val spaceBetween =
                (size.width - elementWidth * trainingUIData.size) / (trainingUIData.size - 1) // Расстояние между элементами

            val circleCenters = mutableListOf<Offset>()

            trainingUIData.forEachIndexed { index, data ->
                if (data.value != 0.0) {
                    val x =
                        elementWidth / 2 + index * (elementWidth + spaceBetween) // Центр элемента по горизонтали
                    val y =
                        size.height * (1f - (((data.value).toFloat() - 0.2f) / 2.5f))
                    circleCenters.add(Offset(x, y))
                }
            }

            // Соединяем точки линиями
            for (i in 0 until circleCenters.size - 1) {
                println("circleCenters[i + 1].y - ${circleCenters[i + 1].y}")
                if (circleCenters[i + 1].y > 0) {
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

}