package org.example.project.screens.training.trainingResult

import org.example.project.domain.model.TrainingResultTab
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

                SportsmanTrainingResultUI(
                    id = "sportsman_$index",
                    number = index + 1,
                    firstname = "Имя_$index",
                    lastname = "Фамилия_$index",
                    middleName = "Отчество_$index",
                    avatar = "https://example.com/avatar_$index.jpg",
                    age = randomAge,
                    kcal = Random.nextInt(500, 2000),
                    trimp = Random.nextInt(50, 300),
                    heartRateMax = Random.nextInt(170, 200),
                    heartRateMin = Random.nextInt(60, 90),
                    heartRateAvg = Random.nextInt(100, 160),
                    time = randomTime,
                    zone1 = 200,
                    zone2 = 300,
                    zone3 = 562,
                    zone4 = 562,
                    zone5 = 562,
                )
            }, null, TrainingResultTab.entries, TrainingResultTab.Sheet, ""
        )
    }
}