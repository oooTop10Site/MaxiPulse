package org.example.project.screens.tablet.training

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.Clock.System
import org.example.project.domain.model.sportsman.HeartBit
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.sportsman.TrainingSportsmanUI

data class TrainingState(
    val isTrimp: Boolean,
    val isAlertDialog: Boolean,
    val durationSeconds: Long,
    val isStart: Boolean,
    val sportsmans: List<SportsmanSensorUI>,
    val selectSportsman: SportsmanSensorUI?,
) {
    companion object {
        val InitState = TrainingState(
            false, false, 0, false, sportsmans = emptyList(),
            null,
        )
    }
}