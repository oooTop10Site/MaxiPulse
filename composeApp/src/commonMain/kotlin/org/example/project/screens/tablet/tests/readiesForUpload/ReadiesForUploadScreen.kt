package org.example.project.screens.tablet.tests.readiesForUpload

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import maxipuls.composeapp.generated.resources.Res
import org.example.project.ext.formatSeconds
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiPageContainer
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import maxipuls.composeapp.generated.resources.`continue`
import maxipuls.composeapp.generated.resources.readies_for_upload
import maxipuls.composeapp.generated.resources.start
import maxipuls.composeapp.generated.resources.stop
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.ext.toText
import org.example.project.screens.tablet.tests.readiesForUpload.result.ReadiesForUploadResultScreen
import org.example.project.screens.tablet.tests.shuttleRun.SportsmanTestItem
import org.example.project.theme.uiKit.MaxiButton

class ReadiesForUploadScreen(private val sportsmans: List<SportsmanSensorUI>) : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel() {
            ReadiesForUploadViewModel()
        }
        val navigator = LocalNavigator.currentOrThrow
        val state by viewModel.stateFlow.collectAsState()

        viewModel.scanBluetoothSensorsManager.scanSensors {
            viewModel.newDataFromSportsman(sensorUI = it)
        }

        LaunchedEffect(state.isStart) {
            launch() {
                while (state.isStart) {
                    delay(995L)
                    viewModel.decrementTime()
                }
            }
        }

        LaunchedEffect(viewModel) {
            viewModel.loadSportmans(sportsmans)
            launch() {
                viewModel.container.sideEffectFlow.collect {
                    when (it) {
                        is ReadiesForUploadEvent.Result -> {
                            navigator.replace(ReadiesForUploadResultScreen(it.sportmans))
                        }
                    }
                }
            }
        }

        MaxiPageContainer(
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(20.dp))
                    Text(
                        text = stringResource(Res.string.readies_for_upload),
                        style = MaxiPulsTheme.typography.bold.copy(
                            fontSize = 20.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        ),
                    )
                    Spacer(Modifier.size(20.dp))
                    Text(
                        text = state.currentTest.toText(),
                        style = MaxiPulsTheme.typography.bold.copy(
                            fontSize = 16.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        ),
                    )
                    Spacer(Modifier.size(20.dp))
                    Row(
                        Modifier,
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = state.time.formatSeconds(),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 40.sp,
                                lineHeight = 40.sp,
                                color = MaxiPulsTheme.colors.uiKit.primary
                            ),
                        )
                    }
                    Spacer(Modifier.size(20.dp))
                }
            }
        ) {
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
                    text = if (!state.isStart) {
                        if (state.time == ReadiesForUploadState.InitState.time) stringResource(Res.string.start) else stringResource(
                            Res.string.`continue`
                        )
                    } else stringResource(
                        Res.string.stop
                    )
                )

                Spacer(Modifier.size(20.dp))
            }
        }
    }
}