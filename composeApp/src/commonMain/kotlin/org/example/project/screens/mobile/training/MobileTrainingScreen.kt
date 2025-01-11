package org.example.project.screens.mobile.training

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.asFloatState
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.assigned_training
import maxipuls.composeapp.generated.resources.choose_sensor
import maxipuls.composeapp.generated.resources.next
import maxipuls.composeapp.generated.resources.put_on_the_sensor
import maxipuls.composeapp.generated.resources.shuttle_running
import maxipuls.composeapp.generated.resources.start
import maxipuls.composeapp.generated.resources.what_is_assigned_training
import org.example.project.domain.model.SelectSensorAlertDialogStep
import org.example.project.domain.model.SelectSensorAlertDialogStep.*
import org.example.project.domain.model.widget.WidgetSize
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.mobile.borgScale.MobileBackIcon
import org.example.project.screens.tablet.widget.PurpleGradientBackground
import org.example.project.screens.tablet.widget.UglyGradientBackground
import org.example.project.screens.tablet.widget.WidgetItem
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.BackIcon
import org.example.project.theme.uiKit.ButtonTextStyle
import org.example.project.theme.uiKit.MaxiAlertDialog
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiPageContainerMobile
import org.example.project.theme.uiKit.MaxiRoundCheckBox
import org.example.project.theme.uiKit.TopBarMobile
import org.example.project.utils.safeAreaHorizontal
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import io.ktor.http.parametersOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import maxipuls.composeapp.generated.resources.device_fully_and_turn_on
import maxipuls.composeapp.generated.resources.end
import maxipuls.composeapp.generated.resources.maxipulse_stroke
import maxipuls.composeapp.generated.resources.searching_sensor
import org.example.project.domain.model.sportsman.SensorStatus
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.ext.clickableBlank
import org.example.project.ext.granted
import org.example.project.platform.ScanBluetoothSensorsManager
import org.example.project.platform.permission.delegate.PermissionDelegate
import org.example.project.platform.permission.model.Permission
import org.example.project.platform.permission.service.PermissionsService
import org.example.project.screens.mobile.training.result.MobileTrainingResultScreen
import org.example.project.theme.uiKit.MaxiRadioButton
import org.example.project.utils.orEmpty
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MobileTrainingScreen : Screen, KoinComponent {
    private val scanBluetoothSensorsManager: ScanBluetoothSensorsManager by inject()

    @Composable
    override fun Content() {
//        LifecycleEventEffect(event = Lifecycle.Event.ON_DESTROY) {
//            scanBluetoothSensorsManager.stopScan()
//        }
        val viewModel = rememberScreenModel {
            MobileTrainingViewModel()
        }
        val navigator = RootNavigator.currentOrThrow
        val state by viewModel.stateFlow.collectAsState()
        var startObserve by remember { mutableStateOf(false) }
        LaunchedEffect(state.isStart) {
            while (state.isStart) {
                delay(995L)
                viewModel.incrementTime()
            }
        }
        LaunchedEffect(state.currentTraining.sensorUI) {
            if (state.currentTraining.sensorUI != SensorUI.Empty && !startObserve) {
                startObserve = true
                println("ТЕПЕРЬ БУДЕТ МЯСО")
                launch() {
                    scanBluetoothSensorsManager.scanBluetoothSensors {
                        println("SCANDEVICE новый инстанс - $it")
                        println("id текущего инстанса - ${it.sensorId}")
                        if (it.sensorId == state.currentTraining.sensorUI.sensorId) {
                            viewModel.changeSelectSensor(it)
                        }
                    }
                }
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                scanBluetoothSensorsManager.stopScan() {}
                println("ЕБУЧИЙ СТОП")
            }
        }

        MaxiPageContainerMobile(modifier = Modifier.fillMaxSize(), topBar = {
            TopBarMobile(
                modifier = Modifier.fillMaxWidth().padding(horizontal = safeAreaHorizontal()),
                title = stringResource(Res.string.assigned_training),
                leadingIcon = {
                    MobileBackIcon(modifier = Modifier.size(40.dp)) {
                        navigator.pop()
                    }
                }
            )
        }) {
            if (state.currentTraining.sensorUI != SensorUI.Empty) {
                state.currentTraining.sensorUI.let {
                    Text(
                        text = it.heartRate.lastOrNull().orEmpty().toString(),
                        style = MaxiPulsTheme.typography.bold.copy(
                            fontSize = 128.sp,
                            lineHeight = 128.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                Text(
                    text = stringResource(Res.string.put_on_the_sensor),
                    style = MaxiPulsTheme.typography.bold.copy(
                        fontSize = 40.sp,
                        lineHeight = 50.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColorWithAlpha,
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = safeAreaHorizontal())
                        .align(Alignment.Center)
                )
            }
            WhatIsTrainingCard(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp).height(110.dp)
                    .padding(horizontal = safeAreaHorizontal()).align(Alignment.TopCenter),
            )

            MaxiButton(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = safeAreaHorizontal(), vertical = 20.dp).height(40.dp)
                    .align(Alignment.BottomCenter),
                onClick = {
                    if (state.currentTraining.sensorUI == SensorUI.Empty) {
                        viewModel.changeConnectSensorDialog()
                    } else {
                        if (state.isStart) {
                            scanBluetoothSensorsManager.stopScan() {}
                            state.currentTraining.let {
                                navigator.replace(MobileTrainingResultScreen(it))
                            }
                        } else {
                            viewModel.changeIsStart()
                        }
                    }
                },
                text = stringResource(
                    if (state.isStart) Res.string.end else Res.string.start
                )
            )
            if (state.connectSensorDialog) {
                SelectSensorAlertDialog(
                    onDismiss = {
                        viewModel.changeConnectSensorDialog()
                    },
                    success = {
                        viewModel.changeSelectSensor(it)
                        viewModel.changeConnectSensorDialog()
                    },
                )
            }
        }
    }

    @Composable
    fun KoinComponent.SelectSensorAlertDialog(
        onDismiss: () -> Unit,
        success: (SensorUI) -> Unit
    ) {
        val devices = remember { mutableStateListOf<SensorUI>() }
        var sensorShow by remember { mutableStateOf(false) }
        var sensorPermission by remember { mutableStateOf(false) }
        val permissionService: PermissionsService by inject()
        permissionService.checkPermissionFlow(Permission.BLUETOOTH_CONNECT)
            .collectAsState(permissionService.checkPermission(Permission.BLUETOOTH_CONNECT))
            .granted {
                if (sensorPermission) {
                    sensorShow = true
                }
            }
        var state by remember { mutableStateOf(SelectSensorAlertDialogStep.SelectTypeSensor) }
        LaunchedEffect(Unit) {
            if (permissionService.checkPermission(Permission.BLUETOOTH_CONNECT)
                    .granted()
            ) {
                sensorShow = true
            } else {
                sensorPermission = true
                permissionService.providePermission(Permission.BLUETOOTH_CONNECT)
            }
        }

        LaunchedEffect(Unit) {
            if (sensorShow) {
                scanBluetoothSensorsManager.scanBluetoothSensors {
                    println("device - $it")
                    if (it.sensorId !in devices.map { it.sensorId }) {
                        devices.add(it)
                    }
                }
            }
        }
        if (sensorShow) {
            when (state) {
                SelectTypeSensor -> {
                    SelectTypeSensor(onDismiss, onButtonClick = {
                        state = SearchSensor
                    })
                }

                SearchSensor -> {
                    SearchSensor(devices = devices, onDismiss = onDismiss, onSuccess = {
                        state = SelectSensor
                    })
                }

                SelectSensor -> {
                    SelectSensor(onDismiss, onSuccess = {
                        success(it)
                    }, sensors = devices)
                }
            }
        }
    }

    @Composable
    fun SelectSensor(
        onDismiss: () -> Unit,
        onSuccess: (SensorUI) -> Unit,
        sensors: List<SensorUI>
    ) {
        var selectSensor: SensorUI? by remember { mutableStateOf(null) }
        MaxiAlertDialog(
            modifier = Modifier.fillMaxWidth().height(404.dp)
                .padding(horizontal = safeAreaHorizontal()),
            paddingValues = PaddingValues(20.dp),
            shape = 15.dp,
            onDismiss = {
                onDismiss()
            },
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.searching_sensor),
                    style = MaxiPulsTheme.typography.medium.copy(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor,
                    ),
                )
                Spacer(Modifier.size(10.dp))
                Text(
                    text = stringResource(Res.string.device_fully_and_turn_on),
                    style = MaxiPulsTheme.typography.regular.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor,
                    ),
                )
                Spacer(Modifier.size(20.dp))
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(sensors) {
                        Row(
                            modifier = Modifier.fillMaxWidth().clickableBlank {
                                selectSensor = if (selectSensor == it) {
                                    null
                                } else {
                                    it
                                }
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MaxiRadioButton(
                                modifier = Modifier.size(24.dp),
                                selected = selectSensor == it,
                                onClick = {
                                    selectSensor = if (selectSensor == it) {
                                        null
                                    } else {
                                        it
                                    }
                                })
                            Spacer(Modifier.size(20.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = it.deviceName,
                                    style = MaxiPulsTheme.typography.medium.copy(
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp,
                                        color = MaxiPulsTheme.colors.uiKit.textColor,
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(Modifier.size(5.dp))
                                Text(
                                    text = it.sensorId,
                                    style = MaxiPulsTheme.typography.regular.copy(
                                        fontSize = 12.sp,
                                        lineHeight = 12.sp,
                                        color = MaxiPulsTheme.colors.uiKit.textColor,
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
                Spacer(Modifier.size(20.dp))
                MaxiButton(
                    enabled = selectSensor != null,
                    modifier = Modifier.height(40.dp).fillMaxWidth(),
                    onClick = {
                        selectSensor?.let {
                            onSuccess(it)
                        }
                    },
                    text = stringResource(Res.string.next),
                    buttonTextStyle = ButtonTextStyle.MobileBold
                )
            }
        }
    }

    @Composable
    fun SearchSensor(devices: List<SensorUI>, onDismiss: () -> Unit, onSuccess: () -> Unit) {
        var animationTarget by remember { mutableStateOf(0f) }

        val searchProgress by animateFloatAsState(
            targetValue = animationTarget,
            animationSpec = tween(durationMillis = 3000) // Длительность анимации
        )
        LaunchedEffect(Unit) {
            animationTarget = 0.7f
        }
        LaunchedEffect(devices) {
            if (devices.isNotEmpty()) {
                animationTarget = 1f
                delay(1000L)
                onSuccess()
            }
        }
        MaxiAlertDialog(
            modifier = Modifier.fillMaxWidth().height(404.dp)
                .padding(horizontal = safeAreaHorizontal()),
            paddingValues = PaddingValues(20.dp),
            shape = 15.dp,
            onDismiss = {
                onDismiss()
            },
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.searching_sensor),
                    style = MaxiPulsTheme.typography.medium.copy(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor,
                    ),
                )
                Spacer(Modifier.size(10.dp))
                Text(
                    text = stringResource(Res.string.device_fully_and_turn_on),
                    style = MaxiPulsTheme.typography.regular.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor,
                    ),
                )
                Spacer(Modifier.weight(1f))
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 7.dp,
                        hoveredElevation = 2.dp
                    ),
                    colors = CardDefaults.cardColors(containerColor = MaxiPulsTheme.colors.uiKit.white),
                    modifier = Modifier.size(200.dp),
                    shape = CircleShape
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(Res.drawable.maxipulse_stroke),
                            modifier = Modifier.size(120.dp),
                            contentDescription = null,
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                Box(
                    modifier = Modifier.fillMaxWidth().height(40.dp).clip(RoundedCornerShape(50.dp))
                ) {
                    Box(
                        modifier = Modifier.height(40.dp).fillMaxWidth()
                            .background(color = MaxiPulsTheme.colors.uiKit.primary.copy(alpha = 0.5f))
                    )
                    Box(
                        modifier = Modifier.height(40.dp).fillMaxWidth(searchProgress)
                            .background(color = MaxiPulsTheme.colors.uiKit.primary)
                    )
                }
            }
        }
    }

    @Composable
    fun SelectTypeSensor(onDismiss: () -> Unit, onButtonClick: () -> Unit) {
        MaxiAlertDialog(
            modifier = Modifier.fillMaxWidth().height(404.dp)
                .padding(horizontal = safeAreaHorizontal()),
            paddingValues = PaddingValues(20.dp),
            shape = 15.dp,
            onDismiss = {
                onDismiss()
            },
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.choose_sensor),
                    style = MaxiPulsTheme.typography.medium.copy(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor,
                    ),
                )
                Spacer(Modifier.weight(1f))
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 7.dp,
                        hoveredElevation = 2.dp
                    ),
                    colors = CardDefaults.cardColors(containerColor = MaxiPulsTheme.colors.uiKit.white),
                    modifier = Modifier.size(200.dp),
                    shape = CircleShape
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Colibri", style = MaxiPulsTheme.typography.bold.copy(
                                fontSize = 32.sp,
                                lineHeight = 32.sp,
                                color = MaxiPulsTheme.colors.uiKit.primary
                            )
                        )
                    }
                }

                Spacer(Modifier.size(10.dp))
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val select = 1
                    for (i in listOf<Int>(1, 2, 3)) {
                        Box(
                            modifier = Modifier.size(10.dp).clip(CircleShape).background(
                                shape = CircleShape,
                                color = if (select == i) MaxiPulsTheme.colors.uiKit.black50 else MaxiPulsTheme.colors.uiKit.black25
                            )
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                MaxiButton(
                    modifier = Modifier.height(40.dp).fillMaxWidth(),
                    onClick = {
                        onButtonClick()
                    },
                    text = stringResource(Res.string.next),
                    buttonTextStyle = ButtonTextStyle.MobileBold
                )
            }

        }
    }

    @Composable
    fun WhatIsTrainingCard(modifier: Modifier) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 7.dp, hoveredElevation = 2.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                PurpleGradientBackground(modifier = Modifier.fillMaxSize())
                Column(
                    modifier = Modifier.fillMaxHeight().padding(vertical = 20.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val sizeText = 14.sp
                    Image(
                        painter = painterResource(Res.drawable.shuttle_running),
                        modifier = Modifier.size(40.dp),
                        contentScale = ContentScale.FillHeight,
                        contentDescription = null
                    )
                    Spacer(Modifier.size(10.dp))
                    Text(
                        text = stringResource(Res.string.what_is_assigned_training),
                        style = MaxiPulsTheme.typography.bold.copy(
                            fontSize = sizeText,
                            lineHeight = sizeText,
                            color = MaxiPulsTheme.colors.uiKit.lightTextColor,
                            textAlign = TextAlign.Center,
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )


                }
            }
        }
    }
}
