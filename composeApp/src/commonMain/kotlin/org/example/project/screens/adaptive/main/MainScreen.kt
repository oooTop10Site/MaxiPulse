package org.example.project.screens.adaptive.main

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.domain.model.test.TestUI
import org.example.project.platformContent.main.MainContent
import org.example.project.screens.adaptive.main.contents.MainDesktopContent
import org.example.project.screens.adaptive.root.ScreenSize
import org.koin.core.component.KoinComponent

class MainScreen(val testUI: TestUI? = null) : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            MainViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        MainContent(viewModel, state, testUI)
    }
}