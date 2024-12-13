package org.example.project.screens.training

sealed interface TrainingEvent {
    object StopTraining: TrainingEvent
}