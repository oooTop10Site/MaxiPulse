package org.example.project.screens.tablet.loadAnalize

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.big_arrow_down
import maxipuls.composeapp.generated.resources.calendar
import maxipuls.composeapp.generated.resources.load_analysis
import maxipuls.composeapp.generated.resources.period
import maxipuls.composeapp.generated.resources.training
import maxipuls.composeapp.generated.resources.training_day
import org.example.project.ext.getCurrentWeekDates
import org.example.project.ext.toAnalizeColor
import org.example.project.ext.toByText
import org.example.project.ext.toTextShort
import org.example.project.ext.toUIDayOfMonth
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiSwitch
import org.example.project.theme.uiKit.MaxiTextFieldMenu
import org.example.project.utils.Constants
import org.example.project.utils.orEmpty
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class LoadAnalizeScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            LoadAnalizeViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        val trainingUIData = listOf(
            DayOfWeekTrainingUI(expectTrainingMark = 229, trainingMark = 121),
            DayOfWeekTrainingUI(expectTrainingMark = 210, trainingMark = 139),
            DayOfWeekTrainingUI(expectTrainingMark = 450, trainingMark = 321),
            DayOfWeekTrainingUI(expectTrainingMark = 300, trainingMark = 123),
            DayOfWeekTrainingUI(expectTrainingMark = 390, trainingMark = 490),
            DayOfWeekTrainingUI(expectTrainingMark = 440, trainingMark = 545),
            DayOfWeekTrainingUI(expectTrainingMark = 0, trainingMark = 0),
        )
        val daysOfWeek = getCurrentWeekDates()
        val ratings = listOf(800, 540, 360, 140)

        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
            Text(
                text = stringResource(Res.string.load_analysis),
                style = MaxiPulsTheme.typography.bold.copy(
                    fontSize = 20.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColor,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(Modifier.weight(1f, false), verticalAlignment = Alignment.CenterVertically) {
                    MaxiTextFieldMenu<String>(
                        items = emptyList(),
                        currentValue = "",
                        placeholderText = stringResource(Res.string.period),
                        itemToString = { it },
                        text = "",
                        modifier = Modifier.height(Constants.TextFieldHeight).weight(1f)
                    )
                    Spacer(Modifier.size(20.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        Icon(
                            painterResource(Res.drawable.calendar),
                            modifier = Modifier.size(30.dp),
                            contentDescription = null
                        )
                    }
                }
                Row(
                    Modifier.weight(1f, false),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = stringResource(Res.string.training),
                        style = MaxiPulsTheme.typography.regular.copy(
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor,
                        )
                    )
                    Spacer(Modifier.size(20.dp))
                    MaxiSwitch(
                        checked = false,
                        onCheckedChange = {},
                        modifier = Modifier.height(25.dp)
                    )
                    Spacer(Modifier.size(20.dp))
                    Text(
                        text = stringResource(Res.string.training_day),
                        style = MaxiPulsTheme.typography.regular.copy(
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor,
                        ),
                        modifier = Modifier.weight(1f, false)
                    )
                }
            }
            Box(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.fillMaxSize()) {
                    ratings.forEachIndexed { index, item ->
                        val shape = when {
                            index == ratings.size - 1 -> RoundedCornerShape(
                                bottomStart = 30.dp,
                                bottomEnd = 30.dp
                            )

                            index == 0 -> RoundedCornerShape(
                                topStart = 30.dp,
                                topEnd = 30.dp
                            )

                            else -> RoundedCornerShape(
                                0.dp
                            )
                        }
                        val next = ratings.getOrNull(index + 1) ?: 0
                        Row(modifier = Modifier.weight(((item - next).toDouble() / 800.0).toFloat())) {
                            Box(
                                modifier = Modifier.width(55.dp).fillMaxHeight(),
                                contentAlignment = Alignment.TopStart
                            ) {
                                Text(
                                    text = item.toString(),
                                    style = MaxiPulsTheme.typography.bold.copy(
                                        fontSize = 14.sp,
                                        color = MaxiPulsTheme.colors.uiKit.textColor,
                                        textAlign = TextAlign.Start
                                    ),
                                    modifier = Modifier.width(55.dp),
                                    maxLines = 1,
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
                Canvas(
                    modifier = Modifier.fillMaxSize().padding(start = 95.dp, end = 40.dp)
                ) {
                    val elementWidth = 75.dp.toPx() // Ширина каждого элемента
                    val spaceBetween =
                        (size.width - elementWidth * trainingUIData.size) / (trainingUIData.size - 1) // Расстояние между элементами

                    val circleCenters = mutableListOf<Offset>()

                    trainingUIData.forEachIndexed { index, data ->
                        if (data.trainingMark != 0) {
                            val x =
                                elementWidth / 2 + index * (elementWidth + spaceBetween) // Центр элемента по горизонтали
                            val y =
                                size.height * (1f - ((data.trainingMark.toFloat() - 37.5f) / 800f))
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
                Row(
                    modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth()
                        .padding(start = 95.dp, end = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    trainingUIData.forEach {
                        Box(Modifier, contentAlignment = Alignment.Center) {
                            Column(
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxHeight((it.expectTrainingMark.toDouble() / 800.0).toFloat())
                                    .width(55.dp)
                                    .background(
                                        color = MaxiPulsTheme.colors.uiKit.white,
                                        shape = RoundedCornerShape(
                                            topStart = 30.dp,
                                            topEnd = 30.dp
                                        )
                                    ).clip(
                                        shape = RoundedCornerShape(
                                            topStart = 30.dp,
                                            topEnd = 30.dp
                                        )
                                    ).align(Alignment.BottomCenter)
                            ) {
                                Text(
                                    text = it.expectTrainingMark.toString(),
                                    style = MaxiPulsTheme.typography.bold.copy(
                                        fontSize = 12.sp,
                                        lineHeight = 12.sp,
                                        color = it.expectTrainingMark.toAnalizeColor()
                                    ),
                                    modifier = Modifier.padding(top = 20.dp)
                                )
                                Spacer(Modifier.weight(1f))
                            }
                            Column(
                                modifier = Modifier.fillMaxHeight((it.trainingMark.toDouble() / 800.0).toFloat())
                                    .align(
                                        Alignment.BottomCenter
                                    ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Box(
                                    modifier = Modifier.size(75.dp).clip(CircleShape)
                                        .background(
                                            shape = CircleShape,
                                            color = it.trainingMark.toAnalizeColor()
                                        ), contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(Res.drawable.big_arrow_down),
                                        modifier = Modifier.rotate(if (it.trainingMark > it.expectTrainingMark) 180f else 0f)
                                            .size(width = 31.dp, height = 26.dp),
                                        contentDescription = null,
                                        tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                                    )
                                }
                                Spacer(Modifier.weight(1f))
                            }
                        }
                    }
                }


            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 95.dp, end = 40.dp, bottom = 20.dp, top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                daysOfWeek.forEach {
                    Column(
                        modifier = Modifier.width(75.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = it.toUIDayOfMonth(),
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 14.sp,
                                lineHeight = 14.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = stringResource(it.dayOfWeek.toTextShort()).uppercase(),
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 14.sp,
                                lineHeight = 14.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            println(daysOfWeek)
            Text(
                text = "${daysOfWeek.firstOrNull()?.dayOfMonth.orEmpty(1)} ${
                    if (daysOfWeek.firstOrNull()?.month != daysOfWeek.lastOrNull()?.month) stringResource(
                        daysOfWeek.firstOrNull()?.month.toByText()
                    ) else ""
                } - ${daysOfWeek.lastOrNull()?.dayOfMonth.orEmpty(1)} ${stringResource(daysOfWeek.lastOrNull()?.month.toByText())} ${daysOfWeek.lastOrNull()?.year}",
                style = MaxiPulsTheme.typography.medium.copy(
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    color = MaxiPulsTheme.colors.uiKit.textColor,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(bottom = 30.dp).fillMaxWidth()
            )
        }
    }
}


data class DayOfWeekTrainingUI(
    val expectTrainingMark: Int, // Оценка от 0 до 800
    val trainingMark: Int, // Оценка от 0 до 800

)

data class TensionUI(
    val value: Double

)

