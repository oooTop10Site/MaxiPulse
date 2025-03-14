package org.example.project.screens.tablet.miniPulseWidget.contents.RpeScale

import kotlinx.datetime.LocalDate
import org.example.project.domain.model.log.LogUI
import org.example.project.domain.model.rpe.RpeUI
import org.example.project.ext.getCurrentWeekDates

data class RpeScaleState(
    val selectWeek: List<LocalDate>,
    val filter: String,
    val filters: List<String>,
    val trainings: List<LogUI>,
    val selectTraining: LogUI?,
) {

    companion object {
        val InitState = RpeScaleState(
            filter = "Сначала новые",
            filters = listOf("Сначала новые", "Сначала старые"),
            selectWeek = getCurrentWeekDates(),
            trainings = emptyList(),
            selectTraining = null,
        )
    }

}