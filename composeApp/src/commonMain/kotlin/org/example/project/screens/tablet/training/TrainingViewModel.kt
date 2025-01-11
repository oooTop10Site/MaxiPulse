package org.example.project.screens.tablet.training

import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.sportsman.TrainingSportsmanUI
import org.example.project.platform.BaseScreenModel
import org.example.project.platform.ScanBluetoothSensorsManager
import org.example.project.platform.permission.service.PermissionsService
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

internal class TrainingViewModel :
    BaseScreenModel<TrainingState, TrainingEvent>(TrainingState.InitState) {
    val permissionService: PermissionsService by inject()
    val scanBluetoothSensorsManager: ScanBluetoothSensorsManager by inject()
    fun newDataFromSportsman(sensorUI: SensorUI) = intent {
        println("---------------")
        println("зашли в newDataFromSportsman")
        reduce {
            state.copy(
                sportsmans = state.sportsmans.map { sportsman ->
                    println("sportsman.sensor?.sensorId - ${sportsman.sensor?.sensorId}")
                    println("sensorUI.sensorId  - ${sensorUI.sensorId}")
                    if (sportsman.sensor?.sensorId == sensorUI.sensorId && sportsman.isTraining) {
                        println("Нашли нащего спорстмена")
                        sportsman.copy(
                            sensor = sensorUI.copy(
                                heartRate = sportsman.sensor.heartRate + sensorUI.heartRate
                            )
                        )
                    } else sportsman
                }
            )
        }
    }

    fun changeIsTrimp() = intent {
        reduce {
            state.copy(
                isTrimp = !state.isTrimp
            )
        }
    }

    fun loadSportsman(sportsmans: List<SportsmanSensorUI>) = intent {
        reduce {
            state.copy(
                sportsmans = sportsmans
            )
        }
    }

    fun changeSelectSportsman(sportsmanUI: SportsmanSensorUI? = null) = intent {
        reduce {
            state.copy(
                selectSportsman = sportsmanUI
            )
        }
    }

    fun incrementTime() = intent {
        reduce {
            state.copy(
                durationSeconds = state.durationSeconds + 1
            )
        }
    }

    fun changeIsStart() = intent {
        if (state.isStart) {
            reduce {
                state.copy(
                    sportsmans = state.sportsmans.map { it.copy(isTraining = false) },
                    isStart = false
                )
            }
            postSideEffect(TrainingEvent.StopTraining)
        } else {
            reduce {
                state.copy(
                    sportsmans = state.sportsmans.map { it.copy(isTraining = true) },
                    isStart = true
                )
            }
        }
    }

    fun changeIsAlertDialog() = intent {
        reduce {
            state.copy(
                isAlertDialog = !state.isAlertDialog
            )
        }
    }

    fun stopTrainingSportsman(id: String) = intent {
        reduce {
            state.copy(
                sportsmans = state.sportsmans.map {
                    if (it.id == id) {
                        it.copy(
                            isTraining = false
                        )
                    } else it
                }
            )
        }
    }

}