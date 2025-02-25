package org.example.project.data.repository

import cafe.adriel.voyager.core.screen.Screen
import org.example.project.data.api.AiApi
import org.example.project.data.api.MaxiPulseApi
import org.example.project.data.mapper.toAiEvent
import org.example.project.data.model.ai.PromptRequest
import org.example.project.data.model.screen.Screens
import org.example.project.domain.model.AiEvent
import org.example.project.domain.repository.AiAssistantRepository
import org.example.project.platform.Either
import org.example.project.platform.Failure
import org.example.project.platform.apiCall

class AiAssistantRepositoryImpl(
    private val aiApi: AiApi,
    private val aiAssistantManager: AiAssistantManager
) : AiAssistantRepository {
    override suspend fun sendMessage(message: String, autoSendEvent: Boolean): Either<Failure, AiEvent> {
        return apiCall(call = {
            println("СОобщение ебучее - $message")
            aiApi.sendPrompt(PromptRequest(message))

        }, mapResponse = {
            val response = it.generatedText.orEmpty().toAiEvent()
            if(autoSendEvent) {
                aiAssistantManager.putMessage(response)
            }
            response
        })
    }
}