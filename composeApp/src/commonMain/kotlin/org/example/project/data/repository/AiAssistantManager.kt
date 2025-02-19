package org.example.project.data.repository

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import org.example.project.data.model.screen.Screens

interface AiAssistantManager {
    val eventsScreen: Channel<Screens>

    fun putMessage(message: Screens)
    fun observerMessage(observer: (Screens) -> Unit)

}