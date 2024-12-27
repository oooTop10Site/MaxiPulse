package org.example.project.screens.tablet.tests.readiesForUpload

import org.example.project.domain.model.test.ReadiesForUploadTests
import org.example.project.domain.model.test.TestSportsmanUI
import org.example.project.domain.model.test.TestStatus

data class ReadiesForUploadState(
    val isStart: Boolean,
    val time: Long,
    val users: List<TestSportsmanUI>,
    val tests: List<ReadiesForUploadTests>,
    val currentTest: ReadiesForUploadTests
) {
    companion object {
        val InitState = ReadiesForUploadState(
            isStart = false,
            time = 10,
            users = listOf(
                TestSportsmanUI("Ivan", "Ivanov", 75, TestStatus.StandingPosition),
                TestSportsmanUI("Petr", "Petrov", 80, TestStatus.LyingPosition),
                TestSportsmanUI("Semen", "Semenov", 65, TestStatus.StandingPosition),
                TestSportsmanUI("Oleg", "Orlov", 70, TestStatus.LyingPosition),
                TestSportsmanUI("Dmitry", "Dmitriev", 78, TestStatus.StandingPosition),
                TestSportsmanUI("Sergey", "Sergeev", 68, TestStatus.LyingPosition),
                TestSportsmanUI("Alexey", "Alekseev", 90, TestStatus.StandingPosition),
                TestSportsmanUI("Andrey", "Andreev", 85, TestStatus.LyingPosition),
            ),
            tests = ReadiesForUploadTests.entries,
            currentTest = ReadiesForUploadTests.entries.firstOrNull()?: ReadiesForUploadTests.LyingPosition
        )
    }
}
