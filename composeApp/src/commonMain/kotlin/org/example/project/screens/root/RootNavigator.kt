package org.example.project.screens.root

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.navigator.Navigator

val RootNavigator: ProvidableCompositionLocal<Navigator?> =
    staticCompositionLocalOf { null }

val ScreenSize: ProvidableCompositionLocal<WindowSizeClass?> =
    staticCompositionLocalOf { null }