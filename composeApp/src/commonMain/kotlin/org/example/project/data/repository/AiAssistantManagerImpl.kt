package org.example.project.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.example.project.data.model.screen.Screens
import org.example.project.domain.model.AiEvent

class AiAssistantManagerImpl : AiAssistantManager {
    override val eventsScreen: Channel<AiEvent> = Channel(Channel.BUFFERED)


    private val coroutineScope =
        CoroutineScope(Dispatchers.Main) // Use Dispatchers.Main for UI updates

    override fun putMessage(newMessage: AiEvent) {
        coroutineScope.launch {
            eventsScreen.send(newMessage)
        }
    }

    override fun observerMessage(observer: (AiEvent) -> Unit) {
        coroutineScope.launch {
            eventsScreen.receiveAsFlow().collect {
                observer(it)
            }
        }
    }

}