package org.example.project.screens.tablet.training.trainingResult

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.domain.model.TrainingResultTab
import org.example.project.domain.model.sportsman.HeartBit
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.sportsman.SportsmanTrainingResultUI
import org.example.project.domain.model.training.TrainingStageChssUI
import org.example.project.ext.minOf
import org.example.project.ext.toSportsmanTrainingResultUI
import org.example.project.platform.BaseScreenModel
import org.example.project.utils.Constants
import org.example.project.utils.orEmpty
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import kotlin.random.Random

internal class TrainingResultViewModel : BaseScreenModel<TrainingResultState, TrainingResultEvent>(
    TrainingResultState.InitState
) {

    fun changeSelectSportsman(sportsmanTrainingResultUI: SportsmanTrainingResultUI) = intent {
        reduce {
            state.copy(
                currentTab = null, selectSportsman = sportsmanTrainingResultUI
            )
        }
    }

    fun loadSportsman(sportsmans: List<SportsmanSensorUI>) = intent {
        val sportsmansMap = sportsmans.map { it.toSportsmanTrainingResultUI() }
        println("LOAD SPORTSMANS")
        reduce {
            state.copy(
                sportsmans = sportsmansMap,
                filterSportmans = sportsmansMap,
            )
        }
    }

    fun loadStage(sourceSportsmans: List<SportsmanSensorUI>, stages: List<TrainingStageChssUI>) =
        intent {
            val startTraining = sourceSportsmans.filter { it.sensor != null }
                .minOf(default = 0L) { it.sensor?.heartRate.orEmpty().minOf(default = 0) { it.mills } }
            reduce {
                state.copy(
                    tabs = state.tabs + stages.mapIndexed { index, item ->
                        TrainingResultTab.Stage(data = sourceSportsmans.map {
                            it.copy(
                                sensor = it.sensor?.copy(
                                    heartRate = it.sensor.heartRate.filter {
                                        it.mills - startTraining in
                                                stages.getOrNull(index - 1)?.time.orEmpty()*60000..stages[index].time*60000
                                    }
                                )
                            ).toSportsmanTrainingResultUI()
                        }, title = item.title)
                    }
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
                    filterSportmans = state.sportsmans.filter { sportman ->
                        val fio =
                            sportman.fio.lowercase() // Приводим ФИО спортсмена к нижнему регистру
                        queryWords.all { word -> fio.contains(word) } // Проверяем, что все слова из запроса есть в ФИО
                    }
                )
            }
        }
    }

    @OptIn(OrbitExperimental::class)
    fun changeSearch(value: String) = blockingIntent() {
        reduce {
            state.copy(
                search = value
            )
        }
    }

    fun changeTab(trainingResultTab: TrainingResultTab) = intent {
        if (state.currentTab != trainingResultTab) {
            reduce {
                state.copy(
                    currentTab = trainingResultTab, selectSportsman = null
                )
            }
        }
    }

}