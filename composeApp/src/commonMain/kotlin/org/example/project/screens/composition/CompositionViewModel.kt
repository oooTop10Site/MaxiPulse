package org.example.project.screens.composition

import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class CompositionViewModel :
    BaseScreenModel<CompositionState, CompositionEvent>(CompositionState.InitState) {

    @OptIn(OrbitExperimental::class)
    fun changeSearch(value: String) = blockingIntent {
        reduce {
            state.copy(
                search = value
            )
        }
    }

}