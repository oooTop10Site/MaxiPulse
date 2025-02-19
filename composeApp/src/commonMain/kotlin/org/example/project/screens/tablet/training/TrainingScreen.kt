package org.example.project.screens.tablet.training

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.theme.uiKit.MaxiPageContainer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.add_ic
import maxipuls.composeapp.generated.resources.age
import maxipuls.composeapp.generated.resources.age_text
import maxipuls.composeapp.generated.resources.attension
import maxipuls.composeapp.generated.resources.chss
import maxipuls.composeapp.generated.resources.chss_max
import maxipuls.composeapp.generated.resources.drop_ic
import maxipuls.composeapp.generated.resources.ending_training
import maxipuls.composeapp.generated.resources.info_ic
import maxipuls.composeapp.generated.resources.mic
import maxipuls.composeapp.generated.resources.ok
import maxipuls.composeapp.generated.resources.profile
import maxipuls.composeapp.generated.resources.start
import maxipuls.composeapp.generated.resources.stop
import maxipuls.composeapp.generated.resources.training
import maxipuls.composeapp.generated.resources.training_info
import maxipuls.composeapp.generated.resources.training_info_desc1
import maxipuls.composeapp.generated.resources.training_info_desc2
import maxipuls.composeapp.generated.resources.training_info_desc3
import maxipuls.composeapp.generated.resources.training_info_desc4
import maxipuls.composeapp.generated.resources.trimp
import maxipuls.composeapp.generated.resources.zone1
import maxipuls.composeapp.generated.resources.zone2
import maxipuls.composeapp.generated.resources.zone3
import maxipuls.composeapp.generated.resources.zone4
import maxipuls.composeapp.generated.resources.zone5
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.training.TrainingStageChssUI
import org.example.project.ext.clickableBlank
import org.example.project.ext.formatSeconds
import org.example.project.ext.granted
import org.example.project.ext.roundToIntOrNull
import org.example.project.ext.toSensorPreviewUI
import org.example.project.platform.SpeechToTextRecognizer
import org.example.project.platform.permission.model.Permission
import org.example.project.platform.permission.service.PermissionsService
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.tablet.sensor.SensorPreviewCard
import org.example.project.screens.tablet.sensor.SensorPreviewContent
import org.example.project.screens.tablet.training.trainingResult.TrainingResultScreen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.BackIcon
import org.example.project.theme.uiKit.ButtonTextStyle
import org.example.project.theme.uiKit.HeartRateGraph
import org.example.project.theme.uiKit.MaxiAlertDialog
import org.example.project.theme.uiKit.MaxiAlertDialogButtons
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiImage
import org.example.project.theme.uiKit.MaxiSwitch
import org.example.project.utils.debouncedClick
import org.example.project.utils.orEmpty
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class TrainingScreen(
    val sportsmans: List<SportsmanSensorUI>,
    val stages: List<TrainingStageChssUI> = emptyList<TrainingStageChssUI>(),
) : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            TrainingViewModel()
        }

        DisposableEffect(Unit) {
            onDispose {
                viewModel.scanBluetoothSensorsManager.stopScan() {}
            }
        }

        val state by viewModel.stateFlow.collectAsState()
        val navigator = RootNavigator.currentOrThrow
        viewModel.scanBluetoothSensorsManager.scanSensors() {
            viewModel.addSensorInList(it)
            viewModel.newDataFromSportsman(it, sportsmans)
        }
        LaunchedEffect(viewModel) {
            viewModel.loadSportsman(sportsmans, stages = stages)
            viewModel.container.sideEffectFlow.collect {
                when (it) {

                    is TrainingEvent.StopTraining -> {
                        viewModel.scanBluetoothSensorsManager.stopScan { }
                        navigator.replace(TrainingResultScreen(it.sportsmans, it.stages))
                    }
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
                            viewModel.scanBluetoothSensorsManager.stopScan { }
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
                    state.currentStage?.let {
                        Spacer(Modifier.size(20.dp))
                        Row(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(30.dp)
                        ) {

                            Text(
                                text = "${it.title} (${it.time} мин)",
                                style = MaxiPulsTheme.typography.medium.copy(
                                    fontSize = 16.sp,
                                    color = MaxiPulsTheme.colors.uiKit.textColor
                                ),
                            )

                            Text(
                                text = "ЧСС ${it.chss}",
                                style = MaxiPulsTheme.typography.medium.copy(
                                    fontSize = 16.sp,
                                    color = MaxiPulsTheme.colors.uiKit.textColor
                                ),

                                )
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
//                enabled = state.stages.isNotEmpty(),
                onClick = debouncedClick() {
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
                    Column(
                        modifier = Modifier.fillMaxSize().padding(bottom = 65.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
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
        state.selectSportsman?.let {
            MaxiAlertDialog(
                modifier = Modifier.fillMaxWidth(0.85f).fillMaxHeight(0.85f),
                alertDialogButtons = null,
                title = "",
                paddingAfterTitle = false,
                descriptionContent = {
                    SportsmanContent(
                        modifier = Modifier.fillMaxSize(),
                        dismiss = {
                            viewModel.changeSelectSportsman(null)
                        },
                        sportsmanUI = it,
                        endTraining = { viewModel.stopTrainingSportsman(state.selectSportsman?.id.orEmpty()) },
                        changeSensor = { sensor ->
                            viewModel.updateSensor(sensor)
                        }, sensors = state.sensors
                    )
                },
                accept = {
                    viewModel.changeSelectSportsman(null)
                },
                onDismiss = {
                    viewModel.changeSelectSportsman(null)
                },
                acceptText = stringResource(Res.string.ok)
            )
        }
    }
}

@Composable
private fun SportsmanContent(
    modifier: Modifier = Modifier,
    sportsmanUI: SportsmanSensorUI,
    changeSensor: (SensorUI) -> Unit,
    sensors: List<SensorUI>,
    dismiss: () -> Unit,
    endTraining: () -> Unit
) {

    var currentSportsmanUI by remember { mutableStateOf(sportsmanUI) }

    LaunchedEffect(sportsmanUI) {
        currentSportsmanUI = sportsmanUI
    }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row() {
                BackIcon(modifier = Modifier.size(40.dp), onClick = { dismiss() })
                Spacer(Modifier.size(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(225.dp).weight(1f)
                ) {
                    Box(
                        modifier = Modifier.size(225.dp),
                    ) {
                        if (currentSportsmanUI.avatar.isBlank()) {
                            Box(
                                modifier = Modifier.fillMaxSize().background(
                                    MaxiPulsTheme.colors.uiKit.sportsmanAvatarBackground,
                                    shape = CircleShape
                                ).clip(CircleShape).align(Alignment.Center)
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.profile),
                                    modifier = Modifier.size(
                                        width = 108.dp,
                                        134.dp
                                    ).align(Alignment.Center),
                                    colorFilter = ColorFilter.tint(color = MaxiPulsTheme.colors.uiKit.divider),
                                    contentDescription = null
                                )
                            }
                        } else {
                            MaxiImage(
                                modifier = Modifier.fillMaxSize().align(Alignment.Center)
                                    .clip(CircleShape),
                                url = currentSportsmanUI.avatar,
                                contentScale = ContentScale.Crop
                            )
                        }
                        Box(
                            modifier = Modifier.background(
                                MaxiPulsTheme.colors.uiKit.grey800,
                                shape = CircleShape
                            ).clip(CircleShape).size(73.dp).align(Alignment.BottomEnd),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = currentSportsmanUI.number.toString(),
                                style = MaxiPulsTheme.typography.semiBold.copy(
                                    color = MaxiPulsTheme.colors.uiKit.lightTextColor,
                                    fontSize = 24.sp,
                                    lineHeight = 24.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size((50).dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${currentSportsmanUI.lastname}\n${
                                currentSportsmanUI.name
                            } ${currentSportsmanUI.middleName}",
                            style = MaxiPulsTheme.typography.medium.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 20.sp,
                                lineHeight = 26.sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.size((20).dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${stringResource(Res.string.age)}: ${
                                    stringResource(
                                        Res.string.age_text,
                                        currentSportsmanUI.age
                                    )
                                }",
                                style = MaxiPulsTheme.typography.regular.copy(
                                    color = MaxiPulsTheme.colors.uiKit.textColor,
                                    fontSize = 16.sp,
                                    lineHeight = 16.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(modifier = Modifier.size((20).dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${stringResource(Res.string.chss_max)}: ${currentSportsmanUI.heartRateMax}",
                                style = MaxiPulsTheme.typography.regular.copy(
                                    color = MaxiPulsTheme.colors.uiKit.textColor,
                                    fontSize = 16.sp,
                                    lineHeight = 16.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1f).wrapContentHeight(),
                        horizontalAlignment = Alignment.End
                    ) {
                        if (currentSportsmanUI.isTraining) {
                            MaxiButton(
                                modifier = Modifier.fillMaxWidth().height(69.dp),
                                text = stringResource(Res.string.ending_training),
                                buttonTextStyle = ButtonTextStyle.Bold,
                                onClick = {
                                    endTraining()
                                    dismiss()
                                }
                            )
                        }
                        Spacer(Modifier.weight(1f))

                        SelectableSensor(
                            currentValue = currentSportsmanUI.sensor,
                            onChangeWorkScope = {
                                changeSensor(it)
                            },
                            items = sensors,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
//
                }
            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                color = MaxiPulsTheme.colors.uiKit.divider
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                val zoneColors = listOf(
                    Color(0xFFAEC6F3), // Голубая
                    Color(0xFF3B6ECF), // Синяя
                    Color(0xFF96D34B), // Зеленая
                    Color(0xFFFFA93A), // Оранжевая
                    Color(0xFFDF0B40)  // Красная
                )
                zoneColors.forEachIndexed { index, item ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier.width(150.dp).height(44.dp).background(
                                shape = RoundedCornerShape(50.dp),
                                color = item
                            ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = when (index) {
                                    0 -> stringResource(Res.string.zone1)
                                    1 -> stringResource(Res.string.zone2)
                                    2 -> stringResource(Res.string.zone3)
                                    3 -> stringResource(Res.string.zone4)
                                    4 -> stringResource(Res.string.zone5)
                                    else -> stringResource(Res.string.zone1)
                                },
                                style = MaxiPulsTheme.typography.bold.copy(
                                    color = MaxiPulsTheme.colors.uiKit.lightTextColor,
                                    fontSize = 20.sp,
                                    lineHeight = 20.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(Modifier.size(10.dp))
                        Text(
                            text = when (index) {
                                0 -> sportsmanUI.zone1
                                1 -> sportsmanUI.zone2
                                2 -> sportsmanUI.zone3
                                3 -> sportsmanUI.zone4
                                4 -> sportsmanUI.zone5
                                else -> sportsmanUI.zone1
                            }.formatSeconds(),
                            style = MaxiPulsTheme.typography.semiBold.copy(
                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                fontSize = 20.sp,
                                lineHeight = 20.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                color = MaxiPulsTheme.colors.uiKit.divider
            )
            println("sportsmanUI.sensor?.heartRate.orEmpty() - ${sportsmanUI.sensor?.heartRate.orEmpty()}")
            HeartRateGraph(
                modifier = Modifier.weight(1f),
                heartRateData = sportsmanUI.sensor?.heartRate.orEmpty()
            )

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectableSensor(
    currentValue: SensorUI?,
    onChangeWorkScope: (SensorUI) -> Unit = {},
    items: List<SensorUI>,
    modifier: Modifier = Modifier,
) {
    var isExpand by remember { mutableStateOf(false) }
    var width by remember { mutableStateOf(0.dp) }
    val bottomPadding by animateDpAsState(
        targetValue = if (isExpand) 0.dp else 20.dp,
        animationSpec = tween(durationMillis = 150) // Длительность анимации в миллисекундах
    )
    BoxWithConstraints(
        modifier = modifier.background(
            color = MaxiPulsTheme.colors.uiKit.card,
            shape = if (isExpand) RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = bottomPadding,
                bottomEnd = bottomPadding
            ) else RoundedCornerShape(20.dp)
        )
    ) {
        width = maxWidth
        Column(
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(Modifier.size(20.dp))
                Column(modifier = Modifier.weight(1f)) {
                    SensorPreviewContent(
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 15.dp, bottom = 10.dp),
                        sensorUI = currentValue?.toSensorPreviewUI(),
                    ) {
                        isExpand = !isExpand
                    }
                }

                val rotationAngle by animateFloatAsState(
                    targetValue = if (isExpand) 180f else 0f,
                    animationSpec = tween(durationMillis = 300) // Длительность анимации в миллисекундах
                )
                Spacer(Modifier.size(20.dp))

                Icon(
                    painter = painterResource(Res.drawable.drop_ic),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(rotationAngle).clickableBlank {
                            isExpand = !isExpand
                        },
                    tint = MaxiPulsTheme.colors.uiKit.textColor
                )
                Spacer(Modifier.size(20.dp))
            }
            Box(modifier = Modifier.width(width).offset(x = 0.dp, y = (-1).dp)) {
                if (isExpand) {
                    Popup(
                        onDismissRequest = {
                            isExpand = !isExpand
                        },
                        properties = PopupProperties(focusable = true)
                    ) {
                        Column(
                            modifier = Modifier.width(width)
                                .heightIn(max = 163.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topEnd = 0.dp,
                                        topStart = 0.dp,
                                        bottomEnd = 20.dp, bottomStart = 20.dp,
                                    )
                                )
                                .background(
                                    color = MaxiPulsTheme.colors.uiKit.card,
                                    shape = RoundedCornerShape(
                                        topEnd = 0.dp,
                                        topStart = 0.dp,
                                        bottomEnd = 20.dp, bottomStart = 20.dp,
                                    )
                                )
                                .animateContentSize(),
                        ) {
                            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                items.filter { it.sensorId != currentValue?.sensorId }
                                    .forEachIndexed { index, item ->

                                        HorizontalDivider(
                                            modifier = Modifier.fillMaxWidth()
                                                .padding(horizontal = 20.dp),
                                            color = MaxiPulsTheme.colors.uiKit.textFieldStroke
                                        )

                                        SensorPreviewContent(
                                            modifier = Modifier.padding(
                                                start = 20.dp,
                                                end = 60.dp,
                                                top = 10.dp,
                                                bottom = 10.dp
                                            ).fillMaxWidth(),
                                            sensorUI = item.toSensorPreviewUI(),
                                        ) {
                                            onChangeWorkScope(item)
                                            isExpand = !isExpand
                                        }

                                    }

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChssSportsmanItem(
    modifier: Modifier = Modifier,
    sportsmanUI: SportsmanSensorUI,
    onClick: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var sensorAvailable: Boolean? by remember { mutableStateOf(null) }
    var sportsmanMutable = remember { mutableStateOf(sportsmanUI) }
    LaunchedEffect(sportsmanUI) {
        sportsmanMutable.value = sportsmanUI
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


    val hmax = 230
    val hmin = 0
    Box(
        modifier.background(
            color = Color(0xFF3B6ECF),
            shape = RoundedCornerShape(25.dp)
        ).clip(RoundedCornerShape(25.dp)).clickableBlank { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.size(10.dp))
            Box(
                modifier = Modifier.height(59.dp).padding(horizontal = 20.dp).fillMaxWidth()
                    .background(
                        color = MaxiPulsTheme.colors.uiKit.white,
                        shape = RoundedCornerShape(100.dp)
                    ).clip(RoundedCornerShape(100.dp)),
            ) {
                Text(
                    text = sportsmanUI.sensor?.heartRate.orEmpty().lastOrNull()?.value.orEmpty()
                        .toString()
                        .orEmpty(),
                    style = MaxiPulsTheme.typography.bold.copy(
                        fontSize = 32.sp,
                        lineHeight = 32.sp,
                        color = Color(0xFF3B6ECF)
                    ),
                    maxLines = 1,
                    modifier = Modifier.align(Alignment.Center)
                )
                if (!(sensorAvailable ?: sportsmanUI.available)) {
                    Icon(
                        painter = painterResource(Res.drawable.attension),
                        tint = MaxiPulsTheme.colors.uiKit.primary,
                        modifier = Modifier.align(Alignment.CenterEnd).padding(5.dp).size(24.dp),
                        contentDescription = null,
                    )
                }

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
                        ((sportsmanUI.sensor?.heartRate.orEmpty().map { it.value }.lastOrNull()
                            .orEmpty()
                            .toFloat() / hmax) * 100).roundToIntOrNull().orEmpty()
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
                    text = sportsmanUI.name,
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
    sportsmanUI: SportsmanSensorUI,
    onClick: () -> Unit,
) {
    val hmax = sportsmanUI.heartRateMax
    val hmin = sportsmanUI.heartRateMin
    var sensorAvailable: Boolean? by remember { mutableStateOf(null) }
    var sportsmanMutable = remember { mutableStateOf(sportsmanUI) }
    LaunchedEffect(sportsmanUI) {
        sportsmanMutable.value = sportsmanUI
    }

    LaunchedEffect(sportsmanMutable) {
        if (sensorAvailable == null) {
            launch {
                SportsmanSensorUI.available(sportsmanMutable).collect {
                    println("fromTRIMP - $it")
                    sensorAvailable = it
                }
            }
        }
    }
    Box(
        modifier.background(
            color = Color(0xFF3B6ECF),
            shape = RoundedCornerShape(25.dp)
        ).clip(RoundedCornerShape(25.dp)).clickableBlank { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.size(10.dp))
            Box(modifier = Modifier.padding(horizontal = 11.dp).fillMaxWidth()) {
                Column(
                    modifier = Modifier.height(47.dp).fillMaxWidth()
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
                        text = sportsmanUI.name,
                        style = MaxiPulsTheme.typography.semiBold.copy(
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        ),
                        maxLines = 1,
                        modifier = Modifier
                    )
                }
                if (!(sensorAvailable ?: sportsmanUI.available)) {
                    Icon(
                        painter = painterResource(Res.drawable.attension),
                        tint = MaxiPulsTheme.colors.uiKit.primary,
                        modifier = Modifier.align(Alignment.CenterEnd).padding(5.dp).size(24.dp),
                        contentDescription = null,
                    )
                }
            }
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 9.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
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
                println(
                    "sportsmanUI.sensor?.heartRate.orEmpty().lastOrNull() - ${
                        sportsmanUI.sensor?.heartRate.orEmpty().lastOrNull()
                    }"
                )
                Text(
                    text = sportsmanUI.sensor?.heartRate.orEmpty().lastOrNull()?.value.orEmpty()
                        .toString(),
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
                            sportsmanUI.sensor?.heartRate.orEmpty().lastOrNull()?.value.orEmpty()
                                .toFloat() / hmax.toFloat()
                        )
                            .background(
                                color = Color(0xFF3B6ECF),
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
                        text = hmin.toString(),
                        style = MaxiPulsTheme.typography.bold.copy(
                            fontSize = 20.sp,
                            lineHeight = 20.sp,
                            color = Color(0xFF3B6ECF)
                        ),
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = hmax.toString(),
                        style = MaxiPulsTheme.typography.semiBold.copy(
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            color = Color(0xFF3B6ECF)
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