package org.example.project.screens.main

import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class MainViewModel : BaseScreenModel<MainState, MainEvent>(MainState.InitState) {

    @OptIn(OrbitExperimental::class)
    fun changeSearch(value: String) = blockingIntent {
        reduce {
            state.copy(
                search = value
            )
        }
    }

    fun changeSelect(sportsmanSensorUI: SportsmanSensorUI) = intent {
        reduce {
            state.copy(
                selectSportsmans = if (sportsmanSensorUI.id in state.selectSportsmans) state.selectSportsmans - sportsmanSensorUI.id else state.selectSportsmans + sportsmanSensorUI.id
            )
        }
    }

    fun changeIsActiveSensor() = intent {
        val newValue = !state.isActiveSensor
        reduce {
            state.copy(
                isActiveSensor = newValue,
                selectSportsmans = (if (newValue)
                    state.sportsmans.filter { it.sensorId.isNotEmpty() }.map { it.id }
                else state.selectSportsmans)
            )
        }
    }


}