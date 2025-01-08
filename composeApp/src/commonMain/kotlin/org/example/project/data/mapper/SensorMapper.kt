package org.example.project.data.mapper

import org.example.project.data.model.sensor.SensorResponse
import org.example.project.domain.model.sportsman.SensorStatus
import org.example.project.domain.model.sportsman.SensorUI

fun SensorResponse.toUI(): SensorUI {
    return SensorUI(
        sensorId = deviceAddress.orEmpty(),
        deviceName = deviceName.orEmpty(),
        status = SensorStatus.Active //todo
    )
}