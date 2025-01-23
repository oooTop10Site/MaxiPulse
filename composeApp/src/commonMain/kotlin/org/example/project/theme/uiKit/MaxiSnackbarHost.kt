package org.example.project.theme.uiKit

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.domain.manager.AuthManager
import org.example.project.screens.adaptive.login.LoginScreen
import org.example.project.screens.adaptive.root.RootNavigator
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaxiSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val rootNavigator = RootNavigator.currentOrThrow
    val authManager = AuthManagerExT()
    val swipeState = rememberSwipeToDismissBoxState(confirmValueChange = {
        if (it == SwipeToDismissBoxValue.StartToEnd || it == SwipeToDismissBoxValue.EndToStart) {
            hostState.currentSnackbarData?.dismiss() // Закрыть текущее уведомление
        }
        true
    })
    LaunchedEffect(hostState.currentSnackbarData?.visuals?.message) {
        if(!hostState.currentSnackbarData?.visuals?.message.isNullOrBlank()) {
            swipeState.reset()
        }
        if (hostState.currentSnackbarData?.visuals?.message.orEmpty().contains("Unauthenticated")) {
            authManager.exitFromAcc()
            rootNavigator.replaceAll(LoginScreen())
        }
    }
    SwipeToDismissBox(backgroundContent = {}, content = {
        SnackbarHost(hostState = hostState, modifier = modifier)
    }, state = swipeState)

}
class AuthManagerExT(): KoinComponent {
    val authManager: AuthManager by inject()

    fun exitFromAcc() {
        authManager.exit()
    }
}
