package org.example.project.screens.tablet.sensor

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.sensor
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiPageContainer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import maxipuls.composeapp.generated.resources.accessed_devices
import maxipuls.composeapp.generated.resources.add_ic
import maxipuls.composeapp.generated.resources.add_round_ic
import maxipuls.composeapp.generated.resources.close_solid_ic
import maxipuls.composeapp.generated.resources.indicator_ic
import maxipuls.composeapp.generated.resources.saved_devices
import maxipuls.composeapp.generated.resources.search_available_devices
import maxipuls.composeapp.generated.resources.sensor_not_choose
import maxipuls.composeapp.generated.resources.sensor_not_found
import maxipuls.composeapp.generated.resources.sportsman
import org.example.project.data.mapper.toAiEvent
import org.example.project.data.model.screen.Screens
import org.example.project.domain.model.AiEvent
import org.example.project.domain.model.sensor.SensorPreviewUI
import org.example.project.domain.model.sportsman.SensorStatus
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.ext.clickableBlank
import org.example.project.ext.toColor
import org.example.project.ext.toText
import org.example.project.platform.ScanBluetoothSensorsManager
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.screens.adaptive.root.navigateEvent
import org.example.project.theme.uiKit.MaxiTextFieldMenu
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.inject
import kotlin.getValue

class SensorScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            SensorViewModel()
        }
        val rootNavigator = RootNavigator.currentOrThrow
        val tabNavigator = LocalTabNavigator.current
        viewModel.scanBluetoothSensorsManager.scanSensors(onCatch = { }) {
            println("device - $it")
            viewModel.addRemoteSensor(it)
        }
        val state by viewModel.stateFlow.collectAsState()

        LaunchedEffect(viewModel) {
            viewModel.loadSensors()
            viewModel.loadSportsman()
        }
        MaxiPageContainer(topBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                    Text(
                        text = stringResource(Res.string.sensor),
                        style = MaxiPulsTheme.typography.bold.copy(
                            fontSize = 20.sp,
                            lineHeight = 20.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
            }
        }) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(20.dp))
                    Text(
                        text = stringResource(Res.string.saved_devices),
                        style = MaxiPulsTheme.typography.bold.copy(
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        ),
                    )
                    Spacer(Modifier.size(5.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().weight(1f, state.isLoading)
                            .animateContentSize(),
                        contentPadding = PaddingValues(vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(state.savedSensors) { it ->
                            SensorPreviewCard(
                                modifier = Modifier.weight(1f),
                                sensorUI = it,
                                sportsmen = state.sportsmen,
                                canChange = true,
                                changeSportsman = { sportsman ->
                                    viewModel.changeSportsman(
                                        sensorId = it.id,
                                        sportsmanUI = sportsman
                                    )
                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(Res.drawable.close_solid_ic),
                                        contentDescription = null,
                                        tint = MaxiPulsTheme.colors.uiKit.primary,
                                        modifier = Modifier.clickableBlank() {
                                        }.size(30.dp)
                                    )
                                }
                            ) {
                            }
                        }
                    }
                }
                Spacer(Modifier.size(20.dp))
                VerticalDivider(
                    Modifier.fillMaxHeight(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
                Spacer(Modifier.size(20.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(20.dp))
                    Text(
                        text = stringResource(Res.string.accessed_devices),
                        style = MaxiPulsTheme.typography.bold.copy(
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        ),
                    )
                    Spacer(Modifier.size(5.dp))
                    if (state.isLoading) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f)) {
                            SearchAvailableDevices(
                                modifier = Modifier.fillMaxSize(),
                                showText = true
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().weight(1f, state.isLoading),
                            contentPadding = PaddingValues(vertical = 20.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            println(state.sensors)
                            items(state.sensors) { it ->
                                SensorPreviewCard(
                                    canChange = false,
                                    modifier = Modifier.weight(1f),
                                    sensorUI = it,
                                    icon = {
                                        Icon(
                                            painter = painterResource(Res.drawable.add_ic),
                                            contentDescription = null,
                                            tint = MaxiPulsTheme.colors.uiKit.primary,
                                            modifier = Modifier.clickableBlank() {
                                                viewModel.addSensor(it)
                                            }.size(30.dp)
                                        )
                                    },
                                    sportsmen = state.sportsmen,
                                    changeSportsman = { sportsman ->
                                    }
                                ) {
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
internal fun ColumnScope.SensorPreviewContent(
    modifier: Modifier = Modifier,
    sensorUI: SensorPreviewUI?,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    Column(modifier.clickableBlank() {
        onClick()
    }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f).padding(end = 7.dp)
            ) {
                if (sensorUI?.mac.orEmpty().isBlank()) {
                    Text(
                        stringResource(Res.string.sensor_not_choose),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaxiPulsTheme.typography.semiBold.copy(
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        ),
                        modifier = Modifier.weight(1f, false)
                    )
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            sensorUI?.mac.orEmpty(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaxiPulsTheme.typography.semiBold.copy(
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            ),
                            modifier = Modifier.weight(1f, false)
                        )

                        VerticalDivider(
                            modifier = Modifier.height(20.dp),
                            color = MaxiPulsTheme.colors.uiKit.divider
                        )
                        Text(
                            sensorUI?.name.orEmpty(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaxiPulsTheme.typography.semiBold.copy(
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                                color = MaxiPulsTheme.colors.uiKit.textColor
                            )
                        )

                    }

                    if (sensorUI?.status != SensorStatus.Unknown) {
                        Text(
                            sensorUI?.status?.toText().orEmpty(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaxiPulsTheme.typography.semiBold.copy(
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                                color = sensorUI?.status?.toColor()
                                    ?: androidx.compose.ui.graphics.Color.Black
                            )
                        )
                    }
                }
            }

            icon?.invoke()
        }
    }
}


@Composable
fun SensorPreviewCard(
    modifier: Modifier = Modifier,
    sensorUI: SensorPreviewUI,
    canChange: Boolean = true,
    sportsmen: List<SportsmanUI>,
    changeSportsman: (SportsmanUI) -> Unit,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    println(sensorUI)
    Column(
        modifier.background(
            color = MaxiPulsTheme.colors.uiKit.card,
            shape = RoundedCornerShape(20.dp)
        ).clip(RoundedCornerShape(20.dp)).animateContentSize()
    ) {
        SensorPreviewContent(
            Modifier.padding(horizontal = 20.dp, vertical = 15.dp).fillMaxSize(),
            sensorUI,
            icon,
            onClick
        )
        if (canChange) {
            Spacer(Modifier.size(5.dp))

            MaxiTextFieldMenu<SportsmanUI>(
                currentValue = sensorUI.sportsmanUI,
                text = "${sensorUI.sportsmanUI.lastname} ${sensorUI.sportsmanUI.name}",
                onChangeWorkScope = {
                    changeSportsman(it)
                },
                hintTextColor = MaxiPulsTheme.colors.uiKit.textColor.copy(alpha = 0.8f),
                items = sportsmen,
                itemToString = { "${it.lastname} ${it.name}" },
                modifier = Modifier.height(Constants.TextFieldHeight)
                    .fillMaxWidth().padding(horizontal = 20.dp),
                placeholderText = "",
                containerColor = MaxiPulsTheme.colors.uiKit.fieldBG
            )
            Spacer(Modifier.size(15.dp))

        }
    }
}

@Composable
fun SearchAvailableDevices(modifier: Modifier = Modifier, showText: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition()

    // Creates a child animation of float type as a part of the [InfiniteTransition].
    val rotate by
    infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
        infiniteRepeatable(
            animation = tween(2500),
            repeatMode = RepeatMode.Restart
        )
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(Res.drawable.indicator_ic),
            contentDescription = null,
            modifier = Modifier.size(100.dp).clickableBlank {

            }.rotate(rotate),
        )
        if (showText) {
            Spacer(Modifier.size(20.dp))
            Text(
                text = stringResource(Res.string.search_available_devices),
                style = MaxiPulsTheme.typography.bold.copy(
                    color = MaxiPulsTheme.colors.uiKit.textDropDown,
                    fontSize = 40.sp,
                    lineHeight = 40.sp
                ),
                modifier = Modifier.width(400.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}