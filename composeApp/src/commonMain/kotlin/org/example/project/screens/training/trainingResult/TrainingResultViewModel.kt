package org.example.project.screens.training.trainingResult

import org.example.project.domain.model.TrainingResultTab
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class TrainingResultViewModel : BaseScreenModel<TrainingResultState, TrainingResultEvent>(
    TrainingResultState.InitState
) {

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
                    currentTab = trainingResultTab
                )
            }
        }
    }

}