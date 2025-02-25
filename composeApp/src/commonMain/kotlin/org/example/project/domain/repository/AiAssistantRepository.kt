package org.example.project.domain.repository

import org.example.project.domain.model.AiEvent
import org.example.project.platform.Either
import org.example.project.platform.Failure

interface AiAssistantRepository {

    suspend fun sendMessage(message: String, autoSendEvent: Boolean = true): Either<Failure, AiEvent>

}