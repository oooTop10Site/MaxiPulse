package org.example.project.screens.tablet.sportsman.detail

import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanUI

data class SportsmanDetailState(
    val sportsmanUI: SportsmanUI,
    val imt: String,
    val sensorUI: SensorUI?,
    val isOpenDialog: Boolean,
) {
    companion object {
        val InitState = SportsmanDetailState(
            SportsmanUI.Default,
            "",
            null,
            false
        )
    }
}