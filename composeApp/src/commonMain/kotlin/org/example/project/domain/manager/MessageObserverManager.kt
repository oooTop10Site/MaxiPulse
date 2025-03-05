package org.example.project.domain.manager

import cafe.adriel.voyager.navigator.tab.TabNavigator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface MessageObserverManager {
    val message: Channel<String>
    val openMobileMenu: Channel<Unit>
    val lastApiCall: StateFlow<(() -> Unit)?>

    fun putMessage(message: String)
    fun observerMessage(observer: (String) -> Unit)

    fun openMobileMenu()

    fun setLastApiCall(lastApiCall: () -> Unit)


}