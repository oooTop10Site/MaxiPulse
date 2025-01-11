package org.example.project.screens.adaptive.main

import org.example.project.domain.model.sportsman.SportsmanSensorUI

sealed interface MainEvent {
    object ShuttleRun: MainEvent
    object ReadiesForUpload: MainEvent
    data class Training(val sportsmans: List<SportsmanSensorUI>): MainEvent
}