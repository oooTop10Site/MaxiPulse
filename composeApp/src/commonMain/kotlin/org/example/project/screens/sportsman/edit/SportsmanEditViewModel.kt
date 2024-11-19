package org.example.project.screens.sportsman.edit

import org.example.project.domain.repository.GamerRepository
import org.example.project.platform.BaseScreenModel
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent

internal class SportsmanEditViewModel: BaseScreenModel<SportsmanEditState, SportsmanEditEvent>(
    SportsmanEditState.InitState
) {
    private val gamerRepository: GamerRepository by inject()

    fun loadSportsman(id: String?) = intent {
        if(!id.isNullOrBlank()) {
            launchOperation(
                operation = {
                    gamerRepository.getGamer(gamerId = id)
                },
                success = {
                    reduceLocal {
                        state.copy(
                            sportsmanUI = it
                        )
                    }
                }
            )
        }
    }

}