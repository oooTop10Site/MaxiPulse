package org.example.project.domain.model.rpe

import org.example.project.domain.model.sportsman.SportsmanUI

data class RpeUI(
    val sportsmanUI: SportsmanUI,
    val score: Int,
) {
    companion object {
        val Default = RpeUI(
            sportsmanUI = SportsmanUI.Default,
            score = 0
        )
    }
}