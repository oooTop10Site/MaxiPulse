package org.example.project.domain.manager

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface MessageObserverManager {
    val message: Channel<String>
    val openMobileMenu: Channel<Unit>

    fun putMessage(message: String)
    fun observerMessage(observer: (String) -> Unit)

    fun openMobileMenu()
}