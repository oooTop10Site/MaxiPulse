package org.example.project.domain.model.rank

import org.example.project.domain.model.trainingStage.TrainingStageUI

data class RankUI(
    val id: String,
    val gameTypeId: String,
    val name: String,
) {
    companion object {
        val Default = RankUI("", "", "")
    }
}