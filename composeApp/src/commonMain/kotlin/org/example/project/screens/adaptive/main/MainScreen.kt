package org.example.project.screens.adaptive.main

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.domain.model.test.TestUI
import org.example.project.screens.adaptive.main.contents.MainDesktopContent
import org.example.project.screens.adaptive.main.contents.MainMobileContent
import org.example.project.screens.adaptive.root.ScreenSize

class MainScreen(val testUI: TestUI? = null) : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            MainViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        val screenSize = ScreenSize.currentOrThrow
        when (screenSize.widthSizeClass) {
            WindowWidthSizeClass.Medium -> {
                MainDesktopContent(viewModel, state, testUI)
            }
            WindowWidthSizeClass.Expanded -> {
                MainDesktopContent(viewModel, state, testUI)
            }
            WindowWidthSizeClass.Compact -> {
                MainMobileContent(viewModel, state, testUI)
            }
            else -> {}
        }
    }
}