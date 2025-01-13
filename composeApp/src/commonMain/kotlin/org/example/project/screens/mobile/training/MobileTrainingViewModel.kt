package org.example.project.screens.mobile.training

import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class MobileTrainingViewModel :
    BaseScreenModel<MobileTrainingState, MobileTrainingEvent>(MobileTrainingState.InitState) {

    fun changeIsStart() = intent {
        if(!state.isStart) {
            reduce {
                state.copy(
                    currentTraining = state.currentTraining.copy(
                        sensorUI = state.currentTraining.sensorUI.copy(
                            heartRate = emptyList()
                        )
                    )
                )
            }
        }
        reduce {
            state.copy(
                isStart = !state.isStart
            )
        }
    }

    fun changeConnectSensorDialog() = intent {
        reduce {
            state.copy(
                connectSensorDialog = !state.connectSensorDialog
            )
        }
    }

    fun incrementTime() = intent {
        reduce {
            state.copy(
                currentTraining = state.currentTraining.copy(
                    duration = state.currentTraining.duration + 1
                )
            )
        }
    }

    fun changeSelectSensor(sensorUI: SensorUI) = intent {
        println("state.selectSensor?.heartRate.orEmpty() - ${state.currentTraining.sensorUI}")
        println("sensorUI.heartRate - ${sensorUI}")
        reduce {
            state.copy(
                currentTraining = state.currentTraining.copy(
                    sensorUI = sensorUI.copy(
                        heartRate = state.currentTraining.sensorUI.heartRate + sensorUI.heartRate
                    )
                ),
            )
        }
    }

}