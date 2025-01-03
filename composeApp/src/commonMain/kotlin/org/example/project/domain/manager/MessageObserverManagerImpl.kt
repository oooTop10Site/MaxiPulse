package org.example.project.domain.manager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MessageObserverManagerImpl : MessageObserverManager {
    override val message: Channel<String> = Channel(Channel.BUFFERED)
    override val openMobileMenu: Channel<Unit> = Channel(Channel.BUFFERED)


    private val coroutineScope =
        CoroutineScope(Dispatchers.Main) // Use Dispatchers.Main for UI updates

    override fun putMessage(newMessage: String) {
        coroutineScope.launch {
            message.send(newMessage)
        }
    }

    override fun observerMessage(observer: (String) -> Unit) {
        coroutineScope.launch {
            message.receiveAsFlow().collect {
                observer(it)
            }
        }
    }

    override fun openMobileMenu() {
        coroutineScope.launch {
            openMobileMenu.send(Unit)
        }
    }
}