package org.example.project.screens.adaptive.root

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

val RootNavigator: ProvidableCompositionLocal<Navigator?> =
    staticCompositionLocalOf { null }

val ScreenSize: ProvidableCompositionLocal<WindowSizeClass?> =
    compositionLocalOf { null }

abstract class AdaptiveScreen<T>(): Screen {
    @Composable
    abstract fun MobileContent(viewModel: T)

    @Composable
    abstract fun TabletContent(viewModel: T)

    @Composable
    abstract fun DesktopContent(viewModel: T)
}