package org.example.project.screens.tablet.utp

import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.domain.model.utp.DayUtpUI
import org.example.project.utils.Constants

data class UtpState(
    val stageOfReadiness: List<String>,
    val selectStageOfReadiness: String,
    val periodOfReadiness: List<String>,
    val selectPeriodOfReadiness: String,
    val years: List<Int>,
    val selectYear: Int,
    val mesocycle: List<String>,
    val selectMesocycle: String,
    val days: List<DayUtpUI>,
    val selectDay: DayOfWeek,
    val microCycle: List<String>,
    val selectMicroCycle: String,
    val readiness: List<String>,
    val selectReadiness: String,
) {
    companion object {
        val currentDate =Clock.System.now()

        val InitState = UtpState(
            stageOfReadiness = listOf(
                "Спортивно-оздоровительный этап",
                "Этап начальной подготовки",
                "Учебно-тренировочный этап",
                "Этап спортивного совершенствования"
            ),
            periodOfReadiness = listOf(
                "Подготовительный",
                "Соревновательный",
            ),
            years = listOf(3, 4, 5, 6),
            mesocycle = emptyList(),
            days = listOf(
                DayUtpUI(
                    day = DayOfWeek.MONDAY,
                    progressTraining = 35
                ),
                DayUtpUI(
                    day = DayOfWeek.TUESDAY,
                    progressTraining = 124
                ),
                DayUtpUI(
                    day = DayOfWeek.WEDNESDAY,
                    progressTraining = 42
                ),
                DayUtpUI(
                    day = DayOfWeek.THURSDAY,
                    progressTraining = 178
                ),
                DayUtpUI(
                    day = DayOfWeek.FRIDAY,
                    progressTraining = Constants.MAX_TRAINING_INTENSITY
                ),
                DayUtpUI(
                    day = DayOfWeek.SATURDAY,
                    progressTraining = 19
                ),
                DayUtpUI(
                    day = DayOfWeek.SUNDAY,
                    progressTraining = 0
                ),
            ),
            selectDay = currentDate.toLocalDateTime(TimeZone.UTC).date.dayOfWeek,
            selectYear = 0,
            selectMesocycle = "",
            selectStageOfReadiness = "",
            selectPeriodOfReadiness = "",
            microCycle = listOf("1", "2", "3"),
            selectMicroCycle = "",
            selectReadiness = "",
            readiness = listOf(
                "Общеподготовительный",
                "Специально-подготовительный",
                "Предсоревновательный"
            )
        )
    }
}