package org.example.project.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.data.api.MaxiPulseApi
import org.example.project.data.model.login.LoginRequest
import org.example.project.domain.repository.AuthRepository
import org.example.project.platform.Either
import org.example.project.platform.Failure
import org.example.project.platform.apiCall

class AuthRepositoryImpl(private val maxiPulseApi: MaxiPulseApi) : AuthRepository {
    override suspend fun login(
        login: String,
        password: String
    ): Either<Failure, String> {
        return apiCall(
            call = { maxiPulseApi.login(LoginRequest(login, password)) },
            mapResponse = { it.data?.token.orEmpty() }
        )
    }
}