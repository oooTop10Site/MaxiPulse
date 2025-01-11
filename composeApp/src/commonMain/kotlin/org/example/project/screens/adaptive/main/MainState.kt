package org.example.project.screens.adaptive.main

import org.example.project.domain.model.MainAlertDialog
import org.example.project.domain.model.sportsman.SensorStatus
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.task.MainTaskUI

data class MainState(
    val sportsmans: List<SportsmanSensorUI>,
    val selectSportsmans: List<String>,
    val search: String,
    val isStartTraining: Boolean,
    val isActiveSensor: Boolean,
    val alertDialog: MainAlertDialog?,
    val sensors: List<SensorUI>,
    //mobile
    val avatar: String,
    val task: List<MainTaskUI>,
    val currentTask: MainTaskUI,
    val name: String,
    val lastname: String,
    val middleName: String,
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

            ),
            avatar = "",
            name = "Иван",
            lastname = "Иванов",
            middleName = "Иванович",
            task = listOf(
                MainTaskUI.BorgScale,
                MainTaskUI.Training,
                MainTaskUI.GoodMorning,
                MainTaskUI.GoodMorning,
                MainTaskUI.GoodMorning,
                MainTaskUI.GoodMorning,
            ),
            currentTask = MainTaskUI.GoodMorning
        )
    }
}
