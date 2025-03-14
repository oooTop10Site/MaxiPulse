package org.example.project.screens.tablet.log

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.domain.model.log.EventType
import org.example.project.domain.model.log.EventUI
import org.example.project.domain.model.log.LogUI
import org.example.project.domain.model.sportsman.SportsmanUI

data class LogState(
    val logs: List<LogUI>,
    val filterSportsman: String,
    val filterSportsmans: List<String>,
    val filterEvent: String,
    val filterEvents: List<String>,
    val filterComposition: String,
    val filterCompositions: List<String>,
) {
    companion object {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val sportsman = SportsmanUI(
            id = "1",
            number = 23,
            name = "Иван",
            lastname = "Иванов",
            middleName = "Иванович",
            age = 25,
            height = 180,
            weight = 75,
            avatar = "https://example.com/avatar.jpg",
            isMale = true,
            mpk = 50,
            imt = 23.15,
            chssPano = 150,
            chssPao = 160,
            chssMax = 200,
            chssResting = 60,
            birthDay = LocalDate(1998, 3, 15),
            gameTypeId = "game1",
            rankId = "rank1",
            groupId = "group1",
            trainigStageId = "stage1"
        )
        val InitState = LogState(
            List(20) { index ->
                LogUI(
                    datetime = now, // Уменьшаем дату на index дней
                    sportsmanUI = sportsman,
                    event = EventUI(
                        id = "event$index",
                        type = if (index % 2 == 0) EventType.Training else EventType.Championship,
                        title = if (index % 2 == 0) "Тренировка" else "Чемпионат"
                    ),
                    duration = (index + 1) * 600L, // Продолжительность в секундах (10 минут * index)
                    avgTrimp = 0,
                    sportsmen = emptyList()
                )
            },
            filterEvent = "",
            filterSportsman = "",
            filterComposition = "",
            filterEvents = listOf("1", "2", "3"),
            filterSportsmans = listOf("1", "2", "3"),
            filterCompositions = listOf("1", "2", "3"),
        )
    }
}