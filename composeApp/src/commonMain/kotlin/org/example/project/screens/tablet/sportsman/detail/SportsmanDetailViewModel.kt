@file:OptIn(OrbitExperimental::class)

package org.example.project.screens.tablet.sportsman.detail

import org.example.project.domain.repository.GamerRepository
import org.example.project.platform.BaseScreenModel
import org.example.project.utils.toStringWithCondition
import org.koin.core.component.inject
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class SportsmanDetailViewModel : BaseScreenModel<SportsmanDetailState, SportsmanDetailEvent>(
    SportsmanDetailState.InitState
) {
    private val gamerRepository: GamerRepository by inject()

    fun changeDialog() = intent {
        reduce {
            state.copy(
                isOpenDialog = !state.isOpenDialog
            )
        }
    }

    fun loadSportsman(id: String?) = intent {
        if (!id.isNullOrBlank()) {
            launchOperation(
                operation = {
                    gamerRepository.getGamer(gamerId = id)
                },
                success = {
                    reduceLocal {
                        state.copy(
                            sportsmanUI = it,
                            imt = it.imt.toStringWithCondition()
                        )
                    }
                }
            )
        }
    }

}