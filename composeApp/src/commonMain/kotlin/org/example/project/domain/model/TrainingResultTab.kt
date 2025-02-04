package org.example.project.domain.model

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.heart_rate_avg_max
import maxipuls.composeapp.generated.resources.sheet
import maxipuls.composeapp.generated.resources.stage_with_title
import maxipuls.composeapp.generated.resources.trimp
import org.example.project.domain.model.sportsman.SportsmanTrainingResultUI
import org.example.project.screens.tablet.training.trainingResult.TrainingResultState
import org.jetbrains.compose.resources.StringResource

sealed class TrainingResultTab(val text: StringResource) {
    object Sheet : TrainingResultTab(text = Res.string.sheet)
    object Trimp : TrainingResultTab(text = Res.string.trimp)
    object HeartRate : TrainingResultTab(text = Res.string.heart_rate_avg_max)
    class Stage(val data: List<SportsmanTrainingResultUI>, val title: String) :
        TrainingResultTab(text = Res.string.stage_with_title)

    companion object {
        val entries = listOf<TrainingResultTab>(
            TrainingResultTab.Sheet, TrainingResultTab.Trimp,
            TrainingResultTab.HeartRate
        )
    }
}