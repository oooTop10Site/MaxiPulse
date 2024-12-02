package org.example.project.screens.tests.shuttleRun

import kotlinx.datetime.LocalTime
import org.example.project.domain.model.test.ShuttleRunSportsmanUI

data class ShuttleRunState(
    val time: LocalTime,
    val isLeft: Boolean,
    val users: List<ShuttleRunSportsmanUI>
) {
    companion object {
        val InitState = ShuttleRunState(
            isLeft = false,
            users = emptyList(),
            time = LocalTime(0, 0, 0)
        )
    }
}