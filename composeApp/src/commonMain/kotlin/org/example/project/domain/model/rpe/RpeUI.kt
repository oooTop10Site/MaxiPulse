package org.example.project.domain.model.rpe

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.domain.model.sportsman.SportsmanUI

data class RpeUI(
    val sportsmanUI: SportsmanUI,
    val score: Int,
    val localDateTime: LocalDateTime
) {
    companion object {
        val Default = RpeUI(
            sportsmanUI = SportsmanUI.Default,
            score = 0,
            localDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        )
    }
}