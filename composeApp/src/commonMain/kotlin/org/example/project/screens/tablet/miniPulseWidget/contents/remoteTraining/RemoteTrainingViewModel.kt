package org.example.project.screens.tablet.miniPulseWidget.contents.remoteTraining

import kotlinx.datetime.LocalDateTime
import org.example.project.domain.model.log.EventType
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.domain.model.training.RemoteTrainingSportsmanStatus
import org.example.project.domain.model.training.RemoteTrainingSportsmanUI
import org.example.project.domain.model.training.RemoteTrainingStatus
import org.example.project.domain.model.training.RemoteTrainingUI
import org.example.project.domain.repository.GamerRepository
import org.example.project.platform.BaseScreenModel
import org.example.project.platform.randomUUID
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class RemoteTrainingViewModel : BaseScreenModel<RemoteTrainingState, RemoteTrainingEvent>(
    RemoteTrainingState.InitState
) {

    private val gamerRepository: GamerRepository by inject()

    fun selectUnFinishedRemoteTrainingId(trainingId: String = state.selectUnFinishedRemoteTrainingId) =
        intent {
            reduce {
                state.copy(
                    selectUnFinishedRemoteTrainingId = if (state.selectUnFinishedRemoteTrainingId == trainingId) "" else trainingId
                )
            }
        }

    fun loadRemoteTraining() = intent {
        reduce {
            state.copy(
                remoteTrainings = listOf(
                    RemoteTrainingUI(
                        id = randomUUID(),
                        title = "",
                        desc = "Чемпионат города по легкой атлетике",
                        localDateTime = LocalDateTime(2025, 3, 10, 10, 0),
                        members = listOf(
                            RemoteTrainingSportsmanUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = randomUUID(),
                                    name = "Иван",
                                    lastname = "Петров",
                                    middleName = "Александрович"
                                ),
                                status = RemoteTrainingSportsmanStatus.InProcess
                            )
                        ),
                        status = RemoteTrainingStatus.InProcess
                    ),
                    RemoteTrainingUI(
                        id = randomUUID(),
                        title = "",
                        desc = "Тренировка на выносливость",
                        localDateTime = LocalDateTime(2025, 3, 11, 14, 30),
                        members = listOf(
                            RemoteTrainingSportsmanUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = randomUUID(),
                                    name = "Алексей",
                                    lastname = "Смирнов",
                                    middleName = "Викторович"
                                ),
                                status = RemoteTrainingSportsmanStatus.End
                            )
                        ),
                        status = RemoteTrainingStatus.End
                    ),
                    RemoteTrainingUI(
                        id = randomUUID(),
                        title = "",
                        desc = "Тестирование физической подготовки",
                        localDateTime = LocalDateTime(2025, 3, 12, 9, 0),
                        members = listOf(
                            RemoteTrainingSportsmanUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = randomUUID(),
                                    name = "Дмитрий",
                                    lastname = "Кузнецов",
                                    middleName = "Сергеевич"
                                ),
                                status = RemoteTrainingSportsmanStatus.InProcess
                            )
                        ),
                        status = RemoteTrainingStatus.InProcess
                    ),
                    RemoteTrainingUI(
                        id = randomUUID(),
                        title = "",
                        desc = "Региональный чемпионат по плаванию",
                        localDateTime = LocalDateTime(2025, 3, 13, 11, 0),
                        members = listOf(
                            RemoteTrainingSportsmanUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = randomUUID(),
                                    name = "Сергей",
                                    lastname = "Иванов",
                                    middleName = "Павлович"
                                ),
                                status = RemoteTrainingSportsmanStatus.End
                            )
                        ),
                        status = RemoteTrainingStatus.End
                    ),
                    RemoteTrainingUI(
                        id = randomUUID(),
                        title = "",
                        desc = "Силовая тренировка в зале",
                        localDateTime = LocalDateTime(2025, 3, 14, 16, 0),
                        members = listOf(
                            RemoteTrainingSportsmanUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = randomUUID(),
                                    name = "Михаил",
                                    lastname = "Соколов",
                                    middleName = "Андреевич"
                                ),
                                status = RemoteTrainingSportsmanStatus.InProcess
                            )
                        ),
                        status = RemoteTrainingStatus.InProcess
                    ),
                    RemoteTrainingUI(
                        id = randomUUID(),
                        title = "",
                        desc = "Контрольный тест на скорость",
                        localDateTime = LocalDateTime(2025, 3, 15, 10, 30),
                        members = listOf(
                            RemoteTrainingSportsmanUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = randomUUID(),
                                    name = "Павел",
                                    lastname = "Морозов",
                                    middleName = "Игоревич"
                                ),
                                status = RemoteTrainingSportsmanStatus.End
                            )
                        ),
                        status = RemoteTrainingStatus.End
                    ),
                    RemoteTrainingUI(
                        id = randomUUID(),
                        title = "",
                        desc = "Соревнования по бегу на 5 км",
                        localDateTime = LocalDateTime(2025, 3, 16, 12, 0),
                        members = listOf(
                            RemoteTrainingSportsmanUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = randomUUID(),
                                    name = "Егор",
                                    lastname = "Васильев",
                                    middleName = "Дмитриевич"
                                ),
                                status = RemoteTrainingSportsmanStatus.InProcess
                            )
                        ),
                        status = RemoteTrainingStatus.InProcess
                    ),
                    RemoteTrainingUI(
                        id = randomUUID(),
                        title = "",
                        desc = "Тренировка на координацию",
                        localDateTime = LocalDateTime(2025, 3, 17, 15, 0),
                        members = listOf(
                            RemoteTrainingSportsmanUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = randomUUID(),
                                    name = "Андрей",
                                    lastname = "Козлов",
                                    middleName = "Николаевич"
                                ),
                                status = RemoteTrainingSportsmanStatus.End
                            )
                        ),
                        status = RemoteTrainingStatus.End
                    ),
                    RemoteTrainingUI(
                        id = randomUUID(),
                        title = "",
                        desc = "Тест на восстановление",
                        localDateTime = LocalDateTime(2025, 3, 18, 8, 0),
                        members = listOf(
                            RemoteTrainingSportsmanUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = randomUUID(),
                                    name = "Николай",
                                    lastname = "Лебедев",
                                    middleName = "Владимирович"
                                ),
                                status = RemoteTrainingSportsmanStatus.InProcess
                            )
                        ),
                        status = RemoteTrainingStatus.InProcess
                    ),
                    RemoteTrainingUI(
                        id = randomUUID(),
                        title = "",
                        desc = "Финал областных соревнований",
                        localDateTime = LocalDateTime(2025, 3, 19, 13, 0),
                        members = listOf(
                            RemoteTrainingSportsmanUI(
                                sportsmanUI = SportsmanUI.Default.copy(
                                    id = randomUUID(),
                                    name = "Владимир",
                                    lastname = "Федоров",
                                    middleName = "Алексеевич"
                                ),
                                status = RemoteTrainingSportsmanStatus.End
                            )
                        ),
                        status = RemoteTrainingStatus.End
                    )
                )//todo
            )
        }
    }

    fun backSportsmen() = intent {
    }

    fun nextSportsmen() = intent {
    }

    fun loadSportsman() = intent {
        launchOperation(
            operation = {
                gamerRepository.getGamers()
            },
            success = {
                reduceLocal {
                    state.copy(
                        sportsmen = it
                    )
                }
            }
        )
    }

    fun createTempTraining() = intent {
        reduce {
            state.copy(
                tempCreateRemoteTrainingUI = RemoteTrainingUI.Default
            )
        }
    }

    fun saveTempTraining() = intent {
        state.tempCreateRemoteTrainingUI?.let {
            reduce {
                state.copy(
                    remoteTrainings = state.remoteTrainings + it,
                    tempCreateRemoteTrainingUI = null,
                )
            }
        }
    }

    fun dismissTempTraining() = intent {
        reduce {
            state.copy(
                tempCreateRemoteTrainingUI = null
            )
        }
    }

    fun selectTraining(remoteTrainingUI: RemoteTrainingUI) = intent {
        reduce {
            state.copy(
                selectRemoteTraining = remoteTrainingUI
            )
        }
    }

    fun dismissSelectTraining() = intent {
        reduce {
            state.copy(
                selectRemoteTraining = null
            )
        }
    }

    fun changeDesc(value: String) = intent {
        reduce {
            state.copy(
                tempCreateRemoteTrainingUI = state.tempCreateRemoteTrainingUI?.copy(desc = value)
            )
        }
    }

    fun changeTitle(title: String) = intent {
        reduce {
            state.copy(
                tempCreateRemoteTrainingUI = state.tempCreateRemoteTrainingUI?.copy(title = title)
            )
        }
    }

    fun selectMember(sportsmanUI: SportsmanUI) = intent {
        println("sportsmanUI.id - ${sportsmanUI.id}")
        println("all - ${state.selectRemoteTraining}")
        reduce {
            state.copy(
                tempCreateRemoteTrainingUI = if (sportsmanUI.id in state.tempCreateRemoteTrainingUI?.members.orEmpty()
                        .map { it.sportsmanUI.id }
                ) state.tempCreateRemoteTrainingUI?.copy(
                    members = state.tempCreateRemoteTrainingUI?.members.orEmpty() - RemoteTrainingSportsmanUI(
                        sportsmanUI = sportsmanUI,
                        status = RemoteTrainingSportsmanStatus.InProcess
                    )
                ) else state.tempCreateRemoteTrainingUI?.copy(
                    members = state.tempCreateRemoteTrainingUI?.members.orEmpty() + RemoteTrainingSportsmanUI(
                        sportsmanUI = sportsmanUI,
                        status = RemoteTrainingSportsmanStatus.InProcess
                    )
                )
            )
        }
    }

    fun finish(isHard: Boolean = false) = intent {
        println("state.selectRemoteTraining?.members - ${state.selectRemoteTraining?.members}")
        if (state.selectRemoteTraining?.members.orEmpty()
                .all() { it.status == RemoteTrainingSportsmanStatus.End }
            || isHard ) {
            reduce {
                state.copy(
                    remoteTrainings = state.remoteTrainings.map {
                        if (it.id == state.selectRemoteTraining?.id) {
                            it.copy(status = RemoteTrainingStatus.End, members = it.members.map { it.copy(status = RemoteTrainingSportsmanStatus.End) })
                        } else it
                    },
                    selectRemoteTraining = null,
                    selectUnFinishedRemoteTrainingId = ""
                )
            }
        } else {
            reduce {
                state.copy(selectUnFinishedRemoteTrainingId = state.selectRemoteTraining?.id.orEmpty())
            }
        }
    }


}