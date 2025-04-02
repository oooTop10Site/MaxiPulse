package org.example.project.screens.tablet.miniPulseWidget.contents.RpeScale

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.example.project.domain.model.log.EventType
import org.example.project.domain.model.log.EventUI
import org.example.project.domain.model.log.LogUI
import org.example.project.domain.model.log.RpeLogUI
import org.example.project.domain.model.rpe.RpeUI
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.ext.weekDates
import org.example.project.platform.BaseScreenModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import kotlin.math.log

internal class RpeScaleViewModel :
    BaseScreenModel<RpeScaleState, RpeScaleEvent>(RpeScaleState.InitState) {

    fun loadTrainings() = intent { //todo
        reduce {
            state.copy(
                trainings = listOf(
                    RpeLogUI(
                        datetime = LocalDateTime(2025, 3, 17, 10, 0),
                        event = EventUI("1", EventType.Training, "Тренировка 1"),
                        duration = 90L,
                        avgTrimp = 70,
                        sportsmen = List(10) { index ->
                            RpeUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = "s${index + 1}",
                                    number = index + 1,
                                    name = "Спортсмен${index + 1}",
                                    lastname = "Фамилия${index + 1}",
                                    middleName = "Отчество${index + 1}"
                                ),
                                score = (1..10).random(),
                                localDateTime = LocalDateTime(2025, 3, 17, 10, 0)
                            )
                        }
                    ),
                    RpeLogUI(
                        datetime = LocalDateTime(2025, 3, 18, 15, 0),
                        event = EventUI("2", EventType.Training, "Тренировка 2"),
                        duration = 100L,
                        avgTrimp = 75,
                        sportsmen = List(10) { index ->
                            RpeUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = "s${index + 1}",
                                    number = index + 1,
                                    name = "Спортсмен${index + 1}",
                                    lastname = "Фамилия${index + 1}",
                                    middleName = "Отчество${index + 1}"
                                ),
                                score = (1..10).random(),
                                localDateTime = LocalDateTime(2025, 3, 18, 15, 0)
                            )
                        }
                    ),
                    RpeLogUI(
                        datetime = LocalDateTime(2025, 3, 19, 18, 0),
                        event = EventUI("3", EventType.Training, "Тренировка 3"),
                        duration = 110L,
                        avgTrimp = 80,
                        sportsmen = List(10) { index ->
                            RpeUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = "s${index + 1}",
                                    number = index + 1,
                                    name = "Спортсмен${index + 1}",
                                    lastname = "Фамилия${index + 1}",
                                    middleName = "Отчество${index + 1}"
                                ),
                                score = (1..10).random(),
                                localDateTime = LocalDateTime(2025, 3, 19, 18, 0)
                            )
                        }
                    ),
                    RpeLogUI(
                        datetime = LocalDateTime(2025, 3, 20, 9, 30),
                        event = EventUI("4", EventType.Training, "Тренировка 4"),
                        duration = 90L,
                        avgTrimp = 72,
                        sportsmen = List(10) { index ->
                            RpeUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = "s${index + 1}",
                                    number = index + 1,
                                    name = "Спортсмен${index + 1}",
                                    lastname = "Фамилия${index + 1}",
                                    middleName = "Отчество${index + 1}"
                                ),                                score = (1..10).random(),
                                localDateTime = LocalDateTime(2025, 3, 20, 9, 30)
                            )
                        }
                    ),
                    RpeLogUI(
                        datetime = LocalDateTime(2025, 3, 21, 16, 0),
                        event = EventUI("5", EventType.Training, "Тренировка 5"),
                        duration = 120L,
                        avgTrimp = 85,
                        sportsmen = List(10) { index ->
                            RpeUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = "s${index + 1}",
                                    number = index + 1,
                                    name = "Спортсмен${index + 1}",
                                    lastname = "Фамилия${index + 1}",
                                    middleName = "Отчество${index + 1}"
                                ),                                score = (1..10).random(),
                                localDateTime = LocalDateTime(2025, 3, 21, 16, 0)
                            )
                        }
                    ),
                    RpeLogUI(
                        datetime = LocalDateTime(2025, 3, 22, 11, 0),
                        event = EventUI("6", EventType.Training, "Тренировка 6"),
                        duration = 105L,
                        avgTrimp = 78,
                        sportsmen = List(10) { index ->
                            RpeUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = "s${index + 1}",
                                    number = index + 1,
                                    name = "Спортсмен${index + 1}",
                                    lastname = "Фамилия${index + 1}",
                                    middleName = "Отчество${index + 1}"
                                ),                                score = (1..10).random(),
                                localDateTime = LocalDateTime(2025, 3, 22, 11, 0)
                            )
                        }
                    ),
                    RpeLogUI(
                        datetime = LocalDateTime(2025, 3, 23, 14, 30),
                        event = EventUI("7", EventType.Training, "Тренировка 7"),
                        duration = 95L,
                        avgTrimp = 74,
                        sportsmen = List(10) { index ->
                            RpeUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = "s${index + 1}",
                                    number = index + 1,
                                    name = "Спортсмен${index + 1}",
                                    lastname = "Фамилия${index + 1}",
                                    middleName = "Отчество${index + 1}"
                                ),                                score = (1..10).random(),
                                localDateTime = LocalDateTime(2025, 3, 23, 14, 30)
                            )
                        }
                    )
                )

            )
        }
    }

    fun selectTraining(rpeLogUI: RpeLogUI) = intent {
        reduce {
            state.copy(
                selectTraining = rpeLogUI,
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

    fun selectSportsman(sportsmanId: String) = intent {
        reduce {
            state.copy(
                selectSportsmanId = sportsmanId,
                selectSportsmanRpe = state.trainings.filter { it.datetime.date in state.selectWeek }
                    .flatMap { it.sportsmen }
                    .filter { it.sportsmanUI.id == sportsmanId }
            )
        }
    }

    fun dismissSelectSportsman() = intent {
        reduce {
            state.copy(
                selectSportsmanId = null,
                selectSportsmanRpe = emptyList()
            )
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