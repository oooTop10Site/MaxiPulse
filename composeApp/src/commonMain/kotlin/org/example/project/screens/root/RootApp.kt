package org.example.project.screens.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.project.screens.splash.SplashScreen
import org.example.project.theme.MaxiPulsTheme


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