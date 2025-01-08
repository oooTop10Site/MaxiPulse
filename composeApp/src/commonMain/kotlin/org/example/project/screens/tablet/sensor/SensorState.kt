package org.example.project.screens.tablet.sensor

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
            emptyList(),
            emptyList(),
            true,
            true,
        )
    }
}