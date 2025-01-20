package org.example.project.screens.tablet.tests.shuttleRun.result

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.domain.model.ShuttleRunResultTab
import org.example.project.domain.model.sportsman.SportsmanShuttleRunResultUI
import org.example.project.platform.BaseScreenModel
import org.example.project.utils.Constants
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

@OptIn(OrbitExperimental::class)
internal class ShuttleRunResultViewModel :
    BaseScreenModel<ShuttleRunResultState, ShuttleRunResultEvent>(
        ShuttleRunResultState.InitState
    ) {

    fun loadResult(sportsmans: List<SportsmanShuttleRunResultUI>) = intent {
        reduce {
            state.copy(sportsmans = sportsmans, filterSportsmans = sportsmans, search = "")
        }
    }

    fun changeSelectSportsman(sportsmanUI: SportsmanShuttleRunResultUI) = intent {
        reduce {
            state.copy(
                currentTab = null, selectSportsman = sportsmanUI
            )
        }
    }

    fun changeSearch(value: String) = blockingIntent() {
        reduce {
            state.copy(
                search = value
            )
        }
    }

    var job: Job? = null
    fun search(value: String) = intent {
        job?.cancel()
        job = screenModelScope.launch {
            delay(Constants.Debounce)
            val queryWords = value.trim().lowercase().split(" ") // Разбиваем запрос на слова
            reduce {
                state.copy(
                    filterSportsmans = state.sportsmans.filter { sportman ->
                        val fio = sportman.fio.lowercase() // Приводим ФИО спортсмена к нижнему регистру
                        queryWords.all { word -> fio.contains(word) } // Проверяем, что все слова из запроса есть в ФИО
                    }
                )
            }
        }
    }

    fun changeDialog() = intent {
        reduce {
            state.copy(
                isDialog = !state.isDialog
            )
        }
    }

    fun changeSelect(filter: String) = intent {
        reduce {
            state.copy(
                filter = filter
            )
        }
    }

    fun changeTab(tab: ShuttleRunResultTab) = intent {
        if (state.currentTab != tab) {
            reduce {
                state.copy(
                    currentTab = tab, selectSportsman = null
                )
            }
        }
    }

}