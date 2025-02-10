package org.example.project.domain.model.log

import kotlinx.datetime.LocalDateTime
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.ext.toTimeUI
import org.example.project.ext.toUI
import kotlin.time.Duration

data class LogUI(
    val datetime: LocalDateTime,
    val sportsmanUI: SportsmanUI,
    val event: EventUI,
    val duration: Long,
) {
    val dateText: String
        get() = datetime.date.toUI()

    val timeText: String
        get() = datetime.time.toUI()

    val durationText: String
        get() = duration.toTimeUI()

}
data class EventUI (
    val id: String,
    val type: EventType,
    val title: String,
)

enum class EventType(val title: String) {
    Training("Тренеровка"),
    Championship("Чемпионат")
}

enum class CriteriaUpload(val title: String, value: Int) {
    PercentByChssMax("Процент от ЧСС max", 0),
}