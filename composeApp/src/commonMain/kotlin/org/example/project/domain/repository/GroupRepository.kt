package org.example.project.domain.repository

import org.example.project.domain.model.composition.GroupUI
import org.example.project.platform.Either
import org.example.project.platform.Failure

interface GroupRepository {

    suspend fun getGroups(): Either<Failure, List<GroupUI>>
    suspend fun getGroupById(groupId: String): Either<Failure, GroupUI>
    suspend fun createGroup(name: String, image: String, sportmans: List<String>): Either<Failure, Unit>
    suspend fun editGroup(groupId: String, name: String, image: String, sportmans: List<String>): Either<Failure, Unit>
    suspend fun groupDelete(groupId: String): Either<Failure, Unit>

}