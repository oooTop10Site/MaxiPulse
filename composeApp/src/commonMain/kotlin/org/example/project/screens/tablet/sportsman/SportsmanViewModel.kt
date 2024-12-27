package org.example.project.screens.tablet.sportsman

import org.example.project.domain.manager.MessageObserverManager
import org.example.project.domain.repository.GamerRepository
import org.example.project.platform.BaseScreenModel
import org.koin.core.component.inject
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class SportsmanViewModel: BaseScreenModel<SportsmanState, SportsmanEvent>(SportsmanState.InitState) {

    private val gamerRepository: GamerRepository by inject()
    private val ibser: MessageObserverManager by inject()

    @OptIn(OrbitExperimental::class)
    fun changeSearch(value: String) = blockingIntent {
        reduce {
            state.copy(
                search = value
            )
        }
    }

    fun changeSportsman(value: String) = intent {
        reduce {
            state.copy(
                filterSportsman = value
            )
        }
    }

    fun changeIsGrid() = intent {
        reduce {
            state.copy(
                isGrid = !state.isGrid
            )
        }
    }

    fun loadSportmans() = intent {
        launchOperation(
            operation = {
                gamerRepository.getGamers()
            },
            success = {
                reduceLocal {
                    state.copy(sportsmans = it)
                }
            },
            failure = {
                println("put БЛЯЛЯЛАЛЛАЛАЛЧАЛЛА")
            }
        )
    }

}