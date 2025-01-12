package org.example.project.screens.tablet.training.trainingResult

import org.example.project.domain.model.TrainingResultTab
import org.example.project.domain.model.sportsman.HeartBit
import org.example.project.domain.model.sportsman.SportsmanSensorUI
import org.example.project.domain.model.sportsman.SportsmanTrainingResultUI
import org.example.project.ext.toSportsmanTrainingResultUI
import org.example.project.platform.BaseScreenModel
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
        reduce {
            state.copy(
                sportsmans = if (sportsmans.isEmpty()) { //todo чисто для теста, потом убрать. Это при переходе с LogScreen пустой массив, така как ничего не сохраняем
                    List(20) { index ->
                        val randomAge = Random.nextInt(18, 45)
                        val randomTime = Random.nextLong(3600, 7200) // Тренировка от 1 до 2 часов
                        val randomHeartRateMin = Random.nextInt(60, 90)
                        val randomHeartRateMax = Random.nextInt(170, 200)
                        val randomHeartRateAvg =
                            Random.nextInt(randomHeartRateMin + 10, randomHeartRateMax - 10)

                        SportsmanTrainingResultUI(
                            id = "sportsman_$index",
                            number = index + 1,
                            firstname = "Имя $index",
                            lastname = "Фамилия $index",
                            middleName = "Отчество $index",
                            avatar = "",
                            age = randomAge,
                            kcal = Random.nextInt(500, 2000),
                            trimp = Random.nextInt(50, 300).toDouble(),
                            heartRateMax = randomHeartRateMax,
                            heartRateMin = randomHeartRateMin,
                            heartRateAvg = randomHeartRateAvg,
                            time = randomTime,
                            zone1 = Random.nextLong(200, 400),
                            zone2 = Random.nextLong(300, 600),
                            zone3 = Random.nextLong(400, 800),
                            zone4 = Random.nextLong(300, 700),
                            zone5 = Random.nextLong(200, 500),
                            heartRate = List(20) { i ->
                                val interval = randomTime / 20 // Время между точками
                                HeartBit(
                                    mills = i * interval,
                                    value = Random.nextInt(randomHeartRateMin, randomHeartRateMax)
                                )
                            }
                        )
                    }
                } else sportsmans.map { it.toSportsmanTrainingResultUI() }
            )
        }
    }

    fun changeSearch(value: String) = intent {
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