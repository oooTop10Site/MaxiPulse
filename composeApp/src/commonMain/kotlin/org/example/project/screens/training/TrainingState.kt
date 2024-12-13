package org.example.project.screens.training

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.Clock
import kotlinx.datetime.Clock.System
import org.example.project.domain.model.sportsman.HeartBit
import org.example.project.domain.model.sportsman.TrainingSportsmanUI

data class TrainingState(
    val isTrimp: Boolean,
    val isAlertDialog: Boolean,
    val durationSeconds: Long,
    val isStart: Boolean,
    val sportsmans: List<TrainingSportsmanUI>,
    val selectSportsman: TrainingSportsmanUI?,
) {
    companion object {
        val InitState = TrainingState(
            false, false, 0, false, sportsmans = List(20) { index ->
                TrainingSportsmanUI(
                    id = "id_$index",
                    number = index + 1,
                    firstname = "Firstname$index",
                    lastname = "Lastname$index",
                    middleName = "MiddleName$index",
                    avatar = "https://example.com/avatar_$index.png",
                    age = (18..40).random(),
                    kcal = (100..500).random(),
                    trimp = (50..150).random(),
                    heartRateMax = (150..200).random(),
                    heartRateMin = (60..90).random(),
                    heartBits = List(10) {
                        HeartBit(
                            mills = Clock.System.now().nanosecondsOfSecond.toLong(),
                            value = (60..200).random()
                        )
                    },
                    heartRateCurrent = (60..200).random(),
                    isTraining = listOf(true, false).random(),
                    color = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow).random()
                )
            },
            null
        )
    }
}