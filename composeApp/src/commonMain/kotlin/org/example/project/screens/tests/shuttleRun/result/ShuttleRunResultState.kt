package org.example.project.screens.tests.shuttleRun.result

import org.example.project.domain.model.ShuttleRunResultTab
import org.example.project.domain.model.TrainingResultTab
import org.example.project.domain.model.sportsman.Performance
import org.example.project.domain.model.sportsman.SportsmanShuttleRunResultUI
import org.example.project.domain.model.sportsman.SportsmanTrainingResultUI
import kotlin.random.Random

data class ShuttleRunResultState(
    val sportsmans: List<SportsmanShuttleRunResultUI>,
    val selectSportsman: SportsmanShuttleRunResultUI?,
    val tabs: List<ShuttleRunResultTab>,
    val currentTab: ShuttleRunResultTab?,
    val search: String,
    val filter: String,
    val filters: List<String>,
) {

    companion object {
        val InitState = ShuttleRunResultState(
            search = "",
            filter = "первый фильтр",
            filters = listOf("первый фильтр", "второй фильтр", "третий фильтр"),
            sportsmans = List(20) { index ->
                SportsmanShuttleRunResultUI(
                    id = "sportsman_$index",
                    number = index + 1,
                    firstname = "Имя_$index",
                    lastname = "Фамилия_$index",
                    middleName = "Отчество_$index",
                    avatar = "https://example.com/avatar_$index.jpg",
                    age = Random.nextInt(18, 45),
                    distance = Random.nextLong(1000, 3000), // Дистанция от 1 км до 3 км
                    heartRateMax = Random.nextInt(170, 200),
                    chssPano = Random.nextInt(140, 160),
                    chssPao = Random.nextInt(120, 140),
                    mpk = Random.nextInt(40, 60),
                    performance = when (index % 3) {
                        0 -> Performance.Max
                        1 -> Performance.Min
                        2 -> Performance.Avg
                        else -> Performance.Max
                    },
                    time = Random.nextInt(40, 280).toLong()
                )
            },
            selectSportsman = null,
            tabs = ShuttleRunResultTab.entries,
            currentTab = ShuttleRunResultTab.OverallResult
        )
    }
}