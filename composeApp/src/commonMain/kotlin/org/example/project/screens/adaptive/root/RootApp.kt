package org.example.project.screens.adaptive.root

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.example.project.domain.manager.MessageObserverManager
import org.example.project.screens.adaptive.splash.SplashScreen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiSnackbarHost
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun RootApp() {
    val windowSizeClass = calculateWindowSizeClass()
    MaxiPulsTheme {
        CompositionLocalProvider(ScreenSize provides windowSizeClass) {
            val stateHost = remember { SnackbarHostState() }
            val observerManager = ObserverManagerExt()
            LaunchedEffect(Unit) {
                launch {
                    println("алу я тут")
                    observerManager.message.receiveAsFlow().collect {
                        println("errorMessage - $it")
                        if (it.isNotBlank()) {
                            stateHost.showSnackbar(
                                it
                            )
                        }
                    }
                }
            }
            Navigator(
                SplashScreen(),
                disposeBehavior = NavigatorDisposeBehavior(
                )
            ) {
                CompositionLocalProvider(
                    RootNavigator provides it,
                ) {
                    SlideTransition(
                        it,
                    ) {
                        it.Content()
                    }
//                    val modifierSnackbarHost = when (windowSizeClass.widthSizeClass) {
//                        WindowWidthSizeClass.Compact -> Modifier.fillMaxWidth()
//                            .padding(horizontal = 16.dp)
//
//                        WindowWidthSizeClass.Medium -> Modifier.width(750.dp)
//                        WindowWidthSizeClass.Expanded -> Modifier.width(900.dp)
//                        else -> {
//                            Modifier.fillMaxWidth()
//                                .padding(horizontal = 16.dp)
//                        }
//                    }
                    MaxiSnackbarHost(
//                        modifier = modifierSnackbarHost,
                        hostState = stateHost
                    )
                }
            }

        }
    }
}

class ObserverManagerExt() : KoinComponent {
    val observerManager: MessageObserverManager by inject()

    val message = observerManager.message
}