package org.example.project.screens.tablet.group.groupEdit

import org.example.project.domain.model.composition.GroupUI
import org.example.project.domain.model.sportsman.SportsmanUI

data class GroupEditState(
    val sportsmans: List<SportsmanUI>,
    val allSportsmans: List<SportsmanUI>,
    val deleteSportsman: List<String>,
    val groupUI: GroupUI,
    val search: String,
    val filteredSportsmans: List<SportsmanUI>,
    val deleteGroupAlert: Boolean,
) {
    companion object {
        val InitState = GroupEditState(
            sportsmans = emptyList(),
            deleteSportsman = emptyList(),
            search = "",
            groupUI = GroupUI.Default,
            filteredSportsmans = emptyList(),
            allSportsmans = emptyList(),
            deleteGroupAlert = false
        )
    }
}