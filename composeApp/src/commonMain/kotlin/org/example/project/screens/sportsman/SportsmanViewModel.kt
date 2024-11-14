package org.example.project.screens.sportsman

import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class SportsmanViewModel: BaseScreenModel<SportsmanState, SportsmanEvent>(SportsmanState.InitState) {

    @OptIn(OrbitExperimental::class)
    fun changeSearch(value: String) = blockingIntent {
        reduce {
            state.copy(
                search = value
            )
        }
    }

}