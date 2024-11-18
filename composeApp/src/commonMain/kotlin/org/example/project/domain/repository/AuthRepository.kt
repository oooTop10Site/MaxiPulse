package org.example.project.domain.repository

import org.example.project.platform.Either
import org.example.project.platform.Failure

interface AuthRepository {

    suspend fun login(login: String, password: String): Either<Failure, String>

}