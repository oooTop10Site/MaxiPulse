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
            users = emptyList(),
            tests = ReadiesForUploadTests.entries,
            currentTest = ReadiesForUploadTests.entries.firstOrNull()?: ReadiesForUploadTests.LyingPosition
        )
    }
}
