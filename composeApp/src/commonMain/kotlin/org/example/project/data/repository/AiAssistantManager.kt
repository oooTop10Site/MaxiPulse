package org.example.project.data.repository

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import org.example.project.data.model.screen.Screens
import org.example.project.domain.model.AiEvent

interface AiAssistantManager {
    val eventsScreen: Channel<AiEvent>

    fun putMessage(message: AiEvent)
    fun observerMessage(observer: (AiEvent) -> Unit)

}