package org.example.project.screens.group

import org.example.project.domain.model.composition.GroupUI

data class GroupState (
    val search: String,
    val isGrid: Boolean,
    val compositions: List<GroupUI>
) {
    companion object {
        val InitState = GroupState("",true, listOf())
    }
}