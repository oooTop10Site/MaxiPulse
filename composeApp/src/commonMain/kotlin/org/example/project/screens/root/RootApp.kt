package org.example.project.screens.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.example.project.domain.manager.MessageObserverManager
import org.example.project.screens.splash.SplashScreen
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiSnackbarHost
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun RootApp() {
    val windowSizeClass = calculateWindowSizeClass()
    MaxiPulsTheme {
        CompositionLocalProvider(ScreenSize provides windowSizeClass) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
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
                    }
                }
            }
        }
    }
}