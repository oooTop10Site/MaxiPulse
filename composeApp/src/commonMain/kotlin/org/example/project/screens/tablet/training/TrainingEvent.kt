package org.example.project.screens.tablet.training

import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.training.TrainingStageChssUI

sealed interface TrainingEvent {
    data class StopTraining(val sportsmans: List<SportsmanSensorUI>, val stages: List<TrainingStageChssUI>): TrainingEvent
}