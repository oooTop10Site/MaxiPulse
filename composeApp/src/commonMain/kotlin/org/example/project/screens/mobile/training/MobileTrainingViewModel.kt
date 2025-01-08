package org.example.project.screens.mobile.training

import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class MobileTrainingViewModel :
    BaseScreenModel<MobileTrainingState, MobileTrainingEvent>(MobileTrainingState.InitState) {

    fun changeIsStart() = intent {
        reduce {
            state.copy(
                isStart = !state.isStart
            )
        }
    }

}