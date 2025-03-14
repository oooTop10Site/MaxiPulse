package org.example.project.screens.tablet.miniPulseWidget.contents.RpeScale

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.ext.toDayOfWeekFullDate
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.BackIcon
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.NextIcon
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.avg_score_rpe
import maxipuls.composeapp.generated.resources.info_ic
import maxipuls.composeapp.generated.resources.trash
import maxipuls.composeapp.generated.resources.trimp
import maxipuls.composeapp.generated.resources.trimp_avg
import org.example.project.domain.model.log.LogUI
import org.example.project.domain.model.rpe.RpeUI
import org.example.project.ext.clickableBlank
import org.example.project.ext.rpeToColor
import org.example.project.ext.toUI
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.theme.uiKit.MaxiTextFieldMenu
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class RpeScaleScreen(private val modifier: Modifier = Modifier) : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { RpeScaleViewModel() }
        val state by viewModel.stateFlow.collectAsState()

        LaunchedEffect(viewModel) {
            viewModel.loadTrainings()
        }

        MaxiPageContainer(modifier = modifier, topBar = {
            Column(modifier = Modifier.fillMaxWidth().height(90.dp)) {
                Spacer(Modifier.size(30.dp))
                Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                    MaxiTextFieldMenu(
                        currentValue = state.filter,
                        items = state.filters,
                        onChangeWorkScope = {
                            viewModel.changeFilter(it)
                        },
                        modifier = Modifier.height(Constants.TextFieldHeight).width(210.dp).align(
                            Alignment.CenterStart
                        ),
                        text = state.filter,
                        placeholderText = "",
                        itemToString = { it }
                    )
                    Row(
                        modifier = Modifier.width(310.dp).align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BackIcon(
                            modifier = Modifier.size(30.dp),
                            iconSize = 18.dp,
                            isDebounce = false
                        ) {
                            viewModel.decrementWeek()
                        }

                        Spacer(Modifier.weight(1f))

                        Text(
                            text = "${
                                state.selectWeek.firstOrNull()?.toUI().orEmpty()
                            } - ${state.selectWeek.lastOrNull()?.toUI().orEmpty()}",
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(Modifier.weight(1f))

                        NextIcon(
                            modifier = Modifier.size(30.dp),
                            iconSize = 18.dp,
                            isDebounce = false
                        ) {
                            viewModel.incrementWeek()
                        }
                    }

                    Icon(
                        painter = painterResource(Res.drawable.info_ic),
                        tint = MaxiPulsTheme.colors.uiKit.primary,
                        modifier = Modifier.size(40.dp).align(Alignment.CenterEnd),
                        contentDescription = null
                    )
                }
                Spacer(Modifier.size(30.dp))
            }
        }) {

            Row(modifier = Modifier.fillMaxWidth()) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(state.trainings) {
                        RpeTrainingCard(
                            modifier = Modifier.fillMaxWidth().height(125.dp),
                            logUI = it,
                            isSelect = it == state.selectTraining
                        ) {
                            viewModel.selectTraining(it)
                        }
                    }
                }
                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )

                Column(modifier = Modifier.weight(1f)) {
                    state.selectTraining?.let {
                        val average = it.sportsmen.map { it.score }.average()
                        Row(modifier = Modifier.fillMaxWidth().height(57.dp)) {
                            Box(
                                modifier = Modifier.fillMaxHeight().weight(0.9f),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = stringResource(Res.string.avg_score_rpe),
                                    style = MaxiPulsTheme.typography.medium.copy(
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp,
                                        color = MaxiPulsTheme.colors.uiKit.textColor,
                                    ),
                                    modifier = Modifier.padding(horizontal = 20.dp), maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            VerticalDivider(
                                modifier = Modifier.fillMaxHeight(),
                                color = MaxiPulsTheme.colors.uiKit.divider
                            )
                            Row(
                                modifier = Modifier.fillMaxHeight().weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(Modifier.weight(0f + (average.toFloat() / 10)))
                                Text(
                                    text = average.toString(),
                                    style = MaxiPulsTheme.typography.bold.copy(
                                        fontSize = 20.sp,
                                        lineHeight = 20.sp,
                                        color = average.rpeToColor(),
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                if (1f - (average.toFloat() / 10) > 0f) {
                                    Spacer(Modifier.weight(1f - (average.toFloat() / 10)))
                                }

                            }
                            VerticalDivider(
                                modifier = Modifier.fillMaxHeight(),
                                color = MaxiPulsTheme.colors.uiKit.divider
                            )
                            Box(
                                modifier = Modifier.fillMaxHeight().width(58.dp),
                                contentAlignment = Alignment.Center
                            ) {

                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaxiPulsTheme.colors.uiKit.divider
                        )
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(it.sportsmen) {
                                RowItem(
                                    rpeUI = it,
                                    modifier = Modifier.fillMaxWidth().height(44.dp)
                                )
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = MaxiPulsTheme.colors.uiKit.divider
                                )
                            }
                        }

                    }
                }
            }
            state.selectTraining?.let {
                Box(modifier = Modifier.fillMaxSize()) {
                    val average = it.sportsmen.map { it.score }.average()
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 57.dp)) {
                        Spacer(Modifier.weight(1f))
                        Row(modifier = Modifier.weight(1f)) {
                            Spacer(Modifier.weight(0.9f))
                            Spacer(Modifier.weight(0f + (average.toFloat() / 10)))
                            DashedVerticalDivider(
                                modifier = Modifier.fillMaxHeight(),
                                color = average.rpeToColor()
                            )
                            if (1f - (average.toFloat() / 10) > 0f) {
                                Spacer(Modifier.weight(1f - (average.toFloat() / 10)))
                            }
                            Spacer(Modifier.size(58.dp))
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun DashedVerticalDivider(
    color: Color = Color.Gray,
    thickness: Dp = 2.dp,
    dashHeight: Dp = 2.dp,
    gapHeight: Dp = 2.dp,
    modifier: Modifier = Modifier.fillMaxHeight()
) {
    Canvas(modifier = modifier.width(thickness)) {
        val dashPx = dashHeight.toPx()
        val gapPx = gapHeight.toPx()
        var y = 0f

        while (y < size.height) {
            val endY = (y + dashPx).coerceAtMost(size.height) // Обрезаем в пределах высоты
            drawLine(
                color = color,
                start = Offset(x = size.width / 2, y = y),
                end = Offset(x = size.width / 2, y = endY),
                strokeWidth = thickness.toPx()
            )
            y += dashPx + gapPx
        }
    }
}


@Composable
private fun RowItem(modifier: Modifier = Modifier, rpeUI: RpeUI) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier.fillMaxHeight().weight(0.9f),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Text(
                    text = rpeUI.sportsmanUI.number.toString(),
                    style = MaxiPulsTheme.typography.bold.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.size(3.dp))
                Text(
                    text = rpeUI.sportsmanUI.fioShort,
                    style = MaxiPulsTheme.typography.medium.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        Row(
            modifier = Modifier.fillMaxHeight().weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val graphWeight = rpeUI.score.toDouble() / 10.0
            Spacer(Modifier.size(8.dp))
            Box(
                modifier = Modifier
                    .weight(graphWeight.toFloat()).height(14.dp)
                    .background(color = rpeUI.score.rpeToColor(), shape = RoundedCornerShape(25.dp))
            )
            if (1f - graphWeight.toFloat() > 0f) {
                Spacer(Modifier.weight(1f - graphWeight.toFloat()))
            }
            Spacer(Modifier.size(8.dp))

        }
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaxiPulsTheme.colors.uiKit.divider
        )
        Box(
            modifier = Modifier.fillMaxHeight().width(58.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = rpeUI.score.toString(),
                style = MaxiPulsTheme.typography.bold.copy(
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    color = rpeUI.score.rpeToColor()
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
private fun RpeTrainingCard(
    modifier: Modifier = Modifier,
    logUI: LogUI,
    isSelect: Boolean,
    onClick: () -> Unit,
) {
    val textColor =
        if (isSelect) MaxiPulsTheme.colors.uiKit.lightTextColor else MaxiPulsTheme.colors.uiKit.textColor
    Column(
        modifier.background(
            color = if (isSelect) MaxiPulsTheme.colors.uiKit.grey800 else MaxiPulsTheme.colors.uiKit.card,
            shape = RoundedCornerShape(25.dp)
        ).clip(RoundedCornerShape(25.dp)).clickableBlank() {
            onClick()
        }.animateContentSize()
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = logUI.dateText,
                    style = MaxiPulsTheme.typography.bold.copy(
                        color = textColor,
                        fontSize = 14.sp,
                        lineHeight = 14.sp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, false),
                    maxLines = 1

                )
                Spacer(Modifier.size(5.dp))

                Text(
                    text = logUI.timeText,
                    style = MaxiPulsTheme.typography.regular.copy(
                        color = textColor,
                        fontSize = 14.sp,
                        lineHeight = 14.sp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, false),
                    maxLines = 1

                )

                VerticalDivider(
                    modifier = Modifier.padding(horizontal = 10.dp).height(17.dp),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )

                Text(
                    text = logUI.event.type.title,
                    style = MaxiPulsTheme.typography.bold.copy(
                        color = textColor,
                        fontSize = 14.sp,
                        lineHeight = 14.sp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f, false)
                )

            }

            Text(
                text = logUI.durationText,
                style = MaxiPulsTheme.typography.regular.copy(
                    color = textColor,
                    fontSize = 14.sp,
                    lineHeight = 14.sp
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1

            )
        }

        Spacer(modifier = Modifier.size(20.dp))


        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = logUI.event.title,
                style = MaxiPulsTheme.typography.regular.copy(
                    color = textColor,
                    fontSize = 14.sp,
                    lineHeight = 14.sp
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1

            )

            VerticalDivider(
                modifier = Modifier.padding(horizontal = 10.dp).height(17.dp),
                color = MaxiPulsTheme.colors.uiKit.divider
            )

            Text(
                text = logUI.sportsmanUI.fio,
                style = MaxiPulsTheme.typography.bold.copy(
                    color = textColor,
                    fontSize = 14.sp,
                    lineHeight = 14.sp
                ),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                maxLines = 1

            )
        }

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = "${stringResource(Res.string.trimp_avg)} ${logUI.avgTrimp}",
            style = MaxiPulsTheme.typography.regular.copy(
                color = if (isSelect) MaxiPulsTheme.colors.uiKit.lightTextColor else MaxiPulsTheme.colors.uiKit.primary,
                fontSize = 14.sp,
                lineHeight = 14.sp
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.size(20.dp))

    }

}