package org.example.project.data.mapper

import org.example.project.data.model.trainingStage.TrainingStageResponse
import org.example.project.domain.model.trainingStage.TrainingStageUI

fun TrainingStageResponse.toUI(): TrainingStageUI {
    return TrainingStageUI(
        id.orEmpty(), gameTypeId.orEmpty(), name.orEmpty()
    )
}