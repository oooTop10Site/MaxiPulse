package org.example.project.screens.tablet.utp

import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class UtpViewModel: BaseScreenModel<UtpState, UtpEvent>(UtpState.InitState) {

    fun changeSelectStageOfReadiness(value: String) = intent {
        reduce {
            state.copy(
                selectStageOfReadiness = value
            )
        }
    }

    fun changeSelectPeriodOfReadiness(value: String) = intent {
        reduce {
            state.copy(
                selectPeriodOfReadiness = value
            )
        }
    }

    fun changeSelectYear(value: Int) = intent {
        reduce {
            state.copy(
                selectYear = value
            )
        }
    }

    fun changeSelectMicroCycle(value: String) = intent {
        reduce {
            state.copy(
                selectMicroCycle = value
            )
        }
    }

    fun changeSelectReadiness(value: String) = intent {
        reduce {
            state.copy(
                selectReadiness = value
            )
        }
    }

}