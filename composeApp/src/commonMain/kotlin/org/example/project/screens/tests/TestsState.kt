package org.example.project.screens.tests

data class TestsState(
    val tests: List<String>
) {
    companion object {
        val InitState = TestsState(
            emptyList()
        )
    }
}