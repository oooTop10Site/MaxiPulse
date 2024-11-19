package org.example.project.data.repository

import org.example.project.data.api.MaxiPulseApi
import org.example.project.data.mapper.toUI
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.domain.repository.GamerRepository
import org.example.project.platform.Either
import org.example.project.platform.Failure
import org.example.project.platform.apiCall

class GamerRepositoryImpl(private val maxiPulseApi: MaxiPulseApi) : GamerRepository {
    override suspend fun getGamers(): Either<Failure, List<SportsmanUI>> {
        return apiCall(
            call = {
                maxiPulseApi.getSportsmans()
            },
            mapResponse = {
                it.data?.map { it.toUI() }.orEmpty()
            }
        )
    }

    override suspend fun getGamer(gamerId: String): Either<Failure, SportsmanUI> {
        return apiCall(
            call = {
                maxiPulseApi.getSportsmanById(id = gamerId)
            },
            mapResponse = {
                it.data?.toUI() ?: SportsmanUI.Default
            }
        )
    }
}