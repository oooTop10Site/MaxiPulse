package org.example.project.screens.log

import kotlinx.datetime.LocalDateTime
import org.example.project.domain.model.log.EventType
import org.example.project.domain.model.log.EventUI
import org.example.project.domain.model.log.LogUI
import org.example.project.domain.model.sportsman.SportsmanUI

data class LogState(
    val logs: List<LogUI>,
) {
    companion object {
        val InitState = LogState(
           emptyList()
        )
    }
}