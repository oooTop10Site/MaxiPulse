package org.example.project.domain.repository

import org.example.project.domain.model.composition.GroupUI
import org.example.project.platform.Either
import org.example.project.platform.Failure

interface GroupRepository {

    suspend fun getGroups(): Either<Failure, List<GroupUI>>
    suspend fun getGroupById(groupId: String): Either<Failure, GroupUI>

}