package org.example.project.domain.model.training

import org.example.project.domain.model.sportsman.SensorUI

data class TrainingUI(
    val sensorUI: SensorUI,
    val duration: Long,
) {
    companion object {
        val Default = TrainingUI(
            sensorUI = SensorUI.Empty,
            duration = 0,
        )
    }
}