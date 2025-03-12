package org.example.project.screens.tablet.miniPulseWidget.contents.remoteTraining

import kotlinx.datetime.LocalDateTime
import org.example.project.domain.model.log.EventType
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.domain.model.training.RemoteTrainingSportsmanStatus
import org.example.project.domain.model.training.RemoteTrainingSportsmanUI
import org.example.project.domain.model.training.RemoteTrainingStatus
import org.example.project.domain.model.training.RemoteTrainingUI
import org.example.project.platform.randomUUID

data class RemoteTrainingState(
    val selectRemoteTraining: RemoteTrainingUI?,
    val tempCreateRemoteTrainingUI: RemoteTrainingUI?,
    val remoteTrainings: List<RemoteTrainingUI>,
    val sportsmen: List<SportsmanUI>
) {
    companion object {
        val InitState = RemoteTrainingState(
            selectRemoteTraining = null,
            tempCreateRemoteTrainingUI = null,
            remoteTrainings = emptyList(),
            sportsmen = emptyList()
        )
    }

}