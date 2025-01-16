package org.example.project.screens.tablet.sportsman.edit

import org.example.project.domain.model.composition.GroupUI
import org.example.project.domain.model.gameType.GameTypeUI
import org.example.project.domain.model.rank.RankUI
import org.example.project.domain.model.sportsman.SensorUI
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.domain.model.trainingStage.TrainingStageUI

data class SportsmanEditState(
    val sportsmanUI: SportsmanUI,
    val imt: String,
    val sensorUI: SensorUI?,
    val gameTypes: List<GameTypeUI>,
    val expandSex: Boolean,
    val expandSportStage: Boolean,
    val expandSportCategory: Boolean,
    val expandSport: Boolean,
    val sensorAlertDialog: Boolean,
    val expandGroup: Boolean,
    val expandCouch: Boolean,
    val groups: List<GroupUI>,
    val ranks: List<RankUI>,
    val sensors: List<SensorUI>?,
    val trainingStages: List<TrainingStageUI>,
    val settingChssDialog: Boolean,
    val deleteSportsmanDialog: Boolean,
) {
    companion object {
        val InitState = SportsmanEditState(
            SportsmanUI.Default,
            "",
            null,
            emptyList(),
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            emptyList(),
            emptyList(),
            null,
            emptyList(),
            false,
            false,
        )
    }
}