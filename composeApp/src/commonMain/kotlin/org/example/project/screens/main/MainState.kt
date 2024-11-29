package org.example.project.screens.main

import org.example.project.domain.model.MainAlertDialog
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanSensorUI

data class MainState(
    val sportsmans: List<SportsmanSensorUI>,
    val selectSportsmans: List<String>,
    val search: String,
    val isStartTraining: Boolean,
    val isActiveSensor: Boolean,
    val alertDialog: MainAlertDialog?,
    val sensors: List<SensorUI>
) {
    companion object {
        val InitState = MainState(
            sportsmans = listOf(
            ),
            selectSportsmans = emptyList(),
            search = "",
            isStartTraining = false,
            isActiveSensor = false,
            alertDialog = null,
            sensors = listOf(

            )
        )
    }
}
