package org.example.project.screens.tablet.miniPulseWidget

import org.example.project.domain.model.widget.MinipulseWidgetTab
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class MiniPulseWidgetViewModel: BaseScreenModel<MiniPulseWidgetState, MiniPulseWidgetEvent>(
    MiniPulseWidgetState.InitState
) {

    fun changeTab(tab: MinipulseWidgetTab) = intent {
        reduce {
            state.copy(
                currentTab = tab
            )
        }
    }

}