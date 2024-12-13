package org.example.project.screens.training

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.theme.uiKit.MaxiPageContainer
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.add_ic
import maxipuls.composeapp.generated.resources.chss
import maxipuls.composeapp.generated.resources.chss_max
import maxipuls.composeapp.generated.resources.info_ic
import maxipuls.composeapp.generated.resources.ok
import maxipuls.composeapp.generated.resources.start
import maxipuls.composeapp.generated.resources.stop
import maxipuls.composeapp.generated.resources.training
import maxipuls.composeapp.generated.resources.training_info
import maxipuls.composeapp.generated.resources.training_info_desc1
import maxipuls.composeapp.generated.resources.training_info_desc2
import maxipuls.composeapp.generated.resources.training_info_desc3
import maxipuls.composeapp.generated.resources.training_info_desc4
import maxipuls.composeapp.generated.resources.trimp
import org.example.project.domain.model.sportsman.TrainingSportsmanUI
import org.example.project.domain.model.test.TestStatus
import org.example.project.ext.clickableBlank
import org.example.project.ext.formatSeconds
import org.example.project.screens.root.RootNavigator
import org.example.project.screens.training.trainingResult.TrainingResultScreen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.BackIcon
import org.example.project.theme.uiKit.MaxiAlertDialog
import org.example.project.theme.uiKit.MaxiAlertDialogButtons
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiSwitch
import org.example.project.utils.orEmpty
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt

class TrainingScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            TrainingViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        val navigator = RootNavigator.currentOrThrow
        LaunchedEffect(viewModel) {
            viewModel.container.sideEffectFlow.collect {
                when(it) {
                    TrainingEvent.StopTraining -> navigator.push(TrainingResultScreen())
                }
            }
        }
        LaunchedEffect(state.isStart) {
            launch() {
                while (state.isStart) {
                    delay(999L)
                    viewModel.incrementTime()
                }
            }
        }
        MaxiPageContainer(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.size(20.dp))
                    Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                        BackIcon(modifier = Modifier.size(40.dp).align(Alignment.CenterStart)) {
                            navigator.pop()
                        }

                        Text(
                            text = stringResource(Res.string.training),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 20.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                            modifier = Modifier.align(Alignment.Center)
                        )

                        Row(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(Res.string.chss),
                                style = MaxiPulsTheme.typography.regular.copy(
                                    fontSize = 16.sp,
                                    color = MaxiPulsTheme.colors.uiKit.textColor
                                ),
                                modifier = Modifier
                            )
                            Spacer(Modifier.size(20.dp))

                            MaxiSwitch(
                                checked = state.isTrimp,
                                onCheckedChange = { viewModel.changeIsTrimp() },
                                modifier = Modifier.size(width = 50.dp, height = 25.dp)
                            )

                            Spacer(Modifier.size(20.dp))

                            Text(
                                text = stringResource(Res.string.trimp),
                                style = MaxiPulsTheme.typography.regular.copy(
                                    fontSize = 16.sp,
                                    color = MaxiPulsTheme.colors.uiKit.textColor
                                ),
                                modifier = Modifier
                            )

                            Spacer(Modifier.size(40.dp))
                            Icon(
                                painterResource(Res.drawable.info_ic),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp).clickableBlank() {
                                    viewModel.changeIsAlertDialog()
                                },
                                tint = MaxiPulsTheme.colors.uiKit.primary
                            )

                            Spacer(Modifier.size(40.dp))
                            Box(
                                modifier = Modifier.size(40.dp)
                                    .background(
                                        MaxiPulsTheme.colors.uiKit.primary,
                                        shape = CircleShape
                                    )
                                    .clip(CircleShape).clickableBlank {
                                        //todo
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painterResource(Res.drawable.add_ic),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                                )
                            }

                        }
                    }
                    Spacer(Modifier.size(20.dp))
                    Text(
                        text = state.durationSeconds.formatSeconds(),
                        style = MaxiPulsTheme.typography.bold.copy(
                            fontSize = 32.sp,
                            lineHeight = 32.sp,
                            color = MaxiPulsTheme.colors.uiKit.primary
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    HorizontalDivider(
                        Modifier.fillMaxWidth().padding(top = 20.dp),
                        color = MaxiPulsTheme.colors.uiKit.divider
                    )
                }
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(
                        top = 20.dp,
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 90.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(state.sportsmans) {
                        if (state.isTrimp) {
                            TrimpSportsmanItem(
                                modifier = Modifier.fillMaxWidth().height(180.dp),
                                sportsmanUI = it
                            ) {
                                viewModel.changeSelectSportsman(it)
                            }
                        } else {
                            ChssSportsmanItem(
                                modifier = Modifier.fillMaxWidth().height(180.dp),
                                sportsmanUI = it
                            ) {
                                viewModel.changeSelectSportsman(it)
                            }
                        }
                    }
                }

            }
            MaxiButton(
                modifier = Modifier.padding(bottom = 20.dp).size(width = 200.dp, height = 50.dp)
                    .align(Alignment.BottomCenter),
                onClick = {
                    viewModel.changeIsStart()
                },
                text = if (!state.isStart) stringResource(Res.string.start) else stringResource(
                    Res.string.stop
                )
            )
        }

        if (state.isAlertDialog) {
            MaxiAlertDialog(
                modifier = Modifier.size(width = 650.dp, height = 660.dp),
                alertDialogButtons = MaxiAlertDialogButtons.Accept,
                title = stringResource(Res.string.training_info),
                descriptionContent = {
                    Column(modifier = Modifier.fillMaxSize().padding(bottom = 65.dp), verticalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = stringResource(Res.string.training_info_desc1),
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 20.sp,
                                lineHeight = 20.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                        )
                        Text(
                            text = stringResource(Res.string.training_info_desc2),
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 20.sp,
                                lineHeight = 20.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                        )
                        Text(
                            text = stringResource(Res.string.training_info_desc3),
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 20.sp,
                                lineHeight = 20.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                        )
                        Text(
                            text = stringResource(Res.string.training_info_desc4),
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 20.sp,
                                lineHeight = 20.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                        )
                    }
                },
                accept = {
                    viewModel.changeIsAlertDialog()
                },
                onDismiss = {
                    viewModel.changeIsAlertDialog()
                },
                acceptText = stringResource(Res.string.ok)
            )
        }
    }
}

@Composable
private fun ChssSportsmanItem(
    modifier: Modifier = Modifier,
    sportsmanUI: TrainingSportsmanUI,
    onClick: () -> Unit,
) {
    Box(
        modifier.background(
            color = sportsmanUI.color,
            shape = RoundedCornerShape(25.dp)
        ).clip(RoundedCornerShape(25.dp)).clickableBlank { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.size(10.dp))
            Column(
                modifier = Modifier.height(59.dp).padding(horizontal = 20.dp).fillMaxWidth()
                    .background(
                        color = MaxiPulsTheme.colors.uiKit.white,
                        shape = RoundedCornerShape(100.dp)
                    ).clip(RoundedCornerShape(100.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = sportsmanUI.heartBits.lastOrNull()?.value?.toString().orEmpty(),
                    style = MaxiPulsTheme.typography.bold.copy(
                        fontSize = 32.sp,
                        lineHeight = 32.sp,
                        color = sportsmanUI.color
                    ),
                    maxLines = 1,
                    modifier = Modifier
                )
            }
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 9.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.chss_max),
                    style = MaxiPulsTheme.typography.semiBold.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        color = MaxiPulsTheme.colors.uiKit.lightTextColor
                    ),
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "${
                        (sportsmanUI.heartRateMax.toFloat() / sportsmanUI.heartBits.maxOf { it.value }
                            .toFloat() * 100).roundToInt()
                    }%",
                    style = MaxiPulsTheme.typography.semiBold.copy(
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        color = MaxiPulsTheme.colors.uiKit.lightTextColor
                    ),
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(Modifier.weight(1f))
            Column(
                modifier = Modifier.padding(horizontal = 11.dp).height(68.dp).fillMaxWidth()
                    .background(
                        color = MaxiPulsTheme.colors.uiKit.white,
                        shape = RoundedCornerShape(25.dp)
                    ).clip(RoundedCornerShape(25.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = sportsmanUI.lastname,
                    style = MaxiPulsTheme.typography.semiBold.copy(
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                    maxLines = 1,
                    modifier = Modifier
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    text = sportsmanUI.firstname,
                    style = MaxiPulsTheme.typography.semiBold.copy(
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                    maxLines = 1,
                    modifier = Modifier
                )
            }
            Spacer(Modifier.size(10.dp))
        }
        if (!sportsmanUI.isTraining) {
            Box(
                modifier = Modifier.fillMaxSize().background(
                    color = Color.Black.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(25.dp)
                ).clip(RoundedCornerShape(25.dp))
            )
        }
    }
}

@Composable
private fun TrimpSportsmanItem(
    modifier: Modifier = Modifier,
    sportsmanUI: TrainingSportsmanUI,
    onClick: () -> Unit,
) {
    Box(
        modifier.background(
            color = sportsmanUI.color,
            shape = RoundedCornerShape(25.dp)
        ).clip(RoundedCornerShape(25.dp)).clickableBlank { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.size(10.dp))
            Column(
                modifier = Modifier.padding(horizontal = 11.dp).height(47.dp).fillMaxWidth()
                    .background(
                        color = MaxiPulsTheme.colors.uiKit.white,
                        shape = RoundedCornerShape(25.dp)
                    ).clip(RoundedCornerShape(25.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = sportsmanUI.lastname,
                    style = MaxiPulsTheme.typography.semiBold.copy(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                    maxLines = 1,
                    modifier = Modifier
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    text = sportsmanUI.firstname,
                    style = MaxiPulsTheme.typography.semiBold.copy(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                    maxLines = 1,
                    modifier = Modifier
                )
            }
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 9.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(Res.string.chss),
                    style = MaxiPulsTheme.typography.semiBold.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        color = MaxiPulsTheme.colors.uiKit.lightTextColor
                    ),
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = sportsmanUI.heartBits.lastOrNull()?.value?.toString().orEmpty(),
                    style = MaxiPulsTheme.typography.semiBold.copy(
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        color = MaxiPulsTheme.colors.uiKit.lightTextColor
                    ),
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(Modifier.weight(1f))
            Column(
                modifier = Modifier.height(69.dp).padding(horizontal = 11.dp).fillMaxWidth()
                    .background(
                        color = MaxiPulsTheme.colors.uiKit.white,
                        shape = RoundedCornerShape(25.dp)
                    ).clip(RoundedCornerShape(25.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.height(20.dp).fillMaxWidth().padding(horizontal = 10.dp)
                        .background(
                            color = MaxiPulsTheme.colors.uiKit.sportsmanAvatarBackground,
                            shape = RoundedCornerShape(10.dp)
                        ).clip(RoundedCornerShape(10.dp))
                ) {
                    Box(
                        modifier = Modifier.fillMaxHeight().fillMaxWidth(
                            sportsmanUI.heartBits.lastOrNull()?.value.orEmpty()
                                .toFloat() / sportsmanUI.heartRateMax.toFloat()
                        )
                            .background(
                                color = sportsmanUI.color,
                                shape = RoundedCornerShape(10.dp)
                            ).clip(RoundedCornerShape(10.dp))
                    )
                }
                Spacer(Modifier.size(9.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 9.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = sportsmanUI.heartRateMin.toString(),
                        style = MaxiPulsTheme.typography.bold.copy(
                            fontSize = 20.sp,
                            lineHeight = 20.sp,
                            color = sportsmanUI.color
                        ),
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = sportsmanUI.heartRateMax.toString(),
                        style = MaxiPulsTheme.typography.semiBold.copy(
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            color = sportsmanUI.color
                        ),
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Spacer(Modifier.size(10.dp))

        }
        if (!sportsmanUI.isTraining) {
            Box(
                modifier = Modifier.fillMaxSize().background(
                    color = Color.Black.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(25.dp)
                ).clip(RoundedCornerShape(25.dp))
            )
        }
    }
}