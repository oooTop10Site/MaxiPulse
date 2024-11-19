package org.example.project.screens.sportsman.edit

import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanUI

data class SportsmanEditState(
    val sportsmanUI: SportsmanUI,
    val sensorUI: SensorUI
) {
    companion object {
        val InitState = SportsmanEditState(
            SportsmanUI.Default,
            SensorUI.Empty
        )
    }
}