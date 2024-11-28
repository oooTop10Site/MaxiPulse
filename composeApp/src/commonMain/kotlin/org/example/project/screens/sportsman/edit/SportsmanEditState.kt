package org.example.project.screens.sportsman.edit

import org.example.project.domain.model.composition.GroupUI
import org.example.project.domain.model.gameType.GameTypeUI
import org.example.project.domain.model.rank.RankUI
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.domain.model.trainingStage.TrainingStageUI

data class SportsmanEditState(
    val sportsmanUI: SportsmanUI,
    val imt: String,
    val sensorUI: SensorUI,
    val gameTypes: List<GameTypeUI>,
    val expandSex: Boolean,
    val expandSportStage: Boolean,
    val expandSportCategory: Boolean,
    val expandSport: Boolean,
    val expandGroup: Boolean,
    val expandCouch: Boolean,
    val groups: List<GroupUI>,
    val ranks: List<RankUI>,
    val trainingStages: List<TrainingStageUI>,
) {
    companion object {
        val InitState = SportsmanEditState(
            SportsmanUI.Default,
            "",
            SensorUI.Empty,
            emptyList(),
            false,
            false,
            false,
            false,
            false,
            false,
            emptyList(),
            emptyList(),
            emptyList()
        )
    }
}