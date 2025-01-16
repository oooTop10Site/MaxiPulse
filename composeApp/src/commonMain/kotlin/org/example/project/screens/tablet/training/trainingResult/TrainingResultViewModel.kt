package org.example.project.screens.tablet.training.trainingResult

import org.example.project.domain.model.TrainingResultTab
import org.example.project.domain.model.sportsman.HeartBit
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.sportsman.SportsmanTrainingResultUI
import org.example.project.ext.toSportsmanTrainingResultUI
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import kotlin.random.Random

internal class TrainingResultViewModel : BaseScreenModel<TrainingResultState, TrainingResultEvent>(
    TrainingResultState.InitState
) {

    fun changeSelectSportsman(sportsmanTrainingResultUI: SportsmanTrainingResultUI) = intent {
        reduce {
            state.copy(
                currentTab = null, selectSportsman = sportsmanTrainingResultUI
            )
        }
    }

    fun loadSportsman(sportsmans: List<SportsmanSensorUI>) = intent {
        reduce {
            state.copy(
                sportsmans = sportsmans.map { it.toSportsmanTrainingResultUI() }
            )
        }
    }

    fun changeSearch(value: String) = intent {
        reduce {
            state.copy(
                search = value
            )
        }
    }

    fun changeTab(trainingResultTab: TrainingResultTab) = intent {
        if (state.currentTab != trainingResultTab) {
            reduce {
                state.copy(
                    currentTab = trainingResultTab, selectSportsman = null
                )
            }
        }
    }

}