@file:OptIn(OrbitExperimental::class)

package org.example.project.screens.tablet.group.groupEdit

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.success_save
import org.example.project.domain.manager.MessageObserverManager
import org.example.project.domain.repository.GamerRepository
import org.example.project.domain.repository.GroupRepository
import org.example.project.platform.BaseScreenModel
import org.jetbrains.compose.resources.getString
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
    private val observerManager: MessageObserverManager by inject()

    fun save(addSportsmans: List<String> = emptyList<String>()) = intent {
        val message = getString(Res.string.success_save)
        launchOperation(operation = {
            if (state.groupUI.id.isNotBlank()) {
                groupRepository.editGroup(
                    groupId = state.groupUI.id,
                    name = state.groupUI.title,
                    image = state.groupUI.avatar,
                    sportmans = state.filteredSportsmans.map { it.id } + addSportsmans
                )
            } else {
                groupRepository.createGroup(
                    name = state.groupUI.title,
                    image = state.groupUI.avatar,
                    sportmans = state.filteredSportsmans.map { it.id } + addSportsmans)
            }
        },
            success = {
                observerManager.putMessage(message)
                if(state.groupUI.id.isNotBlank()) {
                    loadData(state.groupUI.id)
                } else {
                    postSideEffectLocal(GroupEditEvent.SuccessNavBack)

                }
                allSportsman()
                postSideEffectLocal(GroupEditEvent.Success)
            })
    }

    fun changeDeleteGroupAlert() = intent {
        reduce {
            state.copy(
                deleteGroupAlert = !state.deleteGroupAlert
            )
        }
    }

    fun deleteGroup(groupId: String) = intent {
        launchOperation(
            operation = {
                groupRepository.groupDelete(groupId = groupId)
            },
            success = {
                postSideEffectLocal(GroupEditEvent.SuccessDelete)
            }
        )
    }

    fun allSportsman() = intent {
        launchOperation(
            operation = {
                gamerRepository.getGamers()
            },
            success = {
                reduceLocal {
                    state.copy(
                        allSportsmans = it
                    )
                }
            }
        )
    }

    fun changeGroupTitle(value: String) = blockingIntent {
        reduce {
            state.copy(
                groupUI = state.groupUI.copy(title = value)
            )
        }
    }

    fun loadData(groupId: String) = intent {
        if (groupId.isNotBlank()) {
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

    fun changeSearch(value: String) = blockingIntent {
        reduce {
            state.copy(
                search = value
            )
        }
    }
}