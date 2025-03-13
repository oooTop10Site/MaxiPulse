package org.example.project.screens.tablet.miniPulseWidget.contents.RpeScale

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.example.project.ext.weekDates
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class RpeScaleViewModel: BaseScreenModel<RpeScaleState, RpeScaleEvent>(RpeScaleState.InitState) {

    fun loadTrainings() = intent {
        reduce {
            state.copy(
                trainings = emptyList()
            )
        }
    }

    fun loadRpies() = intent {
        reduce {
            state.copy(
                rpies = emptyList()
            )
        }
    }

    fun incrementWeek() = intent {
        state.selectWeek.lastOrNull()?.plus(DatePeriod(days = 7))?.let {
            reduce {
                state.copy(
                    selectWeek = it.weekDates(),
                )
            }
        }
    }

    fun decrementWeek() = intent {
        state.selectWeek.lastOrNull()?.minus(DatePeriod(days = 7))?.let {
            reduce {
                state.copy(
                    selectWeek = it.weekDates(),
                )
            }
        }
    }

    fun changeFilter(filter: String) = intent {
        reduce {
            state.copy(
                filter = filter
            )
        }
    }


}