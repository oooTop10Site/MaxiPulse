package org.example.project.screens.tablet.training

import org.example.project.domain.model.sportsman.SportsmanSensorUI

sealed interface TrainingEvent {
    data class StopTraining(val sportsmans: List<SportsmanSensorUI>): TrainingEvent
}