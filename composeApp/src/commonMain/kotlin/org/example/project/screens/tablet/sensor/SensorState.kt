package org.example.project.screens.tablet.sensor

import org.example.project.domain.model.sensor.SensorPreviewUI
import org.example.project.domain.model.sportsman.SensorStatus
import org.example.project.domain.model.sportsman.SensorUI

data class SensorState(
    val sensors: List<SensorPreviewUI>,
    val savedSensors: List<SensorPreviewUI>,
    val isGrid: Boolean,
    val isLoading: Boolean,
) {
    companion object {
        val InitState = SensorState(
            emptyList(),
            emptyList(),
            true,
            true,
        )
    }
}