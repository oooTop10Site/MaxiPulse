package org.example.project.data.api

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import org.example.project.data.model.ai.PromptRequest
import org.example.project.data.model.ai.PromptResponse

interface AiApi {

    @POST("generate")
    suspend fun sendPrompt(@Body request: PromptRequest): PromptResponse
}