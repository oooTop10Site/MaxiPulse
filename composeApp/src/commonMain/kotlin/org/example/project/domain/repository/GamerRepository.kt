package org.example.project.domain.repository

import org.example.project.domain.model.gameType.GameTypeUI
import org.example.project.domain.model.rank.RankUI
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.domain.model.trainingStage.TrainingStageUI
import org.example.project.platform.Either
import org.example.project.platform.Failure

interface GamerRepository {

    suspend fun getGamers(): Either<Failure, List<SportsmanUI>>
    suspend fun getGamersByGroupId(groupId: String): Either<Failure, List<SportsmanUI>>
    suspend fun getGamer(gamerId: String): Either<Failure, SportsmanUI>
    suspend fun editGamer(
        gamerId: String? = null,
        sportsmanUI: SportsmanUI
    ): Either<Failure, Unit>
    suspend fun deleteGamer(gamerId: String): Either<Failure, Unit>

    suspend fun getGameTypes(): Either<Failure, List<GameTypeUI>>
    suspend fun getRank(gameTypeId: String): Either<Failure, List<RankUI>>
    suspend fun getTrainingStage(gameTypeId: String): Either<Failure, List<TrainingStageUI>>
}