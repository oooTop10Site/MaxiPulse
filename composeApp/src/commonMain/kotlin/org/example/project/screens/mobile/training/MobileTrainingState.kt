package org.example.project.screens.mobile.training

import org.example.project.domain.model.sportsman.SensorUI

data class MobileTrainingState(
    val selectSensor: SensorUI?,
    val isStart: Boolean,
) {
    companion object {
        val InitState = MobileTrainingState(
            selectSensor = null,
            isStart = false
        )
    }
}