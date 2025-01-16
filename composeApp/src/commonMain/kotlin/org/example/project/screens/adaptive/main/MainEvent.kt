package org.example.project.screens.adaptive.main

import org.example.project.domain.model.sportsman.SportsmanSensorUI

sealed interface MainEvent {
    data class ShuttleRun(val sportsmans: List<SportsmanSensorUI>): MainEvent
    data class ReadiesForUpload(val sportsmans: List<SportsmanSensorUI>): MainEvent
    data class Training(val sportsmans: List<SportsmanSensorUI>): MainEvent
}