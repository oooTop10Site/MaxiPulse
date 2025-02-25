package org.example.project.screens.tablet.log

import org.example.project.domain.model.log.LogUI
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class LogViewModel: BaseScreenModel<LogState, LogEvent>(LogState.InitState) {

    fun changeSportsman(value: String) = intent {
        reduce {
            state.copy(
                filterSportsman = value
            )
        }
    }

    fun deleteLog(deleteItem: LogUI) = intent {
        reduce {
            state.copy(
                logs = state.logs.filter { it != deleteItem }
            )
        }
    }

    fun changeEvent(value: String) = intent {
        reduce {
            state.copy(
                filterEvent = value
            )
        }
    }

    fun changeComposition(value: String) = intent {
        reduce {
            state.copy(
                filterComposition = value
            )
        }
    }

}