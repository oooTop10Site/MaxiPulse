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

class AiAssistantManagerImpl : AiAssistantManager {
    override val eventsScreen: Channel<Screens> = Channel(Channel.BUFFERED)

    private val coroutineScope =
        CoroutineScope(Dispatchers.Main) // Use Dispatchers.Main for UI updates

    override fun putMessage(newMessage: Screens) {
        coroutineScope.launch {
            eventsScreen.send(newMessage)
        }
    }

    override fun observerMessage(observer: (Screens) -> Unit) {
        coroutineScope.launch {
            eventsScreen.receiveAsFlow().collect {
                observer(it)
            }
        }
    }

}