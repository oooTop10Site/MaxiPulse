package org.example.project.theme.uiKit

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.cancel
import maxipuls.composeapp.generated.resources.choose_sensor
import maxipuls.composeapp.generated.resources.error_ic
import maxipuls.composeapp.generated.resources.ok
import org.example.project.domain.model.MainAlertDialog
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.ext.clickableBlank
import org.example.project.platform.ScanBluetoothSensorsManager
import org.example.project.screens.adaptive.main.MainState
import org.example.project.screens.adaptive.main.MainViewModel
import org.example.project.screens.tablet.sensor.SearchAvailableDevices
import org.example.project.theme.MaxiPulsTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Composable
internal fun KoinComponent.SelectSensor(
    onDismiss: () -> Unit,
    observeSensor: (SensorUI) -> Unit,
    sensorAlreadyExit: (SensorUI) -> Boolean = { false },
    accept: (SensorUI, String) -> Unit,
    sportsmanId: String,
    sensor: SensorUI?,
) {
    val scanBluetoothSensorsManager: ScanBluetoothSensorsManager by inject()
    var devices: SnapshotStateList<SensorUI> = remember { mutableStateListOf<SensorUI>() }

    var selectSensor by remember { mutableStateOf(sensor) }
    scanBluetoothSensorsManager.scanSensors(onCatch = { onDismiss() }) {
        println("device - $it")
        if(it.sensorId !in devices.map { it.sensorId }) {
            devices.add(it)
        }
        observeSensor(it)
    }
    MaxiAlertDialog(
        modifier = Modifier.padding(horizontal = 20.dp).width(600.dp).animateContentSize(),
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
                accept(
                    it,
                    sportsmanId
                )
            }
        },
        acceptText = stringResource(Res.string.ok),
        cancelText = stringResource(Res.string.cancel),
        alertDialogButtons = MaxiAlertDialogButtons.CancelAccept,
        onDismiss = {
            onDismiss()
        },
        paddingAfterTitle = false,
        descriptionContent = {
            if (devices.isEmpty()) {
                Box(modifier = Modifier.heightIn(400.dp), contentAlignment = Alignment.Center) {
                    SearchAvailableDevices(modifier = Modifier.size(200.dp), false)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                        .heightIn(min = 350.dp, max = 500.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(vertical = 40.dp),
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    items(devices) {
                        val alreadyExist = sensorAlreadyExit(it)

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
                                text = "${it.deviceName} ${it.sensorId}",
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
            }
        },
    )
}