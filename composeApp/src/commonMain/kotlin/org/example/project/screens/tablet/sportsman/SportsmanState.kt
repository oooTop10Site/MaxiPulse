package org.example.project.screens.tablet.sportsman

import org.example.project.domain.model.sportsman.SportsmanUI

data class SportsmanState(
    val search: String,
    val isGrid: Boolean,
    val sportsmans: List<SportsmanUI>,
    val filterSportsmans: List<String>,
    val filterSportsman: String,
) {
    companion object {
        val InitState = SportsmanState(
            "", true, emptyList(), listOf("1", "2", "3"), ""
        )
    }
}