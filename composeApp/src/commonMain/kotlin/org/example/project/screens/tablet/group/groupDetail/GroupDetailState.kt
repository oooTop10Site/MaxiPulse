package org.example.project.screens.tablet.group.groupDetail

import org.example.project.domain.model.composition.GroupUI
import org.example.project.domain.model.sportsman.SportsmanUI

data class GroupDetailState(
    val sportsmans: List<SportsmanUI>,
    val groupUI: GroupUI
) {
    companion object {
        val InitState = GroupDetailState(
            emptyList(), GroupUI.Default
        )
    }
}