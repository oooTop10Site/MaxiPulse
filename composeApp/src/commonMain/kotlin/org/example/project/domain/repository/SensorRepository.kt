package org.example.project.domain.repository

import org.example.project.domain.model.sensor.SensorPreviewUI
import org.example.project.platform.Either
import org.example.project.platform.Failure

interface SensorRepository {
    suspend fun getSensors(): Either<Failure, List<SensorPreviewUI>>
    suspend fun addSensor(mac: String, name: String): Either<Failure, Unit>
}