package org.example.project.screens.component.selectSensor

import org.example.project.domain.model.sportsman.SensorUI

data class SelectSensorState(
    val devices: List<SensorUI>,
    val selectSensor: String,
) {

    companion object {
        val InitState = SelectSensorState(
            emptyList(),
            ""
        )
    }

}