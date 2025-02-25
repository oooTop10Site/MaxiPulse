package org.example.project.screens.adaptive.main

import org.example.project.domain.model.MainAlertDialog
import org.example.project.domain.model.sportsman.SensorStatus
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.task.MainTaskUI

data class MainState(
    val sportsmans: List<SportsmanSensorUI>,
    val filterSportsmans: List<SportsmanSensorUI>,
    val selectSportsmans: List<String>,
    val search: String,
    val isStartTraining: Boolean,
    val isActiveSensor: Boolean,
    val alertDialog: MainAlertDialog?,
    val sensors: List<SensorUI>?,
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
            sensors = null,
            filterSportsmans = emptyList()
        )
    }
}
