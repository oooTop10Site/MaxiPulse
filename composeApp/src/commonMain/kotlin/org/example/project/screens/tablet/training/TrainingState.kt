package org.example.project.screens.tablet.training

import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.training.TrainingStageChssUI

data class TrainingState(
    val isTrimp: Boolean,
    val isAlertDialog: Boolean,
    val durationSeconds: Long,
    val isStart: Boolean,
    val sportsmans: List<SportsmanSensorUI>,
    val selectSportsman: SportsmanSensorUI?,
    val stages: List<TrainingStageChssUI>,
    val currentStage: TrainingStageChssUI?
) {
    companion object {
        val InitState = TrainingState(
            false,
            false,
            0,
            false,
            sportsmans = emptyList(),
            null,
            emptyList(),
//            listOf(
//                TrainingStageChssUI(time = 1, title = "aloxa", chss = 120),
//                TrainingStageChssUI(time = 2, title = "aloxa2", chss = 140)
//            ),
            null
        )
    }
}