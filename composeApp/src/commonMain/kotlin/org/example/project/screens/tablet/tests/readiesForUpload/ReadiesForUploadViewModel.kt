package org.example.project.screens.tablet.tests.readiesForUpload

import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

internal class ReadiesForUploadViewModel :
    BaseScreenModel<ReadiesForUploadState, ReadiesForUploadEvent>(
        ReadiesForUploadState.InitState
    ) {

    fun stop() = intent {
        reduce {
            state.copy(
                isStart = false
            )
        }
    }

    fun changeTest() = intent {
        reduce {
            state.copy(isStart = false)
        }
        val nextText = state.tests.getOrNull(state.tests.indexOf(state.currentTest) + 1)
        if (nextText != null) {
            reduce {
                state.copy(
                    currentTest = nextText,
                    time = 10L
                )
            }
        } else {
            postSideEffect(ReadiesForUploadEvent.Result)
        }
    }


    fun start() = intent {
        reduce {
            state.copy(
                isStart = true
            )
        }
    }

    fun decrementTime() = intent {
        val newTime = state.time -1
        if(state.isStart) {
            reduce {
                state.copy(
                    time = newTime
                )
            }

            if (newTime == 0L) {
                changeTest()
            }
        }
    }
}