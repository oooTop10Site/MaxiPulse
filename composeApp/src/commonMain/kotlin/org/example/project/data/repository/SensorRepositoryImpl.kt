package org.example.project.data.repository

import org.example.project.data.api.MaxiPulseApi
import org.example.project.data.mapper.toUI
import org.example.project.data.model.sensor.SensorPreviewResponse
import org.example.project.domain.model.sensor.SensorPreviewUI
import org.example.project.domain.repository.SensorRepository
import org.example.project.platform.Either
import org.example.project.platform.Failure
import org.example.project.platform.apiCall

class SensorRepositoryImpl(
    val api: MaxiPulseApi
) : SensorRepository {
    override suspend fun getSensors(): Either<Failure, List<SensorPreviewUI>> {
        return apiCall(
            call = {
                api.getSensor()
            },
            mapResponse = {
                it.data?.map { it.toUI() }.orEmpty()
            }
        )
    }

    override suspend fun addSensor(
        mac: String,
        name: String
    ): Either<Failure, Unit> {
        return apiCall(
            call = {
                api.addSensor(
                    request = SensorPreviewResponse(
                        mac = mac,
                        name = name,
                        id = null
                    )
                )
            }
        )
    }
}