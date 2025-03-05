package org.example.project.domain.model.training

import kotlinx.datetime.LocalDate
import org.example.project.domain.model.log.CriteriaUpload
import org.example.project.domain.model.log.EventType
import org.example.project.domain.model.trainingStage.TrainingStageUI
import org.example.project.platform.randomUUID

data class TrainingUtpUI(
    val id: String,
    val date: LocalDate,
    val typeOfEvent: EventType,
    val criteriaUpload: CriteriaUpload,
    val stages: List<TrainingUtpStageUI>,
    val expectValue: Float,
    val existValue: Float,
) {
    companion object {
        fun default(initDate: LocalDate): TrainingUtpUI {
            return TrainingUtpUI(
                id = randomUUID(),
                date = initDate,
                typeOfEvent = EventType.Training,
                criteriaUpload = CriteriaUpload.PercentByChssMax,
                stages = emptyList(),
                existValue = 0.3f,
                expectValue = 0.7f
            )
        }
    }
}

data class TrainingUtpStageUI(
    val id: String,
    val title: String,
    val min: Int,
    val value: Int
) {
    companion object {
        val Default = TrainingUtpStageUI(
            randomUUID(),
            "",
            0, 0
        )
    }
}