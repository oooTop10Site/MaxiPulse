package org.example.project.screens.tablet.training.trainingResult

import org.example.project.domain.model.TrainingResultTab
import org.example.project.domain.model.sportsman.HeartBit
import org.example.project.domain.model.sportsman.SportsmanTrainingResultUI
import kotlin.random.Random

data class TrainingResultState(
    val sportsmans: List<SportsmanTrainingResultUI>,
    val selectSportsman: SportsmanTrainingResultUI?,
    val filterSportmans: List<SportsmanTrainingResultUI>,
    val tabs: List<TrainingResultTab>,
    val currentTab: TrainingResultTab?,
    val search: String,
) {
    companion object {
        val InitState = TrainingResultState(
            emptyList(),
            selectSportsman = null,
            emptyList(),
            tabs = TrainingResultTab.entries,
            currentTab = TrainingResultTab.Sheet,
            search = ""
        )
    }
}
