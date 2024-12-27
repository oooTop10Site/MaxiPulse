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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.sensor
import org.example.project.screens.adaptive.root.ScreenSize
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiPageContainer
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import maxipuls.composeapp.generated.resources.accessed_devices
import maxipuls.composeapp.generated.resources.add_round_ic
import maxipuls.composeapp.generated.resources.close_solid_ic
import maxipuls.composeapp.generated.resources.grid_ic
import maxipuls.composeapp.generated.resources.indicator_ic
import maxipuls.composeapp.generated.resources.rectangle_listv2
import maxipuls.composeapp.generated.resources.saved_devices
import maxipuls.composeapp.generated.resources.search_available_devices
import org.example.project.domain.model.sportsman.SensorStatus
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.ext.clickableBlank
import org.example.project.ext.toColor
import org.example.project.ext.toText
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.collections.chunked
import kotlin.collections.forEach

class SensorScreen : Screen {

    @Composable
    override fun Content() {
        val screenSize = ScreenSize.currentOrThrow
        val viewModel = rememberScreenModel {
            SensorViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        val chunkSize = when {
            !state.isGrid -> 1
            screenSize.widthSizeClass == WindowWidthSizeClass.Medium -> 2
            screenSize.widthSizeClass == WindowWidthSizeClass.Expanded -> 2
            screenSize.widthSizeClass == WindowWidthSizeClass.Compact -> 1
            else -> 1
        }

        LaunchedEffect(viewModel) {
            viewModel.loadSensors()
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
                    Icon(
                        if (state.isGrid) painterResource(Res.drawable.grid_ic) else painterResource(
                            Res.drawable.rectangle_listv2
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp).clickableBlank {
                            viewModel.changeIsGrid()
                        }.align(Alignment.CenterEnd),
                        tint = MaxiPulsTheme.colors.uiKit.textColor,
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )
            }
        }) {

            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.saved_devices),
                    style = MaxiPulsTheme.typography.bold.copy(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f, state.isLoading)
                        .animateContentSize(),
                    contentPadding = PaddingValues(vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(state.savedSensors.chunked(chunkSize)) { chunk ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(25.dp)
                        ) {
                            chunk.forEach {
                                SensorCard(
                                    modifier = Modifier.weight(1f),
                                    sensorUI = it,
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
                            if (chunk.size != chunkSize) {
                                for (i in 1..chunkSize - chunk.size) {
                                    Box(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    color = MaxiPulsTheme.colors.uiKit.divider
                )

                Spacer(Modifier.size(20.dp))
                Text(
                    text = stringResource(Res.string.accessed_devices),
                    style = MaxiPulsTheme.typography.bold.copy(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        color = MaxiPulsTheme.colors.uiKit.textColor
                    ),
                )
                if (state.isLoading) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f)) {
                        SearchAvailableDevices(modifier = Modifier.fillMaxSize())
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().weight(1f, state.isLoading),
                        contentPadding = PaddingValues(vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(state.sensors.chunked(chunkSize)) { chunk ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(25.dp)
                            ) {
                                chunk.forEach {
                                    SensorCard(
                                        modifier = Modifier.weight(1f),
                                        sensorUI = it,
                                        icon = {
                                            Box(
                                                modifier = Modifier.size(30.dp)
                                                    .background(
                                                        MaxiPulsTheme.colors.uiKit.primary,
                                                        shape = CircleShape
                                                    )
                                                    .clip(CircleShape),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    painterResource(Res.drawable.add_round_ic),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(18.dp).clickableBlank {

                                                    },
                                                    tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                                                )
                                            }
                                        }
                                    ) {
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
            }

        }
    }
}


@Composable
internal fun SensorCard(
    modifier: Modifier = Modifier,
    sensorUI: SensorUI,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    Column(
        modifier.background(
            color = MaxiPulsTheme.colors.uiKit.card,
            shape = RoundedCornerShape(20.dp)
        ).clip(RoundedCornerShape(20.dp)).clickableBlank() {
            onClick()
        }.animateContentSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f).padding(end = 7.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        sensorUI.sensorId,
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
                        sensorUI.deviceName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaxiPulsTheme.typography.semiBold.copy(
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        )
                    )

                }
                if (sensorUI.status != SensorStatus.Unknown) {
                    Text(
                        sensorUI.status.toText(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaxiPulsTheme.typography.semiBold.copy(
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            color = sensorUI.status.toColor()
                        )
                    )
                }
            }

            icon?.invoke()
        }
    }
}

@Composable
fun SearchAvailableDevices(modifier: Modifier = Modifier) {
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