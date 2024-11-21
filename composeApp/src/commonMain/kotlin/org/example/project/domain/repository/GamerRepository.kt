package org.example.project.domain.repository

import org.example.project.domain.model.gameType.GameTypeUI
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.platform.Either
import org.example.project.platform.Failure

interface GamerRepository {

    suspend fun getGamers(): Either<Failure, List<SportsmanUI>>
    suspend fun getGamer(gamerId: String): Either<Failure, SportsmanUI>
    suspend fun editGamer(
        gamerId: String? = null,
        sportsmanUI: SportsmanUI
    ): Either<Failure, Unit>

    suspend fun getGameTypes(): Either<Failure, List<GameTypeUI>>

}