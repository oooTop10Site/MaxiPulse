package org.example.project.screens.sportsman.detail

import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanUI

data class SportsmanDetailState(
    val sportsmanUI: SportsmanUI,
    val imt: String,
    val sensorUI: SensorUI
) {
    companion object {
        val InitState = SportsmanDetailState(
            SportsmanUI.Default,
            "",
            SensorUI.Empty,
        )
    }
}