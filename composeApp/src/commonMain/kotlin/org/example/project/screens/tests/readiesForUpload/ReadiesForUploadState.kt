package org.example.project.screens.tests.readiesForUpload

import org.example.project.domain.model.test.ShuttleRunSportsmanUI

data class ReadiesForUploadState(
    val isStart: Boolean,
    val time: Long,
    val users: List<ShuttleRunSportsmanUI>
) {
    companion object {
        val InitState = ReadiesForUploadState(
            isStart = false,
            time = 0,
            users = emptyList()
        )
    }
}
