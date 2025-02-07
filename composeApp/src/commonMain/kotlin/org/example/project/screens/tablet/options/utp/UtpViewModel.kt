package org.example.project.screens.tablet.options.utp

import org.example.project.domain.model.composition.GroupUI
import org.example.project.domain.model.utp.UTPTab
import org.example.project.domain.repository.GroupRepository
import org.example.project.platform.BaseScreenModel
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class UtpViewModel: BaseScreenModel<UtpState, UtpEvent>(UtpState.InitState) {

    private val groupRepository: GroupRepository by inject()

    fun changeSelectStageOfReadiness(value: String) = intent {
        reduce {
            state.copy(
                selectStageOfReadiness = value
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

    fun changeSelectPeriodOfReadiness(value: String) = intent {
        reduce {
            state.copy(
                selectPeriodOfReadiness = value
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