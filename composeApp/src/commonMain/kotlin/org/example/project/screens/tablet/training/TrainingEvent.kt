package org.example.project.screens.tablet.training

sealed interface TrainingEvent {
    object StopTraining: TrainingEvent
}