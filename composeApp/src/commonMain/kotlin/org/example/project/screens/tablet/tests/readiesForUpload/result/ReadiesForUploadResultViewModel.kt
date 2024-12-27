package org.example.project.screens.tablet.tests.readiesForUpload.result

import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class ReadiesForUploadResultViewModel: BaseScreenModel<ReadiesForUploadResultState, ReadiesForUploadResultEvent>(
    ReadiesForUploadResultState.InitState
) {

    fun changeSearch(value: String) = intent {
        reduce {
            state.copy(
                search = value
            )
        }
    }

    fun changeSelect(value: String) = intent {
        reduce {
            state.copy(
                filter = value
            )
        }
    }

}