package org.example.project.screens.tablet.options.utp

import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.domain.model.AnalizeGraph
import org.example.project.domain.model.composition.GroupUI
import org.example.project.domain.model.log.CriteriaUpload
import org.example.project.domain.model.log.EventType
import org.example.project.domain.model.training.TrainingStageChssUI
import org.example.project.domain.model.training.TrainingUtpUI
import org.example.project.domain.model.trainingStage.TrainingStageUI
import org.example.project.domain.model.utp.DayUtpUI
import org.example.project.domain.model.utp.UTPTab
import org.example.project.ext.generateCalendarGrid
import org.example.project.ext.weekDates
import org.example.project.utils.Constants

data class UtpState(
    val years: List<Int>,
    val selectYear: Int,
    val mesocycle: List<String>,
    val selectMesocycle: String,
    val days: List<TrainingUtpUI>,
    val selectDay: DayOfWeek,
    val microCycle: List<String>,
    val selectMicroCycle: String,
    val readiness: List<String>,
    val selectReadiness: String,
    val utpTab: UTPTab,
    val tabs: List<UTPTab>,
    val groups: List<GroupUI>,
    val selectGroup: GroupUI?,
    val daysDate: List<TrainingUtpUI>,
    val selectedDay: TrainingUtpUI?,
    val currentDay: LocalDate,
    val events: List<EventType>,
    val criteriaUploads: List<CriteriaUpload>,
    val trainingStages: List<String>,
    val yearsReadies: List<String>,
    val isWeek: Boolean,
    val period: String,
    val periods: List<String>,
    val isCompareWithPlan: Boolean,
    val analizeGraphTabs: List<AnalizeGraph>,
    val selectAnalizeGraph: AnalizeGraph?,
    val currentWeek: List<LocalDate>,


    ) {
    companion object {
        val currentDate = Clock.System.now()
        val date = currentDate.toLocalDateTime(TimeZone.currentSystemDefault()).date

        val InitState = UtpState(
            groups = emptyList(),
            years = listOf(3, 4, 5, 6),
            mesocycle = emptyList(),
            days = emptyList(),
            selectedDay = null,
            daysDate = date.generateCalendarGrid().map { TrainingUtpUI.default(it) },
            selectDay = currentDate.toLocalDateTime(TimeZone.UTC).date.dayOfWeek,
            selectYear = 0,
            selectMesocycle = "",
            microCycle = listOf("1", "2", "3"),
            selectMicroCycle = "",
            selectReadiness = "",
            readiness = listOf(
                "Общеподготовительный",
                "Специально-подготовительный",
                "Предсоревновательный"
            ),
            utpTab = UTPTab.PannedUtp,
            tabs = UTPTab.entries,
            selectGroup = null,
            currentDay = date,
            events = EventType.entries,
            criteriaUploads = CriteriaUpload.entries,
            trainingStages = listOf(
                "Спортивно-оздоровительный этап",
                "Этап начальной подготовки",
                "Учебно-тренировочный этап",
                "Этап спортивного совершенствования"
            ),
            yearsReadies = listOf("ЭНП (3-6 год обучения)"),
            period = "",
            periods = emptyList<String>(),
            isWeek = false,
            isCompareWithPlan = false,
            analizeGraphTabs = AnalizeGraph.entries,
            selectAnalizeGraph = AnalizeGraph.MONOTONY,
            currentWeek = date.weekDates(),
        )
    }
}