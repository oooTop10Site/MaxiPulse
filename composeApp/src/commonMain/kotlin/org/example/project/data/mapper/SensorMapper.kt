package org.example.project.data.mapper

import org.example.project.data.model.sensor.SensorPreviewResponse
import org.example.project.data.model.sensor.SensorResponse
import org.example.project.domain.model.sensor.SensorPreviewUI
import org.example.project.domain.model.sportsman.SensorStatus
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.platform.randomUUID
import org.example.project.utils.orEmpty

fun SensorResponse.toUI(): SensorPreviewUI {
    return SensorPreviewUI(
        id = randomUUID(),
        mac = deviceAddress.orEmpty(),
        name = deviceName.orEmpty(),
        status = SensorStatus.Active //todo
    )
}

fun SensorResponse.toSensorUI(): SensorUI {
    return SensorUI(
        companyId = companyId.orEmpty(),
        runningCounter = runningCounter.orEmpty(),
        battery = battery?.toIntOrNull().orEmpty(),
        deviceName = deviceName.orEmpty(),
        acc = acc?.toFloat().orEmpty(),
        rr = rateInterval.orEmpty(),
        sensorId = deviceAddress.orEmpty(),
        heartRate = listOf(heartRate.orEmpty()),
        status = SensorStatus.Active //todo
    )
}

fun SensorPreviewResponse.toUI(): SensorPreviewUI {
    return SensorPreviewUI(
        id = id.orEmpty(),
        name = name.orEmpty(),
        mac = mac.orEmpty()
    )
}