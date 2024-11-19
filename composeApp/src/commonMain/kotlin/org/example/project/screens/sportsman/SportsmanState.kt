package org.example.project.screens.sportsman

import org.example.project.domain.model.sportsman.SportsmanUI

data class SportsmanState(
    val search: String,
    val isGrid: Boolean,
    val sportsmans: List<SportsmanUI>
) {
    companion object {
        val InitState = SportsmanState(
            "", true, emptyList()
        )
    }
}