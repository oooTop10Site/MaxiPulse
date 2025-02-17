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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.big_arrow_down
import org.example.project.ext.getCurrentWeekDates
import org.example.project.ext.toAnalizeColor
import org.example.project.screens.tablet.loadAnalize.DayOfWeekTrainingUI
import org.example.project.theme.MaxiPulsTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun TensionGraph(modifier: Modifier = Modifier) {
    val trainingUIData = listOf(
        DayOfWeekTrainingUI(expectTrainingMark = 229, trainingMark = 229),
        DayOfWeekTrainingUI(expectTrainingMark = 210, trainingMark = 210),
        DayOfWeekTrainingUI(expectTrainingMark = 450, trainingMark = 450),
        DayOfWeekTrainingUI(expectTrainingMark = 300, trainingMark = 300),
        DayOfWeekTrainingUI(expectTrainingMark = 390, trainingMark = 390),
        DayOfWeekTrainingUI(expectTrainingMark = 440, trainingMark = 440),
        DayOfWeekTrainingUI(expectTrainingMark = 0, trainingMark = 0),
    )
    val daysOfWeek = getCurrentWeekDates()
    val ratings = listOf(800, 540, 360, 140)
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
                val next = ratings.getOrNull(index + 1) ?: 0
                Row(modifier = Modifier.weight(((item - next).toDouble() / 800.0).toFloat())) {
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
                            color = item.toAnalizeColor().copy(alpha = 0.5f),
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
                        modifier = Modifier.fillMaxHeight((it.trainingMark.toDouble() / 800.0).toFloat())
                            .width(55.dp)
                            .background(
                                color = it.trainingMark.toAnalizeColor(),
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
                        modifier = Modifier.fillMaxHeight(((it.trainingMark.toDouble()-15) / 800.0).toFloat())
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
                if (data.trainingMark != 0) {
                    val x =
                        elementWidth / 2 + index * (elementWidth + spaceBetween) // Центр элемента по горизонтали
                    val y =
                        size.height * (1f - (((data.trainingMark).toFloat() - 30f) / 800f))
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