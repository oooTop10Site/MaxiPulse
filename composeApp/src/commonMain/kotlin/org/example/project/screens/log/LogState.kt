package org.example.project.screens.log

import kotlinx.datetime.LocalDateTime
import org.example.project.domain.model.log.EventType
import org.example.project.domain.model.log.EventUI
import org.example.project.domain.model.log.LogUI
import org.example.project.domain.model.sportsman.SportsmanUI

data class LogState(
    val logs: List<LogUI>,
) {
    companion object {
        val InitState = LogState(
            List(20) { index ->
                LogUI(
                    datetime = LocalDateTime(2024, 11, index + 1, 10 + (index % 3), 15),
                    sportsmanUI = SportsmanUI(
                        id = "sportsman-$index",
                        number = "SP${100 + index}",
                        name = "Имя$index",
                        lastname = "Фамилия$index",
                        middleName = "Отчество$index",
                        age = 15 + (index % 10),
                        height = 160 + (index % 10),
                        weight = 50 + (index % 5),
                    ),
                    event = EventUI(
                        id = "event-$index",
                        type = if (index % 2 == 0) EventType.Training else EventType.Championship,
                        title = if (index % 2 == 0) "Конькобежцы 13-15 лет" else "Чемпионат города $index",
                    ),
                    duration = 60L * (30 + index) // минуты в миллисекундах
                )
            }
        )
    }
}