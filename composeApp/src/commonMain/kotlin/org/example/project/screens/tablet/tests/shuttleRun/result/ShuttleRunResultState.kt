package org.example.project.screens.tablet.tests.shuttleRun.result

import org.example.project.domain.model.ShuttleRunResultTab
import org.example.project.domain.model.sportsman.Performance
import org.example.project.domain.model.sportsman.SportsmanShuttleRunResultUI
import kotlin.random.Random

data class ShuttleRunResultState(
    val sportsmans: List<SportsmanShuttleRunResultUI>,
    val selectSportsman: SportsmanShuttleRunResultUI?,
    val filterSportsmans: List<SportsmanShuttleRunResultUI>,
    val tabs: List<ShuttleRunResultTab>,
    val currentTab: ShuttleRunResultTab?,
    val search: String,
    val filter: String,
    val filters: List<String>,
    val isDialog: Boolean,
) {

    companion object {
        val InitState = ShuttleRunResultState(
            search = "",
            filter = "первый фильтр",
            filters = listOf("первый фильтр", "второй фильтр", "третий фильтр"),
            sportsmans = emptyList(),
            selectSportsman = null,
            tabs = ShuttleRunResultTab.entries,
            currentTab = ShuttleRunResultTab.OverallResult,
            isDialog = false,
            filterSportsmans = emptyList()
        )
    }
}