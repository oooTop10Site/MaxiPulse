package org.example.project.screens.tests.shuttleRun

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
            users = listOf(
                TestSportsmanUI("Ivan", "Ivanov", 75, TestStatus.Chill()),
                TestSportsmanUI("Petr", "Petrov", 80, TestStatus.Running),
                TestSportsmanUI("Semen", "Semenov", 65, TestStatus.TestEnd),
                TestSportsmanUI("Oleg", "Orlov", 70, TestStatus.Running),
                TestSportsmanUI("Dmitry", "Dmitriev", 78, TestStatus.Chill()),
                TestSportsmanUI("Sergey", "Sergeev", 68, TestStatus.TestEnd),
                TestSportsmanUI("Alexey", "Alekseev", 90, TestStatus.Running),
                TestSportsmanUI("Andrey", "Andreev", 85, TestStatus.Chill()),
                TestSportsmanUI("Roman", "Romanov", 72, TestStatus.TestEnd),
                TestSportsmanUI("Kirill", "Kirilov", 66, TestStatus.Running),
                TestSportsmanUI("Maxim", "Maximov", 77, TestStatus.Chill()),
                TestSportsmanUI("Egor", "Egorov", 83, TestStatus.TestEnd),
                TestSportsmanUI("Viktor", "Viktorov", 74, TestStatus.Running),
                TestSportsmanUI("Nikolay", "Nikolaev", 71, TestStatus.Chill()),
                TestSportsmanUI("Stepan", "Stepanov", 79, TestStatus.TestEnd)
            ),
            time = 0,
            isStart = false
        )
    }
}