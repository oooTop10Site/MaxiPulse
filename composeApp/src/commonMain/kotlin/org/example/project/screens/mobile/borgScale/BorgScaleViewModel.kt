package org.example.project.screens.mobile.borgScale

import org.example.project.domain.model.scaleBorg.ScaleBorg
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class BorgScaleViewModel :
    BaseScreenModel<BorgScaleState, BorgScaleEvent>(BorgScaleState.InitState) {

    fun selectItem(item: ScaleBorg) = intent {
        reduce {
            state.copy(
                selectItem = if (state.selectItem != item) item else null
            )
        }
    }

}