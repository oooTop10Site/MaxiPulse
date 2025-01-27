package org.example.project.screens.tablet.tests.shuttleRun

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.arrow_right
import maxipuls.composeapp.generated.resources.attension
import maxipuls.composeapp.generated.resources.cone_ic
import maxipuls.composeapp.generated.resources.shuttle_run
import maxipuls.composeapp.generated.resources.start
import maxipuls.composeapp.generated.resources.stop
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.test.TestSportsmanUI
import org.example.project.domain.model.test.TestStatus
import org.example.project.ext.clickableBlank
import org.example.project.ext.formatSeconds
import org.example.project.screens.tablet.tests.shuttleRun.result.ShuttleRunResultScreen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiPageContainer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class ShuttleRunScreen(private val sportsmans: List<SportsmanSensorUI>) : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            ShuttleRunViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        viewModel.scanBluetoothSensorsManager.scanSensors() {
            viewModel.newDataFromSportsman(it)
        }
        LaunchedEffect(state.isStart) {
            launch() {
                while (state.isStart) {
                    delay(995L)
                    viewModel.incrementTime()
                }
            }
        }

        LaunchedEffect(viewModel) {
            viewModel.loadSportsman(sportsmans = sportsmans)
            launch {
                viewModel.container.sideEffectFlow.collect {
                    when (it) {
                        is ShuttleRunEvent.StopShuttleRun -> {
                            navigator.replace(ShuttleRunResultScreen(it.sportsman))
                        }
                    }
                }
            }
        }

        MaxiPageContainer(topBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.size(20.dp))
                Text(
                    text = stringResource(Res.string.shuttle_run),
                    style = MaxiPulsTheme.typography.bold.copy(
                        fontSize = 20.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                )
                Spacer(Modifier.size(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(Modifier.weight(1f))
                    Row(
                        Modifier,
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.cone_ic),
                            contentDescription = null,
                            tint = if (!state.isLeft) MaxiPulsTheme.colors.uiKit.placeholder else MaxiPulsTheme.colors.uiKit.green500
                        )
                        Spacer(Modifier.size(77.dp))
                        if (state.isLeft) {
                            Icon(
                                painter = painterResource(Res.drawable.arrow_right),
                                contentDescription = null,
                                modifier = Modifier.rotate(180f).size(52.dp),
                                tint = MaxiPulsTheme.colors.uiKit.green500
                            )
                        } else {
                            Box(Modifier.size(52.dp))
                        }
                        Spacer(Modifier.size(77.dp))

                        Text(
                            text = state.time.formatSeconds(),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 40.sp,
                                lineHeight = 40.sp,
                                color = MaxiPulsTheme.colors.uiKit.primary
                            ),
                        )
                        Spacer(Modifier.size(77.dp))
                        if (!state.isLeft) {
                            Icon(
                                painter = painterResource(Res.drawable.arrow_right),
                                contentDescription = null,
                                modifier = Modifier.size(52.dp),
                                tint = MaxiPulsTheme.colors.uiKit.secondary
                            )
                        } else {
                            Box(Modifier.size(52.dp))
                        }
                        Spacer(Modifier.size(77.dp))
                        Icon(
                            painter = painterResource(Res.drawable.cone_ic),
                            contentDescription = null,
                            tint = if (state.isLeft) MaxiPulsTheme.colors.uiKit.placeholder else MaxiPulsTheme.colors.uiKit.secondary
                        )


                    }
                    Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.size(20.dp))
            }
        }, modifier = Modifier) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyVerticalGrid(
                    modifier = Modifier.weight(1f),
                    columns = GridCells.Adaptive(150.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(state.users) {
                        SportsmanTestItem(modifier = Modifier.height(200.dp), it) {
                            //todo
                        }
                    }
                }

                MaxiButton(
                    onClick = {
                        if (state.isStart) {
                            viewModel.stop()
                        } else {
                            viewModel.start()
                        }
                    },
                    text = if (!state.isStart) stringResource(Res.string.start) else stringResource(
                        Res.string.stop
                    )
                )

                Spacer(Modifier.size(20.dp))
            }
        }
    }
}

@Composable
fun SportsmanTestItem(
    modifier: Modifier = Modifier,
    sportsmanUI: TestSportsmanUI,
    onClick: () -> Unit
) {
    var sensorAvailable: Boolean? by remember { mutableStateOf(null) }
    var sportsmanMutable = remember { mutableStateOf(sportsmanUI.sportsmanSensorUI) }
    LaunchedEffect(sportsmanUI) {
        sportsmanMutable.value = sportsmanUI.sportsmanSensorUI
    }

    LaunchedEffect(sportsmanMutable) {
        if (sensorAvailable == null) {
            launch {
                SportsmanSensorUI.available(sportsmanMutable).collect {
                    println("fromCHSS - $it")

                    sensorAvailable = it
                }
            }
        }
    }
    Column(
        modifier = modifier.background(
            color = sportsmanUI.status.color,
            shape = RoundedCornerShape(25.dp)
        ).clip(RoundedCornerShape(25.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "${sportsmanUI.lastname}\n${sportsmanUI.firstname}",
            style = MaxiPulsTheme.typography.semiBold.copy(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                color = MaxiPulsTheme.colors.uiKit.lightTextColor
            ),
            maxLines = 2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 7.dp, start = 3.dp, end = 3.dp)
        )
        Spacer(Modifier.weight(1f))
        Box(modifier = Modifier.width(94.dp)) {
            Column(
                modifier = Modifier.align(Alignment.Center).fillMaxWidth()
                    .background(
                        color = MaxiPulsTheme.colors.uiKit.white.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(25.dp)
                    ).clip(RoundedCornerShape(25.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = sportsmanUI.chss.toString(),
                    style = MaxiPulsTheme.typography.semiBold.copy(
                        fontSize = 32.sp,
                        lineHeight = 32.sp,
                        color = MaxiPulsTheme.colors.uiKit.lightTextColor
                    ),
                    maxLines = 1,
                    modifier = Modifier
                )

                when (val status = sportsmanUI.status) {
                    is TestStatus.Chill -> {
                        Spacer(Modifier.size(10.dp))
                        Text(
                            text = status.timer.formatSeconds(),
                            style = MaxiPulsTheme.typography.semiBold.copy(
                                color = MaxiPulsTheme.colors.uiKit.lightTextColor,
                                fontSize = 20.sp,
                                lineHeight = 20.sp
                            ),
                            modifier = Modifier,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.size(5.dp))
                    }

                    else -> {}
                }
            }

            if (!(sensorAvailable ?: sportsmanUI.sportsmanSensorUI.available)) {
                Icon(
                    painter = painterResource(Res.drawable.attension),
                    tint = MaxiPulsTheme.colors.uiKit.primary,
                    modifier = Modifier.align(Alignment.Center).padding(5.dp).size(24.dp),
                    contentDescription = null,
                )
            }
        }
        Spacer(Modifier.weight(1f))

        when (val status = sportsmanUI.status) {
            is TestStatus.Chill, TestStatus.TestEnd, TestStatus.StandingPosition, TestStatus.LyingPosition -> {
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(25.dp)).fillMaxWidth()
                        .padding(start = 7.dp, end = 7.dp, bottom = 8.dp).height(40.dp)
                        .clickableBlank() {
                            onClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(status.action),
                        style = MaxiPulsTheme.typography.semiBold.copy(
                            color = MaxiPulsTheme.colors.uiKit.lightTextColor,
                            fontSize = 16.sp,
                            lineHeight = 16.sp
                        ),
                        modifier = Modifier,
                        textAlign = TextAlign.Center
                    )
                }
            }

            TestStatus.Running -> {
                Box(
                    modifier = Modifier.padding(start = 7.dp, end = 7.dp, bottom = 8.dp).background(
                        color = MaxiPulsTheme.colors.uiKit.white,
                        shape = RoundedCornerShape(25.dp)
                    ).clip(RoundedCornerShape(25.dp)).fillMaxWidth()
                        .height(40.dp).clickableBlank() {
                            onClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(status.action),
                        style = MaxiPulsTheme.typography.semiBold.copy(
                            color = status.color,
                            fontSize = 16.sp,
                            lineHeight = 16.sp
                        ),
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}