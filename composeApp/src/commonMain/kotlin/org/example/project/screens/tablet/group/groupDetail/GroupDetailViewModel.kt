package org.example.project.screens.tablet.group.groupDetail

import org.example.project.domain.repository.GamerRepository
import org.example.project.domain.repository.GroupRepository
import org.example.project.platform.BaseScreenModel
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent

internal class GroupDetailViewModel: BaseScreenModel<GroupDetailState, GroupDetailEvent>(GroupDetailState.InitState) {

    private val groupRepository: GroupRepository by inject()
    private val gamerRepository: GamerRepository by inject()

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
                        sportsmans = it
                    )
                }
            }
        )
    }


}