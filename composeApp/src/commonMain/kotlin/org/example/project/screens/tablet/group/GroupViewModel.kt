package org.example.project.screens.tablet.group

import org.example.project.domain.repository.AiAssistantRepository
import org.example.project.domain.repository.GroupRepository
import org.example.project.platform.BaseScreenModel
import org.koin.core.component.inject
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class GroupViewModel :
    BaseScreenModel<GroupState, GroupEvent>(GroupState.InitState) {

    private val groupRepository: GroupRepository by inject()

    @OptIn(OrbitExperimental::class)
    fun changeSearch(value: String) = blockingIntent {
        reduce {
            state.copy(
                search = value
            )
        }
    }

    fun changeIsGrid() = intent {
        reduce {
            state.copy(isGrid = !state.isGrid)
        }
    }

    fun changeGroup(value: String) = intent {
        reduce {
            state.copy(
                filterGroup = value
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
                        compositions = it
                    )
                }
            }
        )
    }

}