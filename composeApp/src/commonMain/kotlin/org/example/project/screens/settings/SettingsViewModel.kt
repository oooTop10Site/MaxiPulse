package org.example.project.screens.settings

import org.example.project.domain.model.setting.SettingTab
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class SettingsViewModel :
    BaseScreenModel<SettingsState, SettingsEvent>(SettingsState.InitState) {

    fun changeSelectTab(tab: SettingTab?) = intent {
        reduce {
            state.copy(
                selectTab = if (state.selectTab == tab) null else tab
            )
        }
    }

    fun changeHighPerformance() = intent {
        reduce {
            state.copy(useHighPerformance = !state.useHighPerformance)
        }
    }
    fun changeUseRoute() = intent {
        reduce {
            state.copy(useRoute = !state.useRoute)
        }
    }
}