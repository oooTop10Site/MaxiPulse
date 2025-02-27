package org.example.project.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.active
import maxipuls.composeapp.generated.resources.disable
import maxipuls.composeapp.generated.resources.empty_string
import org.example.project.domain.model.sensor.SensorPreviewUI
import org.example.project.domain.model.sportsman.SensorStatus
import org.example.project.domain.model.sportsman.SensorStatus.*
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.theme.MaxiPulsTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun SensorStatus.toColor(): Color {
    return when (this) {
        Disable -> MaxiPulsTheme.colors.uiKit.disable
        Active -> MaxiPulsTheme.colors.uiKit.primary
        Unknown -> MaxiPulsTheme.colors.uiKit.textColor
    }
}

@Composable
fun SensorStatus.toText(): String {
    return stringResource(
        when (this) {
            Disable -> Res.string.disable
            Active -> Res.string.active
            Unknown -> Res.string.empty_string
        }
    )
}

fun SensorUI.toSensorPreviewUI(): SensorPreviewUI {
    return SensorPreviewUI(
        id = sensorId,
        name = deviceName,
        mac = sensorId,
        status = status,
        sportsmanUI = SportsmanUI.Default //todo
    )
}