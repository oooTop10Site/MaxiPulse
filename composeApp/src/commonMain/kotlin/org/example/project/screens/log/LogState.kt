package org.example.project.screens.log

import org.example.project.domain.model.log.LogUI

data class LogState(
    val logs: List<LogUI>,
) {
    companion object {
        val InitState = LogState(
            emptyList()
        )
    }
}