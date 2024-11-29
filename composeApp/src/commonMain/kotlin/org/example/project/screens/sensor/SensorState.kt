package org.example.project.screens.sensor

import org.example.project.domain.model.sportsman.SensorStatus
import org.example.project.domain.model.sportsman.SensorUI

data class SensorState(
    val sensors: List<SensorUI>,
    val savedSensors: List<SensorUI>,
    val isGrid: Boolean,
    val isLoading: Boolean,
) {
    companion object {
        val InitState = SensorState(
            List(13) { index ->
                SensorUI(
                    sensorId = "00:1A:7D:DA:71:${10 + index}",
                    deviceName = "Sensor ${'A' + index}",
                    status = SensorStatus.Unknown
                )
            },
            List(13) { index ->
                SensorUI(
                    sensorId = "00:1A:7D:DA:72:${10 + index}",
                    deviceName = "Saved Sensor ${'X' + index}",
                    status = if (index % 2 == 0) SensorStatus.Active else SensorStatus.Disable
                )
            },
            true,
            true,
        )
    }
}