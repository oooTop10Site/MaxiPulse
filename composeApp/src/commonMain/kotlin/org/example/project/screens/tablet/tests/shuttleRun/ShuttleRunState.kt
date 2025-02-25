package org.example.project.screens.tablet.tests.shuttleRun

import org.example.project.domain.model.test.TestSportsmanUI
import org.example.project.domain.model.test.TestStatus

data class ShuttleRunState(
    val isStart: Boolean,
    val time: Long,
    val isLeft: Boolean,
    val users: List<TestSportsmanUI>
) {
    companion object {
        val InitState = ShuttleRunState(
            isLeft = false,
            users = emptyList(),
            time = 0,
            isStart = false
        )
    }
}