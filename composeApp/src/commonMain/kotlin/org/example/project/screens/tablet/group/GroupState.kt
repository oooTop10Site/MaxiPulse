package org.example.project.screens.tablet.group

import org.example.project.domain.model.composition.GroupUI

data class GroupState(
    val search: String,
    val isGrid: Boolean,
    val compositions: List<GroupUI>,
    val filterGroup: String,
    val filterGroups: List<String>,
) {
    companion object {
        val InitState = GroupState("", true, listOf(), "", listOf("1", "2", "3"))
    }
}