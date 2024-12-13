package org.example.project.screens.training

import org.example.project.domain.model.sportsman.TrainingSportsmanUI
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

internal class TrainingViewModel :
    BaseScreenModel<TrainingState, TrainingEvent>(TrainingState.InitState) {

    fun changeIsTrimp() = intent {
        reduce {
            state.copy(
                isTrimp = !state.isTrimp
            )
        }
    }

    fun changeSelectSportsman(sportsmanUI: TrainingSportsmanUI? = null) = intent {
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