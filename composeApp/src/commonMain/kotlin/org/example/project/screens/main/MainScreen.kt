package org.example.project.screens.main

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
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
import maxipuls.composeapp.generated.resources.start_tarining
import maxipuls.composeapp.generated.resources.what_do_if_sensor_not_active
import maxipuls.composeapp.generated.resources.what_do_if_sensor_not_active_desc
import org.example.project.domain.model.ButtonActions
import org.example.project.domain.model.MainAlertDialog
import org.example.project.domain.model.test.TestUI
import org.example.project.ext.clickableBlank
import org.example.project.screens.main.components.SportsmanSensorCard
import org.example.project.screens.root.RootNavigator
import org.example.project.screens.root.ScreenSize
import org.example.project.screens.tests.shuttleRun.ShuttleRunScreen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiAlertDialog
import org.example.project.theme.uiKit.MaxiAlertDialogButtons
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiPageContainer
import org.example.project.theme.uiKit.MaxiSwitch
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.collections.chunked

class MainScreen(val testUI: TestUI? = null) : Screen {
    @Composable
    override fun Content() {
        MaxiPageContainer() {
            val viewModel = rememberScreenModel {
                MainViewModel()
            }
            val rootNavigator = RootNavigator.currentOrThrow
            val state by viewModel.stateFlow.collectAsState()
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
                launch {
                    viewModel.container.sideEffectFlow.collect {
                        when (it) {
                            MainEvent.ShuttleRun -> rootNavigator.push(ShuttleRunScreen())
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
                        onClick = {
                            if (!state.isStartTraining) {
                                viewModel.changeIsStartTraining()
                            } else {
                                viewModel.startTraining(testUI)
                            }
                        }, shape = RoundedCornerShape(0.dp), text = stringResource(
                            Res.string.start_tarining
                        ), modifier = Modifier.fillMaxWidth().height((94 / buttonDivision).dp),
                        buttonActions = ButtonActions.Unlimit,
                        enabled = (state.alertDialog == null && state.selectSportsmans.isNotEmpty()) || !state.isStartTraining
                    )
                },
                topBar = {
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
                                    },
                                    placeholder = stringResource(Res.string.search),
                                    modifier = Modifier.height(40.dp).weight(1f),
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
                        items(state.sportsmans.chunked(chunkSize)) { chunk ->
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
                        accept = {
                            viewModel.changeAlertDialog(null)
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
                    var selectSensor by remember { mutableStateOf(alertData.sportsman.sensor) }
                    MaxiAlertDialog(
                        modifier = Modifier.padding(horizontal = 20.dp).width(600.dp),
                        title = stringResource(Res.string.choose_sensor),
                        paddingValues = PaddingValues(
                            start = 20.dp,
                            end = 20.dp,
                            top = 30.dp,
                            bottom = 40.dp
                        ),
                        paddingValuesButton = PaddingValues(
                            horizontal = 20.dp
                        ),
                        accept = {
                            selectSensor?.let {
                                viewModel.changeSensorValidation(
                                    it,
                                    alertData.sportsman.id
                                )
                            }
                        },
                        acceptText = stringResource(Res.string.ok),
                        cancelText = stringResource(Res.string.cancel),
                        alertDialogButtons = MaxiAlertDialogButtons.CancelAccept,
                        onDismiss = {
                            viewModel.changeAlertDialog(null)
                        },
                        paddingAfterTitle = false,
                        descriptionContent = {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth()
                                    .heightIn(min = 350.dp, max = 500.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                contentPadding = PaddingValues(vertical = 40.dp),
                                verticalArrangement = Arrangement.spacedBy(13.dp)
                            ) {
                                items(state.sensors) {
                                    val alreadyExist =
                                        it in state.sportsmans.filter { it != alertData.sportsman }
                                            .map { it.sensor }
                                    val isSelect = selectSensor == it
                                    Row(
                                        modifier = Modifier.fillMaxWidth().background(
                                            color = if (isSelect) MaxiPulsTheme.colors.uiKit.grey800 else MaxiPulsTheme.colors.uiKit.grey400,
                                            shape = RoundedCornerShape(25.dp)
                                        ).clip(RoundedCornerShape(25.dp)).clickableBlank {
                                            if (it != selectSensor) {
                                                selectSensor = it
                                            }
                                        },
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Movesense ${it.sensorId}",
                                            style = MaxiPulsTheme.typography.regular.copy(
                                                color = MaxiPulsTheme.colors.uiKit.textColor,
                                                fontSize = 16.sp,
                                                lineHeight = 16.sp
                                            ),
                                            color = if (isSelect) MaxiPulsTheme.colors.uiKit.lightTextColor else MaxiPulsTheme.colors.uiKit.textColor,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(
                                                top = 17.dp,
                                                bottom = 17.dp,
                                                start = 20.dp
                                            )
                                        )
                                        if (alreadyExist) {
                                            Icon(
                                                painter = painterResource(Res.drawable.error_ic),
                                                contentDescription = null,
                                                modifier = Modifier.padding(end = 20.dp),
                                                tint = if (isSelect) MaxiPulsTheme.colors.uiKit.lightTextColor else MaxiPulsTheme.colors.uiKit.primary
                                            )
                                        }
                                    }
                                }
                            }
                        },
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
}