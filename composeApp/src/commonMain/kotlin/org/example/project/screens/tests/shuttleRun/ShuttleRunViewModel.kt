package org.example.project.screens.tests.shuttleRun

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalTime
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class ShuttleRunViewModel: BaseScreenModel<ShuttleRunState, ShuttleRunEvent>(ShuttleRunState.InitState) {

    fun start() = intent {
        reduce {
            state.copy(
                isStart = true
            )
        }
    }

    fun incrementTime() = intent {
        reduce {
            state.copy(
                time = state.time + 1
            )
        }
    }


    fun stop() = intent {
        reduce {
            state.copy(
                isStart = false
            )
        }
    }

}