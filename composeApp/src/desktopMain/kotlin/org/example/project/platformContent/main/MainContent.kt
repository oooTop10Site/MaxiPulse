package org.example.project.platformContent.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.attention
import maxipuls.composeapp.generated.resources.attention_user_with_not_active_sensor_desc
import maxipuls.composeapp.generated.resources.back
import maxipuls.composeapp.generated.resources.background_auth
import maxipuls.composeapp.generated.resources.cancel
import maxipuls.composeapp.generated.resources.choose_sensor
import maxipuls.composeapp.generated.resources.connect
import maxipuls.composeapp.generated.resources.connecting
import maxipuls.composeapp.generated.resources.error_ic
import maxipuls.composeapp.generated.resources.logo
import maxipuls.composeapp.generated.resources.marker_question
import maxipuls.composeapp.generated.resources.ok
import maxipuls.composeapp.generated.resources.on_active_sensors
import maxipuls.composeapp.generated.resources.search
import maxipuls.composeapp.generated.resources.sensor_already_assigned
import maxipuls.composeapp.generated.resources.sensor_already_assigned_desc
import maxipuls.composeapp.generated.resources.settings_ic
import maxipuls.composeapp.generated.resources.start_tarining
import maxipuls.composeapp.generated.resources.start_test
import maxipuls.composeapp.generated.resources.what_do_if_sensor_not_active
import maxipuls.composeapp.generated.resources.what_do_if_sensor_not_active_desc
import org.example.project.domain.model.ButtonActions
import org.example.project.domain.model.MainAlertDialog
import org.example.project.domain.model.test.TestUI
import org.example.project.domain.model.training.TrainingStageChssUI
import org.example.project.ext.clickableBlank
import org.example.project.ext.granted
import org.example.project.platform.ScanBluetoothSensorsManager
import org.example.project.platform.permission.model.Permission
import org.example.project.platform.permission.service.PermissionsService
import org.example.project.screens.adaptive.main.MainEvent
import org.example.project.screens.adaptive.main.MainState
import org.example.project.screens.adaptive.main.MainViewModel
import org.example.project.screens.adaptive.main.components.SportsmanSensorCard
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.screens.tablet.tests.readiesForUpload.ReadiesForUploadScreen
import org.example.project.screens.tablet.tests.shuttleRun.ShuttleRunScreen
import org.example.project.screens.tablet.training.TrainingScreen
import org.example.project.screens.tablet.widget.WidgetScreen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiAlertDialog
import org.example.project.theme.uiKit.MaxiAlertDialogButtons
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.MaxiSwitch
import org.example.project.theme.uiKit.SelectSensor
import org.example.project.utils.Constants
import org.example.project.utils.debouncedClick
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

@Composable
internal actual fun KoinComponent.MainContent(
    viewModel: MainViewModel,
    state: MainState,
    testUI: TestUI?,
    stages: List<TrainingStageChssUI>,
) {
    val scanBluetoothSensorsManager: ScanBluetoothSensorsManager by inject()
    if (state.alertDialog is MainAlertDialog.SelectSensor) {
        scanBluetoothSensorsManager.scanSensors() {
            println("device - $it")
            viewModel.addSensor(it)
        }
    }
    MaxiPageContainer() {
        val rootNavigator = RootNavigator.currentOrThrow
        val navigator = LocalNavigator.currentOrThrow
        val screenSize = ScreenSize.currentOrThrow
        val chunkSize = when (screenSize.widthSizeClass) {
            WindowWidthSizeClass.Medium -> 2
            WindowWidthSizeClass.Expanded -> 3
            WindowWidthSizeClass.Compact -> 1
            else -> 1
        }
        val buttonDivision = when (screenSize.heightSizeClass) {
            WindowHeightSizeClass.Medium -> 1.4
            WindowHeightSizeClass.Expanded -> 1.0
            WindowHeightSizeClass.Compact -> 1.7
            else -> 1.5
        }

        LaunchedEffect(viewModel) {
            viewModel.loadSportsman()
            launch {
                viewModel.container.sideEffectFlow.collect {
                    when (it) {
                        is MainEvent.ShuttleRun -> rootNavigator.push(ShuttleRunScreen(it.sportsmans))
                        is MainEvent.ReadiesForUpload -> rootNavigator.push(ReadiesForUploadScreen(it.sportsmans))
                        is MainEvent.Training -> {
                            scanBluetoothSensorsManager.stopScan { }
                            rootNavigator.push(TrainingScreen(it.sportsmans, stages))
                        }
                    }
                }
            }
        }

        MaxiPageContainer(
            floatingActionButton = {
                if (state.isStartTraining) {
                    Box(
                        modifier = Modifier.size(40.dp).clip(CircleShape).background(
                            shape = CircleShape,
                            color = MaxiPulsTheme.colors.uiKit.primary
                        ).clickableBlank {
                            viewModel.changeAlertDialog(MainAlertDialog.Question)
                        }, contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.marker_question),
                            contentDescription = null,
                            tint = MaxiPulsTheme.colors.uiKit.white,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            bottomBar = {
                MaxiButton(
                    onClick = debouncedClick() {
                        if (!state.isStartTraining) {
                            viewModel.changeIsStartTraining()
                        } else {
                            viewModel.startTraining(testUI)
                        }
                    }, shape = RoundedCornerShape(0.dp),
                    text = stringResource(
                        if (testUI == null) Res.string.start_tarining else Res.string.start_test
                    ), modifier = Modifier.fillMaxWidth().height((88 / buttonDivision).dp),
                    enabled = (state.alertDialog == null && state.selectSportsmans.isNotEmpty()) || !state.isStartTraining
                )
            },
            topBar = {
                AnimatedVisibility(
                    !state.isStartTraining,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut() + slideOutHorizontally()
                ) {
                    Box(Modifier.fillMaxWidth().padding(vertical = 20.dp, horizontal = 20.dp)) {
                        Box(
                            modifier = Modifier.size(40.dp)
                                .background(
                                    MaxiPulsTheme.colors.uiKit.primary,
                                    shape = CircleShape
                                )
                                .clip(CircleShape).align(Alignment.CenterEnd),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painterResource(Res.drawable.settings_ic),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                                    .clickableBlank(onClick = debouncedClick() {
                                        navigator.push(WidgetScreen())
                                    }
                                    ),
                                tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                            )
                        }
                    }
                }
                AnimatedVisibility(
                    state.isStartTraining,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut() + slideOutHorizontally()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.size(20.dp))
                        Text(
                            text = stringResource(Res.string.connecting),
                            style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 20.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            )
                        )
                        Text(
                            text = stringResource(
                                Res.string.connect,
                                state.sportsmans.count { it.sensor != null }),
                            style = MaxiPulsTheme.typography.regular.copy(
                                fontSize = 16.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, top = 4.dp)
                        )
                        Spacer(
                            Modifier.size(12.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(
                                text = stringResource(Res.string.on_active_sensors),
                                style = MaxiPulsTheme.typography.regular.copy(
                                    fontSize = 16.sp,
                                    color = MaxiPulsTheme.colors.uiKit.textColor
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            MaxiSwitch(
                                checked = state.isActiveSensor,
                                onCheckedChange = {
                                    viewModel.changeIsActiveSensor()
                                }
                            )
                            MaxiOutlinedTextField(
                                value = state.search,
                                onValueChange = {
                                    viewModel.changeSearch(it)
                                    viewModel.search(it)
                                },
                                placeholder = stringResource(Res.string.search),
                                modifier = Modifier.height(Constants.TextFieldHeight)
                                    .weight(1f),
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(Res.drawable.search),
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp),
                                        tint = MaxiPulsTheme.colors.uiKit.textColor
                                    )
                                }
                            )
                        }
                        Spacer(
                            Modifier.size(20.dp)
                        )
                    }
                }
            }
        ) {
            AnimatedVisibility(
                state.isStartTraining,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(20.dp)
                ) {
                    items(state.filterSportsmans.chunked(chunkSize)) { chunk ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(25.dp)
                        ) {
                            chunk.forEach {
                                val isSelect = it.id in state.selectSportsmans
                                SportsmanSensorCard(
                                    modifier = Modifier.weight(1f),
                                    name = it.name,
                                    number = it.number,
                                    middleName = it.middleName,
                                    lastname = it.lastname,
                                    compositionText = "Состав - ${it.compositionNumber}",
                                    sensorId = it.sensor?.sensorId.orEmpty(),
                                    sensorName = it.sensor?.deviceName.orEmpty(),
                                    isSelect = isSelect,
                                    isBorder = state.isActiveSensor && isSelect,
                                    clickSensor = {
                                        viewModel.changeAlertDialog(
                                            MainAlertDialog.SelectSensor(
                                                sportsman = it
                                            )
                                        )
                                    }
                                ) {
                                    viewModel.changeSelect(it)
                                }
                            }
                            if (chunk.size != chunkSize) {
                                for (i in 1..chunkSize - chunk.size) {
                                    Box(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(
                !state.isStartTraining,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        bitmap = imageResource(resource = Res.drawable.background_auth),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                    )
                    Image(
                        bitmap = imageResource(resource = Res.drawable.logo),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.align(Alignment.Center).padding(horizontal = 16.dp)
                    )
                }
            }
        }

        when (val alertData = state.alertDialog) {
            is MainAlertDialog.Question -> {
                MaxiAlertDialog(
                    modifier = Modifier.padding(horizontal = 20.dp).width(600.dp),
                    descriptionContent = {
                        Text(
                            text = stringResource(Res.string.what_do_if_sensor_not_active_desc),
                            style = MaxiPulsTheme.typography.regular.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 20.sp,
                                lineHeight = 20.sp
                            ),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(
                                bottom = 40.dp,
                                start = 25.dp,
                                end = 25.dp
                            )
                        )
                    },
                    title = stringResource(Res.string.what_do_if_sensor_not_active),
                    accept = {
                        viewModel.changeAlertDialog(null)
                    },
                    acceptText = stringResource(Res.string.ok),
                    alertDialogButtons = MaxiAlertDialogButtons.Accept,
                    onDismiss = {
                        viewModel.changeAlertDialog(null)
                    }
                )
            }

            is MainAlertDialog.PlayerWithNoActiveSensor -> {
                MaxiAlertDialog(
                    modifier = Modifier.padding(horizontal = 20.dp).width(600.dp),
                    title = stringResource(Res.string.attention),
                    accept = debouncedClick() {
                        viewModel.startTraining(testUI, false)
                    },
                    acceptText = stringResource(Res.string.ok),
                    cancelText = stringResource(Res.string.cancel),
                    alertDialogButtons = MaxiAlertDialogButtons.CancelAccept,
                    onDismiss = {
                        viewModel.changeAlertDialog(null)
                    },
                    description = stringResource(Res.string.attention_user_with_not_active_sensor_desc),
                )
            }

            is MainAlertDialog.SelectSensor -> {
                SelectSensor(
                    onDismiss = {
                        viewModel.changeAlertDialog(null)
                    },
                    observeSensor = {
                        viewModel.addSensor(it)
                    },
                    sportsmanId = alertData.sportsman.id,
                    sensor = alertData.sportsman.sensor,
                    sensorAlreadyExit = {
                        it in state.sportsmans.filter { it != alertData.sportsman }
                            .map { it.sensor }
                    },
                    accept = { sensor, sportsmanId ->
                        viewModel.changeSensorValidation(
                            sensor,
                            sportsmanId
                        )
                    }
                )
            }

            is MainAlertDialog.SensorAlreadyAssigned -> {
                MaxiAlertDialog(
                    modifier = Modifier.padding(horizontal = 20.dp).width(600.dp),
                    title = stringResource(Res.string.sensor_already_assigned),
                    accept = {
                        viewModel.changeSensor(
                            alertData.sensorUI,
                            alertData.sportsmanId
                        )
                        viewModel.changeAlertDialog(null)
                    },
                    acceptText = stringResource(Res.string.ok),
                    cancelText = stringResource(Res.string.back),
                    alertDialogButtons = MaxiAlertDialogButtons.CancelAccept,
                    onDismiss = {
                        viewModel.changeAlertDialog(null)
                    },
                    description = stringResource(Res.string.sensor_already_assigned_desc),
                )
            }

            null -> {}
        }
    }

}