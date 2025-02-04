package org.example.project.screens.tablet.training

import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.sportsman.TrainingSportsmanUI
import org.example.project.domain.model.training.TrainingStageChssUI
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
    fun newDataFromSportsman(sensorUI: SensorUI, sportsmans: List<SportsmanSensorUI>) = intent {
        val newSportsmans = state.sportsmans.map { sportsman ->
            if (sportsman.sensor?.sensorId == sensorUI.sensorId && sportsman.isTraining) {
                sportsman.copy(
                    sensor = sensorUI.copy(
                        heartRate = sportsman.sensor.heartRate + sensorUI.heartRate
                    )
                )
            } else sportsman
        }
        reduce {
            state.copy(
                sportsmans = newSportsmans,
                selectSportsman = newSportsmans.find { it.id == state.selectSportsman?.id }
            )
        }
    }

    fun trainingStages(input: String) = intent {
        println(input)
        println(
            "TrainingStageChssUI.parseTrainingStages(input) - ${
                TrainingStageChssUI.parseTrainingStages(
                    input
                )
            }}"
        )
        reduce {
            state.copy(
                stages = TrainingStageChssUI.parseTrainingStages(input)
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
        val newTime = state.durationSeconds + 1
        reduce {
            state.copy(
                durationSeconds = newTime,
                currentStage = state.stages.let { stages ->
                    var temp: TrainingStageChssUI? = null
                    for (index in 0..stages.lastIndex) {
                        println("------------------------")
                        println(stages.subList(0, index + 1))
                        if (stages.subList(0, index + 1).sumOf { it.time * 60 } > newTime) {
                            temp = stages.getOrNull(index)
                            try {
                                if (stages.subList(0, index + 2).sumOf { it.time } * 60 > newTime) {
                                    return@let temp
                                }
                            } catch (e: Exception) {
                                println("message error - ${e.message}")
                            }
                        } else continue
                    }
                    temp
                }
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
            postSideEffect(TrainingEvent.StopTraining(state.sportsmans, state.stages))
        } else {
            reduce {
                state.copy(
                    sportsmans = state.sportsmans.map {
                        it.copy(
                            isTraining = true,
                            sensor = it.sensor?.copy(heartRate = emptyList())
                        )
                    },
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