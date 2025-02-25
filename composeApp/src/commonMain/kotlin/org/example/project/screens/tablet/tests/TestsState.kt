package org.example.project.screens.tablet.tests

import org.example.project.domain.model.test.TestUI

data class TestsState(
    val tests: List<TestUI>,
    val search: String,
    val selectTestUI: TestUI?
) {
    companion object {
        val InitState = TestsState(
            TestUI.entries, "", null
        )
    }
}