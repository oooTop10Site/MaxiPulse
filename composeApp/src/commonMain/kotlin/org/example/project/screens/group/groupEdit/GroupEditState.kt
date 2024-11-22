package org.example.project.screens.group.groupEdit

import org.example.project.domain.model.composition.GroupUI
import org.example.project.domain.model.sportsman.SportsmanUI

data class GroupEditState(
    val sportsmans: List<SportsmanUI>,
    val deleteSportsman: List<String>,
    val groupUI: GroupUI,
    val filteredSportsmans: List<SportsmanUI>
) {
    companion object {
        val InitState = GroupEditState(
            sportsmans = emptyList(),
            deleteSportsman = emptyList(),
            groupUI = GroupUI.Default,
            emptyList()
        )
    }
}