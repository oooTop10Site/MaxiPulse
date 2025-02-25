package org.example.project.screens.tablet.options.utp

import kotlinx.datetime.LocalDate
import org.example.project.data.mapper.toAiEvent
import org.example.project.domain.model.AiEvent
import org.example.project.domain.model.AnalizeGraph
import org.example.project.domain.model.composition.GroupUI
import org.example.project.domain.model.log.CriteriaUpload
import org.example.project.domain.model.log.EventType
import org.example.project.domain.model.training.TrainingStageChssUI
import org.example.project.domain.model.training.TrainingUtpStageUI
import org.example.project.domain.model.training.TrainingUtpUI
import org.example.project.domain.model.utp.UTPTab
import org.example.project.domain.repository.AiAssistantRepository
import org.example.project.domain.repository.GamerRepository
import org.example.project.domain.repository.GroupRepository
import org.example.project.platform.BaseScreenModel
import org.example.project.platform.randomUUID
import org.example.project.utils.orEmpty
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import kotlin.math.min
import kotlin.text.isDigit

internal class UtpViewModel : BaseScreenModel<UtpState, UtpEvent>(UtpState.InitState) {

    private val groupRepository: GroupRepository by inject()
    private val gamerRepository: GamerRepository by inject()
    private val aiRepository: AiAssistantRepository by inject()



    fun changeSelectTrainingStage(id: String, value: String) = intent {
        reduce {
            state.copy(
                selectGroup = state.selectGroup?.copy(
                    selectTrainingStage = value
                ),
            )
        }
    }


    fun changePeriod(value: String) = intent {
        reduce {
            state.copy(
                period = value
            )
        }
    }

    fun changeSelectAnalizeGraph(value: AnalizeGraph) = intent {
        reduce {
            state.copy(selectAnalizeGraph = value)
        }
    }

    fun changeIsWeek() = intent {
        reduce {
            state.copy(
                isWeek = !state.isWeek
            )
        }
    }

    fun changeIsComposeWithPlan() = intent {
        reduce {
            state.copy(
                isCompareWithPlan = !state.isCompareWithPlan
            )
        }
    }

    fun changeSelectYearReadies(id: String, value: String) = intent {
        reduce {
            state.copy(
                selectGroup = state.selectGroup?.copy(
                    yearReadies = value
                ),
            )
        }
    }

    fun dismiss() = intent() {
        reduce {
            state.copy(
                selectedDay = null
            )
        }
    }

    fun changeSelectDay(localDate: LocalDate) = intent {
        reduce {
            state.copy(
                selectedDay = state.days.find { it.date == localDate } ?: TrainingUtpUI.default(
                    localDate
                ),
            )
        }
    }

    fun addEmptyStage() = intent {
        reduce {
            state.copy(
                selectedDay = state.selectedDay?.let {
                    it.copy(stages = it.stages + TrainingUtpStageUI.Default.copy(id = randomUUID()))
                }
            )
        }
    }

    fun saveSelectedDay(trainingUtpUI: TrainingUtpUI) = intent {
        val isExist = state.days.find { it.id == trainingUtpUI.id } != null

        reduce {
            state.copy(
                days = if (isExist) state.days.map { if (it.id == trainingUtpUI.id) trainingUtpUI else it } else state.days + trainingUtpUI,
                selectedDay = null,

                )
        }
    }

    fun changeSelectedEvent(event: EventType) = intent {
        reduce {
            state.copy(
                selectedDay = state.selectedDay?.copy(
                    typeOfEvent = event
                )
            )
        }
    }

    fun changeSelectedCriteria(criteriaUpload: CriteriaUpload) = intent {
        reduce {
            state.copy(
                selectedDay = state.selectedDay?.copy(
                    criteriaUpload = criteriaUpload
                )
            )
        }
    }

    fun changeSelectedMin(min: String, trainingUtpStageId: String) = intent {
        reduce {
            state.copy(
                selectedDay = state.selectedDay?.let {
                    it.copy(
                        stages = it.stages.map {
                            if (it.id == trainingUtpStageId) it.copy(
                                min = min.take(3).filter { it.isDigit() }.toIntOrNull().orEmpty()
                            ) else it
                        }
                    )
                }
            )
        }
    }


    fun trainingStages(input: String) = intent {
        launchOperation(
            operation = {
                aiRepository.sendMessage(autoSendEvent = false, message = input)
            },
            success = { response ->
                when (response) {
                    is AiEvent.ScreenEvent -> {}
                    is AiEvent.TrainingEvent -> {
                        reduceLocal {
                            state.copy(
                                selectedDay = state.selectedDay?.let {
                                    it.copy(
                                        stages = response.value.map {
                                            TrainingUtpStageUI(
                                                id = randomUUID(),
                                                min = it.time.toInt(),
                                                value = it.chss,
                                                title = it.title
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    }

                    AiEvent.Unknown -> {}
                }
            }
        )
    }

    fun changeSelectedValue(value: String, trainingUtpStageId: String) = intent {
        reduce {
            state.copy(
                selectedDay = state.selectedDay?.let {
                    it.copy(
                        stages = it.stages.map {
                            if (it.id == trainingUtpStageId) it.copy(
                                value = value.take(3).filter { it.isDigit() }.toIntOrNull()
                                    .orEmpty()
                            ) else it
                        }
                    )
                }
            )
        }
    }

    fun loadGroups() = intent {
        launchOperation(
            operation = {
                groupRepository.getGroups()
            },
            success = {
                reduceLocal {
                    state.copy(
                        groups = it
                    )
                }
            }
        )
    }

    fun selectGroup(groupUI: GroupUI) = intent {
        reduce {
            state.copy(
                selectGroup = groupUI
            )
        }
    }

    fun changeUTPTab(value: UTPTab) = intent {
        reduce {
            state.copy(
                utpTab = value
            )
        }
    }

    fun changeSelectYear(value: Int) = intent {
        reduce {
            state.copy(
                selectYear = value
            )
        }
    }

    fun changeSelectMicroCycle(value: String) = intent {
        reduce {
            state.copy(
                selectMicroCycle = value
            )
        }
    }

    fun changeSelectReadiness(value: String) = intent {
        reduce {
            state.copy(
                selectReadiness = value
            )
        }
    }

}