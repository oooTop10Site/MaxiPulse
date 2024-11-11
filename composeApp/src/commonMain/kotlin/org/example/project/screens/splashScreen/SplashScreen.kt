package org.example.project.screens.splashScreen

import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.screens.root.ScreenSize

class SplashScreen: Screen {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val windowSizeClass = ScreenSize.currentOrThrow
        when(windowSizeClass.widthSizeClass) {
             WindowWidthSizeClass.Compact -> {
                Text("телефон")
            }
            WindowWidthSizeClass.Medium -> {
                Text("ну планшет мб")
            }
            WindowWidthSizeClass.Expanded -> {
                Text("ну десктоп")
            }
        }
    }
}