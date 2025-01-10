package org.example.project.screens.mobile.training

import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class MobileTrainingViewModel :
    BaseScreenModel<MobileTrainingState, MobileTrainingEvent>(MobileTrainingState.InitState) {

    fun changeIsStart() = intent {
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

    fun changeSelectSensor(sensorUI: SensorUI) = intent {
        println("state.selectSensor?.heartRate.orEmpty() - ${state.selectSensor}")
        println("sensorUI.heartRate - ${sensorUI}")
        reduce {
            state.copy(
                selectSensor = sensorUI.copy(heartRate = state.selectSensor?.heartRate.orEmpty() + sensorUI.heartRate),
            )
        }
    }

}