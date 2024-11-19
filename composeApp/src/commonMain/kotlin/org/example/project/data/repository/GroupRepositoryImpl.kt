package org.example.project.data.repository

import org.example.project.data.api.MaxiPulseApi
import org.example.project.data.mapper.toUI
import org.example.project.domain.model.composition.GroupUI
import org.example.project.domain.repository.GroupRepository
import org.example.project.platform.Either
import org.example.project.platform.Failure
import org.example.project.platform.apiCall

class GroupRepositoryImpl(private val maxiPulseApi: MaxiPulseApi) : GroupRepository {
    override suspend fun getGroups(): Either<Failure, List<GroupUI>> {
        return apiCall(
            call = {
                maxiPulseApi.getGroups()
            },
            mapResponse = {
                it.data?.map { it.toUI() }.orEmpty()
            }
        )
    }

    override suspend fun getGroupById(groupId: String): Either<Failure, GroupUI> {
        return apiCall(
            call = {
                maxiPulseApi.getGroupById(id = groupId)
            },
            mapResponse = {
                it.data?.toUI() ?: GroupUI.Default
            }
        )
    }
}