package org.example.project.screens.tablet.miniPulseWidget

import org.example.project.domain.model.widget.MinipulseWidgetTab

data class MiniPulseWidgetState(
    val tabs: List<MinipulseWidgetTab>,
    val currentTab: MinipulseWidgetTab,
) {
    companion object {
        val InitState = MiniPulseWidgetState(
            tabs = MinipulseWidgetTab.entries,
            currentTab = MinipulseWidgetTab.MorningRecovery
        )
    }
}