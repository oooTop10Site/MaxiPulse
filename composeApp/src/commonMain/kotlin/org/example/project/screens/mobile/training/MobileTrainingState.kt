package org.example.project.screens.mobile.training

import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.training.TrainingUI

data class MobileTrainingState(
    val currentTraining: TrainingUI,
    val isStart: Boolean,
    val connectSensorDialog: Boolean,
) {
    companion object {
        val InitState = MobileTrainingState(
            currentTraining = TrainingUI.Default,
            isStart = false,
            connectSensorDialog = false,
        )
    }
}