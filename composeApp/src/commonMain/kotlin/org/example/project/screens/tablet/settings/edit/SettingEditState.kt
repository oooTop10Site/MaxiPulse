package org.example.project.screens.tablet.settings.edit

import org.example.project.domain.model.setting.SettingTab
import org.example.project.domain.model.sportsman.SportsmanUI

data class SettingEditState(
    val sportsmanUI: SportsmanUI,
    val tabs: List<SettingTab>,
    val useHighPerformance: Boolean,
    val useRoute: Boolean,
    val city: String,
    val title: String,
    val desc: String,
) {
    companion object {
        val InitState = SettingEditState(
            sportsmanUI = SportsmanUI.Default,
            tabs = SettingTab.list,
            false,
            false,
            "",
            "",
            "",
        )
    }
}