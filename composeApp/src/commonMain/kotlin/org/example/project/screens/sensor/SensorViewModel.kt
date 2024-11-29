package org.example.project.screens.sensor

import kotlinx.coroutines.delay
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class SensorViewModel: BaseScreenModel<SensorState, SensorEvent>(SensorState.InitState) {

    fun changeIsGrid() = intent {
        reduce {
            state.copy(
                isGrid = !state.isGrid
            )
        }
    }

    fun loadSensors() = intent {
        reduce {
            state.copy(
                isLoading = true
            )
        }
        delay(7000L)
        reduce {
            state.copy(
                isLoading = false
            )
        }
    }

}