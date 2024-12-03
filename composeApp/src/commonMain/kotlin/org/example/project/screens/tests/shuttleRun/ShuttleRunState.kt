package org.example.project.screens.tests.shuttleRun

import kotlinx.datetime.LocalTime
import org.example.project.domain.model.test.ShuttleRunSportsmanUI
import org.example.project.domain.model.test.ShuttleRunStatus

data class ShuttleRunState(
    val isStart: Boolean,
    val time: Long,
    val isLeft: Boolean,
    val users: List<ShuttleRunSportsmanUI>
) {
    companion object {
        val InitState = ShuttleRunState(
            isLeft = false,
            users = listOf(
                ShuttleRunSportsmanUI("Ivan", "Ivanov", 75, ShuttleRunStatus.Chill()),
                ShuttleRunSportsmanUI("Petr", "Petrov", 80, ShuttleRunStatus.Running),
                ShuttleRunSportsmanUI("Semen", "Semenov", 65, ShuttleRunStatus.TestEnd),
                ShuttleRunSportsmanUI("Oleg", "Orlov", 70, ShuttleRunStatus.Running),
                ShuttleRunSportsmanUI("Dmitry", "Dmitriev", 78, ShuttleRunStatus.Chill()),
                ShuttleRunSportsmanUI("Sergey", "Sergeev", 68, ShuttleRunStatus.TestEnd),
                ShuttleRunSportsmanUI("Alexey", "Alekseev", 90, ShuttleRunStatus.Running),
                ShuttleRunSportsmanUI("Andrey", "Andreev", 85, ShuttleRunStatus.Chill()),
                ShuttleRunSportsmanUI("Roman", "Romanov", 72, ShuttleRunStatus.TestEnd),
                ShuttleRunSportsmanUI("Kirill", "Kirilov", 66, ShuttleRunStatus.Running),
                ShuttleRunSportsmanUI("Maxim", "Maximov", 77, ShuttleRunStatus.Chill()),
                ShuttleRunSportsmanUI("Egor", "Egorov", 83, ShuttleRunStatus.TestEnd),
                ShuttleRunSportsmanUI("Viktor", "Viktorov", 74, ShuttleRunStatus.Running),
                ShuttleRunSportsmanUI("Nikolay", "Nikolaev", 71, ShuttleRunStatus.Chill()),
                ShuttleRunSportsmanUI("Stepan", "Stepanov", 79, ShuttleRunStatus.TestEnd)
            ),
            time = 0,
            isStart = false
        )
    }
}