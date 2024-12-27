package org.example.project.screens.tablet.settings

import org.example.project.domain.model.setting.SettingTab
import org.example.project.domain.model.sportsman.SportsmanUI

data class SettingsState(
    val sportsmanUI: SportsmanUI,
    val selectTab: SettingTab?,
    val tabs: List<SettingTab>,
    val useHighPerformance: Boolean,
    val useRoute: Boolean,
) {
    companion object {
        val InitState = SettingsState(
            sportsmanUI = SportsmanUI.Default,
            selectTab = null,
            tabs = SettingTab.list,
            false,
            false,
        )
    }
}