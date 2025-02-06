package org.example.project.screens.tablet.options.utp

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.microcycle
import maxipuls.composeapp.generated.resources.period_readiness
import maxipuls.composeapp.generated.resources.stage_readiness
import maxipuls.composeapp.generated.resources.start_tarining
import maxipuls.composeapp.generated.resources.utp_title
import maxipuls.composeapp.generated.resources.year_education
import org.example.project.ext.clickableBlank
import org.example.project.ext.toText
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiCheckbox
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.TopBarTitle
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.stringResource

class UtpScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            UtpViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        val screenSize = ScreenSize.currentOrThrow
        val buttonDivision = when (screenSize.heightSizeClass) {
            WindowHeightSizeClass.Medium -> 1.4
            WindowHeightSizeClass.Expanded -> 1.0
            WindowHeightSizeClass.Compact -> 1.7
            else -> 1.5
        }
        MaxiPageContainer() {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopBarTitle(
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp),
                    showCurrentTime = false,
                    text = stringResource(Res.string.utp_title)
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f, false),
                        verticalArrangement = Arrangement.spacedBy(13.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.stage_readiness),
                            style = MaxiPulsTheme.typography.medium.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 16.sp,
                                lineHeight = 16.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        state.stageOfReadiness.forEach {
                            SelectableItem(modifier = Modifier,
                                value = it == state.selectStageOfReadiness,
                                text = it,
                                onChange = {
                                    viewModel.changeSelectStageOfReadiness(it)
                                })
                        }

                    }

                    VerticalDivider(
                        Modifier.height(172.dp).align(Alignment.CenterVertically),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )


                    Column(
                        modifier = Modifier.weight(0.5f, false),
                        verticalArrangement = Arrangement.spacedBy(13.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.year_education),
                            style = MaxiPulsTheme.typography.medium.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 16.sp,
                                lineHeight = 16.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        state.years.forEach {
                            SelectableItem(modifier = Modifier.width(90.dp),
                                value = it == state.selectYear,
                                text = it.toString(),
                                onChange = {
                                    viewModel.changeSelectYear(it)
                                })
                        }

                    }

                    VerticalDivider(
                        Modifier.height(172.dp).align(Alignment.CenterVertically),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )

                    Column(
                        modifier = Modifier.weight(1f, false),
                        verticalArrangement = Arrangement.spacedBy(13.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.period_readiness),
                            style = MaxiPulsTheme.typography.medium.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 16.sp,
                                lineHeight = 16.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        state.periodOfReadiness.forEach {
                            SelectableItem(modifier = Modifier.widthIn(max = 200.dp),
                                value = it == state.selectPeriodOfReadiness,
                                text = it.toString(),
                                onChange = {
                                    viewModel.changeSelectPeriodOfReadiness(it)
                                })
                        }
                    }
                    Spacer(Modifier.weight(1f))
                }

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    state.readiness.forEach { text ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            modifier = Modifier.clickableBlank {
                                viewModel.changeSelectReadiness(text)
                            }
                        ) {
                            Text(
                                text = text,
                                style = MaxiPulsTheme.typography.regular.copy(
                                    color = MaxiPulsTheme.colors.uiKit.textColor,
                                    fontSize = 14.sp,
                                    lineHeight = 14.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            MaxiCheckbox(
                                checked = text == state.selectReadiness,
                                onCheckedChange = {
                                    viewModel.changeSelectReadiness(text)
                                },
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.microcycle),
                        style = MaxiPulsTheme.typography.medium.copy(
                            color = MaxiPulsTheme.colors.uiKit.textColor,
                            fontSize = 16.sp,
                            lineHeight = 16.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    state.microCycle.forEach { text ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickableBlank {
                                viewModel.changeSelectMicroCycle(text)
                            },
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                        ) {
                            Text(
                                text = text,
                                style = MaxiPulsTheme.typography.regular.copy(
                                    color = MaxiPulsTheme.colors.uiKit.textColor,
                                    fontSize = 14.sp,
                                    lineHeight = 14.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            MaxiCheckbox(
                                checked = text == state.selectMicroCycle,
                                onCheckedChange = {
                                    viewModel.changeSelectMicroCycle(text)
                                },
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )

                Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                    state.days.forEach { dayUtp ->
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickableBlank {
                                }.padding(bottom = 15.dp),
                                horizontalArrangement = Arrangement.spacedBy(20.dp),
                            ) {
                                Text(
                                    text = (dayUtp.day.ordinal + 1).toString(),
                                    style = MaxiPulsTheme.typography.regular.copy(
                                        color = MaxiPulsTheme.colors.uiKit.textColor,
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.size(24.dp).clip(RoundedCornerShape(5.dp))
                                        .border(
                                            width = 1.dp,
                                            shape = RoundedCornerShape(5.dp),
                                            color = MaxiPulsTheme.colors.uiKit.textFieldStroke
                                        )
                                ) {
                                    if (state.selectDay == dayUtp.day) {
                                        Box(
                                            modifier = Modifier.size(16.dp)
                                                .clip(RoundedCornerShape(5.dp)).background(
                                                    color = MaxiPulsTheme.colors.uiKit.primary,
                                                    shape = RoundedCornerShape(2.dp)
                                                )
                                        )
                                    }

                                }
                            }
                            Box(
                                Modifier.fillMaxWidth().weight(1f),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Box(
                                    Modifier.fillMaxHeight((dayUtp.progressTraining.toDouble() / Constants.MAX_TRAINING_INTENSITY.toDouble()).toFloat())
                                        .width(80.dp).background(
                                            color = dayUtp.color,
                                            shape = RoundedCornerShape(
                                                topStart = 50.dp,
                                                topEnd = 50.dp
                                            )
                                        ).clip(
                                            shape = RoundedCornerShape(
                                                topStart = 50.dp,
                                                topEnd = 50.dp
                                            )
                                        )
                                )
                            }
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                color = MaxiPulsTheme.colors.uiKit.divider
                            )

                            Text(
                                text = stringResource(dayUtp.day.toText()),
                                style = MaxiPulsTheme.typography.regular.copy(
                                    color = MaxiPulsTheme.colors.uiKit.textColor,
                                    fontSize = 14.sp,
                                    lineHeight = 14.sp
                                ),
                                maxLines = 1,
                                modifier = Modifier.padding(vertical = 14.dp),
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                MaxiButton(
                    onClick = {

                    },
                    shape = RoundedCornerShape(0.dp),
                    text = stringResource(
                        Res.string.start_tarining
                    ),
                    modifier = Modifier.fillMaxWidth().height((84.0/buttonDivision).dp),
                )
            }
        }
    }
}

@Composable
private fun SelectableItem(
    modifier: Modifier = Modifier,
    value: Boolean,
    onChange: () -> Unit,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier.clickableBlank {
            onChange()
        },
    ) {
        Text(
            text = text,
            style = MaxiPulsTheme.typography.regular.copy(
                color = MaxiPulsTheme.colors.uiKit.textColor,
                fontSize = 14.sp,
                lineHeight = 14.sp
            ),
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        MaxiCheckbox(checked = value, onCheckedChange = {
            onChange()
        }, modifier = Modifier.size(24.dp))
    }

}