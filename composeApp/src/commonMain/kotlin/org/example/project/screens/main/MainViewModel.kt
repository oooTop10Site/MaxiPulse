package org.example.project.screens.main

import org.example.project.domain.model.MainAlertDialog
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.test.TestUI
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

internal class MainViewModel : BaseScreenModel<MainState, MainEvent>(MainState.InitState) {

    @OptIn(OrbitExperimental::class)
    fun changeSearch(value: String) = blockingIntent {
        reduce {
            state.copy(
                search = value
            )
        }
    }

    fun changeIsStartTraining() = intent {
        reduce {
            state.copy(
                isStartTraining = !state.isStartTraining
            )
        }
    }

    fun changeAlertDialog(alertDialog: MainAlertDialog?) = intent {
        reduce {
            state.copy(
                alertDialog = alertDialog
            )
        }
    }

    fun startTraining(testUI: TestUI?) = intent {
        val alertDialog = when {
            state.sportsmans.find { it.sensor == null && it.id in state.selectSportsmans } != null -> MainAlertDialog.PlayerWithNoActiveSensor
            else -> null
        }
        reduce {
            state.copy(
                alertDialog = alertDialog
            )
        }
        if(alertDialog == null) {
            when(testUI) {
                TestUI.ReadiesForUpload -> {
                    postSideEffect(MainEvent.ReadiesForUpload)
                }
                TestUI.ShuttleRun -> {
                    postSideEffect(MainEvent.ShuttleRun)
                }
                null -> {
                    //todo
                }
            }
        }
    }

    fun changeSensorValidation(sensorUI: SensorUI, sportsmanId: String) = intent {
        val userAlreadyHasSensor =
            state.sportsmans.find { it.sensor?.sensorId == sensorUI.sensorId && it.id != sportsmanId }
        userAlreadyHasSensor?.let {
            reduce {
                state.copy(
                    alertDialog = MainAlertDialog.SensorAlreadyAssigned(
                        sensorUI = sensorUI,
                        sportsmanId = sportsmanId
                    ),
                )
            }
            return@intent
        }
        changeSensor(sensorUI, sportsmanId)
    }

    fun changeSensor(sensorUI: SensorUI, sportsmanId: String) = intent {
        reduce {
            state.copy(
                sportsmans = state.sportsmans.map {
                    when {
                        it.id == sportsmanId -> it.copy(
                            sensor = sensorUI
                        )

                        sensorUI.sensorId == it.sensor?.sensorId -> it.copy(sensor = null)

                        else -> it
                    }
                },
                alertDialog = null
            )
        }
    }

    fun changeSelect(sportsmanSensorUI: SportsmanSensorUI) = intent {
        reduce {
            state.copy(
                selectSportsmans = if (sportsmanSensorUI.id in state.selectSportsmans)
                    state.selectSportsmans - sportsmanSensorUI.id
                else state.selectSportsmans + sportsmanSensorUI.id
            )
        }
    }

    fun changeIsActiveSensor() = intent {
        val newValue = !state.isActiveSensor
        reduce {
            state.copy(
                isActiveSensor = newValue,
                selectSportsmans = (if (newValue)
                    state.sportsmans.filter { it.sensor != null }.map { it.id }
                else state.selectSportsmans)
            )
        }
    }


}