package org.example.project.data.model.trainingStage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class TrainingStageResponse(
    val id: String?,
    @SerialName("game_type_id")
    val gameTypeId: String?,
    val name: String?,
)