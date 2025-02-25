package org.example.project.domain.model.trainingStage

import kotlinx.serialization.SerialName

data class TrainingStageUI(
    val id: String,
    val gameTypeId: String,
    val name: String,
) {
    companion object {
        val Default = TrainingStageUI("", "", "")
    }
}