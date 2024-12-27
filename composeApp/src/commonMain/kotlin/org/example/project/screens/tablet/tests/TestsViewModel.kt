@file:OptIn(OrbitExperimental::class)

package org.example.project.screens.tablet.tests

import org.example.project.domain.model.test.TestUI
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class TestsViewModel : BaseScreenModel<TestsState, TestsEvent>(TestsState.InitState) {

    fun changeTests(value: TestUI) = intent {
        reduce {
            state.copy(
                selectTestUI = if (state.selectTestUI == value) null else value
            )
        }
    }

    fun changeSearch(value: String) = blockingIntent {
        reduce {
            state.copy(
                search = value
            )
        }
    }

}