package org.example.project.screens.tablet.miniPulseWidget.contents.RpeScale

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.example.project.domain.model.log.EventType
import org.example.project.domain.model.log.EventUI
import org.example.project.domain.model.log.LogUI
import org.example.project.domain.model.rpe.RpeUI
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.ext.weekDates
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import kotlin.math.log

internal class RpeScaleViewModel: BaseScreenModel<RpeScaleState, RpeScaleEvent>(RpeScaleState.InitState) {

    fun loadTrainings() = intent { //todo
        reduce {
            state.copy(
                trainings = listOf(
                    LogUI(
                        datetime = LocalDateTime(2025, 3, 11, 10, 0),
                        sportsmanUI = SportsmanUI.Default.copy(
                            id = "1", number = 1, name = "Иван", lastname = "Иванов", middleName = "Иванович"
                        ),
                        event = EventUI("1", EventType.Training, "Утренняя тренировка"),
                        duration = 90L,
                        avgTrimp = 70,
                        sportsmen = List(10) { index ->
                            RpeUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = "s${index + 1}", number = index + 1,
                                    name = "Спортсмен${index + 1}", lastname = "Фамилия${index + 1}", middleName = "Отчество${index + 1}"
                                ),
                                score = (1..10).random()
                            )
                        }
                    ),
                    LogUI(
                        datetime = LocalDateTime(2025, 3, 14, 17, 30),
                        sportsmanUI = SportsmanUI.Default.copy(
                            id = "2", number = 2, name = "Петр", lastname = "Петров", middleName = "Петрович"
                        ),
                        event = EventUI("2", EventType.Training, "Вечерняя тренировка"),
                        duration = 120L,
                        avgTrimp = 80,
                        sportsmen = List(10) { index ->
                            RpeUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = "s${index + 11}", number = index + 11,
                                    name = "Спортсмен${index + 11}", lastname = "Фамилия${index + 11}", middleName = "Отчество${index + 11}"
                                ),
                                score = (1..10).random()
                            )
                        }
                    )
                )
            )
        }
    }

    fun selectTraining(logUI: LogUI) = intent {
        reduce {
            state.copy(
                selectTraining = logUI,
            )
        }
    }

    fun incrementWeek() = intent {
        state.selectWeek.lastOrNull()?.plus(DatePeriod(days = 7))?.let {
            reduce {
                state.copy(
                    selectWeek = it.weekDates(),
                )
            }
        }
    }

    fun decrementWeek() = intent {
        state.selectWeek.lastOrNull()?.minus(DatePeriod(days = 7))?.let {
            reduce {
                state.copy(
                    selectWeek = it.weekDates(),
                )
            }
        }
    }

    fun changeFilter(filter: String) = intent {
        reduce {
            state.copy(
                filter = filter
            )
        }
    }


}