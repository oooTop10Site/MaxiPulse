package org.example.project.domain.model.sensor

import org.example.project.domain.model.sportsman.SensorStatus
import org.example.project.domain.model.sportsman.SportsmanUI

data class SensorPreviewUI(
    val id: String,
    val name: String,
    val mac: String,
    val status: SensorStatus = SensorStatus.Active,
    val sportsmanUI: SportsmanUI
)