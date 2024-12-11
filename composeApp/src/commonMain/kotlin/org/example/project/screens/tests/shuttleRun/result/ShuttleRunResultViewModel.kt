package org.example.project.screens.tests.shuttleRun.result

import org.example.project.domain.model.ShuttleRunResultTab
import org.example.project.domain.model.TrainingResultTab
import org.example.project.domain.model.sportsman.SportsmanShuttleRunResultUI
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class ShuttleRunResultViewModel: BaseScreenModel<ShuttleRunResultState, ShuttleRunResultEvent>(
    ShuttleRunResultState.InitState
) {
    fun changeSelectSportsman(sportsmanUI: SportsmanShuttleRunResultUI) = intent {
        reduce {
            state.copy(
                currentTab = null, selectSportsman = sportsmanUI
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

    fun changeSelect(filter: String) = intent {
        reduce {
            state.copy(
                filter = filter
            )
        }
    }

    fun changeTab(tab: ShuttleRunResultTab) = intent {
        if (state.currentTab != tab) {
            reduce {
                state.copy(
                    currentTab = tab, selectSportsman = null
                )
            }
        }
    }

}