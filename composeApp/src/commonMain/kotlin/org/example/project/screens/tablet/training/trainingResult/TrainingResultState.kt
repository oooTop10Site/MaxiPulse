package org.example.project.screens.tablet.training.trainingResult

import org.example.project.domain.model.TrainingResultTab
import org.example.project.domain.model.sportsman.HeartBit
import org.example.project.domain.model.sportsman.SportsmanTrainingResultUI
import kotlin.random.Random

data class TrainingResultState(
    val sportsmans: List<SportsmanTrainingResultUI>,
    val selectSportsman: SportsmanTrainingResultUI?,
    val tabs: List<TrainingResultTab>,
    val currentTab: TrainingResultTab?,
    val search: String,
) {
    companion object {
        val InitState = TrainingResultState(
            List(20) { index ->
                val randomAge = Random.nextInt(18, 45)
                val randomTime = Random.nextLong(3600, 7200) // Тренировка от 1 до 2 часов
                val randomHeartRateMin = Random.nextInt(60, 90)
                val randomHeartRateMax = Random.nextInt(170, 200)
                val randomHeartRateAvg = Random.nextInt(randomHeartRateMin + 10, randomHeartRateMax - 10)

                SportsmanTrainingResultUI(
                    id = "sportsman_$index",
                    number = index + 1,
                    firstname = "Имя $index",
                    lastname = "Фамилия $index",
                    middleName = "Отчество $index",
                    avatar = "",
                    age = randomAge,
                    kcal = Random.nextInt(500, 2000),
                    trimp = Random.nextInt(50, 300),
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
            },
            selectSportsman = null,
            tabs = TrainingResultTab.entries,
            currentTab = TrainingResultTab.Sheet,
            search = ""
        )
    }
}
