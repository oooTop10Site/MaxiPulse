@file:OptIn(OrbitExperimental::class)

package org.example.project.screens.group.groupEdit

import org.example.project.domain.repository.GamerRepository
import org.example.project.domain.repository.GroupRepository
import org.example.project.platform.BaseScreenModel
import org.koin.core.component.inject
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import kotlin.getValue

internal class GroupEditViewModel :
    BaseScreenModel<GroupEditState, GroupEditEvent>(GroupEditState.InitState) {

    private val groupRepository: GroupRepository by inject()
    private val gamerRepository: GamerRepository by inject()

    fun save() = intent {

    }

    fun changeGroupTitle(value: String) = blockingIntent {
        reduce {
            state.copy(
                groupUI = state.groupUI.copy(title = value)
            )
        }
    }

    fun loadData(groupId: String) = intent {
        launchOperation(
            operation = {
                groupRepository.getGroupById(groupId)
            },
            success = {
                reduceLocal {
                    state.copy(
                        groupUI = it
                    )
                }
            }
        )
        launchOperation(
            operation = {
                gamerRepository.getGamersByGroupId(groupId)
            },
            success = {
                reduceLocal {
                    state.copy(
                        sportsmans = it,
                        filteredSportsmans = it.filter { it.id !in state.deleteSportsman }
                    )
                }
            }
        )
    }

    fun deleteSportsman(id: String) = intent {
        val newDeleteSportsmans = state.deleteSportsman + id
        reduce {
            state.copy(
                deleteSportsman = state.deleteSportsman + id,
                filteredSportsmans = state.sportsmans.filter { it.id !in newDeleteSportsmans }
            )
        }
    }

}